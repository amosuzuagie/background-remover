package com.mstra.removebg.controller;

import com.mstra.removebg.dto.OrderDTO;
import com.mstra.removebg.response.RemoveBgResponse;
import com.mstra.removebg.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestParam String planId, Authentication authentication) throws IOException{

        System.out.println("GOT TO CREATE ORDER");
        RemoveBgResponse response = null;

        if (authentication.getName().isEmpty() || authentication.getName() == null) {
            response = RemoveBgResponse.builder()
                    .statusCode(HttpStatus.FORBIDDEN)
                    .success(false)
                    .data("User does not have permission/access to this resources")
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        try {
            Map<String, Object> data = orderService.createOrder(planId, authentication.getName());
            response = RemoveBgResponse.builder()
                    .success(true)
                    .data(data)
                    .statusCode(HttpStatus.CREATED)
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch (Exception e) {
            response = RemoveBgResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                    .data(e.getMessage())
                    .success(false)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/payment/verify")
    public ResponseEntity<?> verifyTransaction(@RequestBody Map<String, Object> payload) throws IOException {
        System.out.println("Got to verify payment callback");
        orderService.verifyTransaction(payload);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{reference}")
    public ResponseEntity<?> getOrderDetails(@PathVariable String reference, Authentication authentication) throws IOException {
        System.out.println("GOT_TO_ORDER_DETAIL. ORDER_FER: " + reference);
        RemoveBgResponse response = null;

        if (authentication.getName().isEmpty() || authentication.getName() == null) {
            response = RemoveBgResponse.builder()
                    .statusCode(HttpStatus.FORBIDDEN)
                    .data("User does not have permission/access to this resource")
                    .success(false)
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        OrderDTO orderDetails = orderService.getOrderDetails(reference);
        System.out.println("CONTROLLER_ORDER_DETAILS: " + orderDetails);

        response = RemoveBgResponse.builder()
                .success(true)
                .data(orderDetails)
                .statusCode(HttpStatus.OK)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
