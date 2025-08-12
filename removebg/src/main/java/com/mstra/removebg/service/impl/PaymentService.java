package com.mstra.removebg.service.impl;

import com.mstra.removebg.entities.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final RestTemplate restTemplate;
    @Value("${paystack.secret.key}")
    private String payStackSecretKey;

    @Value("${paystack.url}")
    private String url;

    public String initializeTransaction(OrderEntity order, String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(payStackSecretKey);

        Map<String, Object> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("amount", order.getAmount());
        payload.put("reference", order.getOrderId());
        payload.put("callback_url", "http://localhost:5173/payment_details");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && (Boolean) response.getBody().get("status")) {
            Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
            return (String) data.get("authorization_url");
        }
        else {
            throw new RuntimeException("Failed to initialize payment");
        }
    }
}
