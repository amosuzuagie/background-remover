package com.mstra.removebg.service;

import com.mstra.removebg.dto.OrderDTO;
import com.mstra.removebg.entities.OrderEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public interface OrderService {
    Map<String, Object> createOrder(String planId, String clerkId) throws IOException;

    void verifyTransaction(Map<String, Object> payload) throws IOException;

    OrderDTO getOrderDetails(String reference) throws IOException;

    Optional<OrderEntity> getOrderByOrderId(String orderId);

    void markOrderPaid(String orderId);
}
