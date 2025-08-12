package com.mstra.removebg.service.impl;

import com.mstra.removebg.dto.OrderDTO;
import com.mstra.removebg.dto.UserDTO;
import com.mstra.removebg.entities.OrderEntity;
import com.mstra.removebg.repository.OrderRepository;
import com.mstra.removebg.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.mstra.removebg.entities.OrderEntity.Status.COMPLETE;
import static com.mstra.removebg.entities.OrderEntity.Status.PENDING;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserServiceImpl userService;
    private final PaymentService paymentService;
    private final OrderRepository orderRepository;

    private static final Map<String, PlanDetails> PLAN_DETAILS = Map.of(
            "Basic", new PlanDetails("Basic", 100, 499.00),
            "Premium", new PlanDetails("Premium", 250, 899.00),
            "Ultimate", new PlanDetails("Ultimate", 1000, 1499.00)
    );

    private record PlanDetails(String name, int credits, double amount){}


    @Override
    public Map<String, Object> createOrder(String planId, String clerkId) {
        Map<String, Object> response = new HashMap<>();

        PlanDetails details = PLAN_DETAILS.get(planId);
        if (details == null) throw new IllegalArgumentException("Invalid planId: " + planId);

        OrderEntity order = OrderEntity.builder()
                .clerkId(clerkId)
                .plan(details.name())
                .credits(details.credits())
                .amount(details.amount() * 100)
                .orderId("order_rcpt_"+ System.currentTimeMillis())
                .status(PENDING)
                .build();

        order = orderRepository.save(order);

        String email = userService.getUserEmailByClerkId(order.getClerkId());
        String paymentLink = paymentService.initializeTransaction(order, email);
        String reference = order.getOrderId();

        response.put("payment_link", paymentLink);
        response.put("reference", reference);


        return response;
    }

    @Override
    public void verifyTransaction(Map<String, Object> payload) throws IOException {
        if (payload == null) {
            log.error("Received null payload from Paystack");
            return;
        }
        Map<String, Object> data = (Map<String, Object>) payload.get("data");

        String reference = (String) data.get("reference");
        String status = (String) data.get("status");

        if (reference == null || status == null) {
            log.error("Missing reference or status in Paystack data: {}", data);
            return;
        }

        log.info("REFERENCE: {}", reference);
        log.info("STATUS: {}", status);

        Optional<OrderEntity> optionalOrder = orderRepository.findByOrderId(reference);
        if (optionalOrder.isPresent()) {
            OrderEntity order = optionalOrder.get();
            if (status.equals("success")) {
                order.setStatus(COMPLETE);

                UserDTO userDTO = userService.getUserByClerkId(order.getClerkId());
                userDTO.setCredits(userDTO.getCredits() + order.getCredits());
                userService.saveUser(userDTO);
                orderRepository.save(order);
            }

        }
    }

    @Override
    public OrderDTO getOrderDetails(String reference) {
        System.out.println("GOT TO ORDER_DETAILS SERVICE>>>");
        OrderEntity order = orderRepository.findByOrderId(reference)
                .orElseThrow(() -> new IllegalArgumentException("Order not found."));

        System.out.println("ORDER: " + order);

        return OrderDTO.builder()
                .id(String.valueOf(order.getId()))
                .entity("order")
                .amount(order.getAmount() * 100)
                .currency("NGN")
                .status(String.valueOf(order.getStatus()))
                .created_at(order.getCreatedAt())
                .receipt(order.getOrderId())
                .build();
    }

    @Override
    public Optional<OrderEntity> getOrderByOrderId(String orderId) {
        return Optional.empty();
    }

    @Override
    public void markOrderPaid(String orderId) {

    }
}
