package com.mstra.removebg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mstra.removebg.dto.UserDTO;
import com.mstra.removebg.response.RemoveBgResponse;
import com.mstra.removebg.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webhooks")
public class ClerkWebhookController {
    @Value("${clerk.secretKey}")
    private String webhookSecret;

    private final UserService userService;

    @PostMapping("/clerk")
    public ResponseEntity<?> handleClerkWebhook(
            @RequestHeader("svix-id") String svixId,
            @RequestHeader("svix-timestamp") String svixTimestamp,
            @RequestHeader("svix-signature") String svixSignature,
            @RequestBody String payload
    ) {
        RemoveBgResponse response = null;
        try {
            boolean isValid = verifyWebhookSignature(svixId, svixTimestamp, svixSignature, payload);
            if (!isValid) {
                response = RemoveBgResponse.builder()
                        .statusCode(HttpStatus.UNAUTHORIZED)
                        .data("Invalid webhook signature")
                        .success(false)
                        .build();

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(payload);
            String eventType = rootNode.path("type").asText();

            switch (eventType) {
                case "user.created" -> handleUserCreated(rootNode.path("data"));
                case "user.updated" -> handleUserUpdated(rootNode.path("data"));
                case "user.deleted" -> handleUserDeleted(rootNode.path("data"));
                default -> throw new IllegalArgumentException("Unknown event type" + eventType);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            response = RemoveBgResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                    .data(e.getMessage())
                    .success(false)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private void handleUserDeleted(JsonNode data) {
        userService.deleteUserByClerkId(data.path("id").asText());
    }

    private void handleUserUpdated(JsonNode data) {
        String clerkId = data.path("id").asText();
        UserDTO existingUser = userService.getUserByClerkId(clerkId);

        existingUser.setEmail(data.path("email_addresses").path(0).path("email_address").asText());
        existingUser.setFirstName(data.path("first_name").asText());
        existingUser.setLastName(data.path("last_name").asText());
        existingUser.setPhotoUrl(data.path("image_url").asText());

        userService.saveUser(existingUser);
    }

    private void handleUserCreated(JsonNode data) {
        UserDTO user = UserDTO.builder()
                .clerkId(data.path("id").asText())
                .email(data.path("email_addresses").path(0).path("email_address").asText())
                .firstName(data.path("first_name").asText())
                .lastName(data.path("last_name").asText())
                .photoUrl(data.path("image_url").asText())
                .build();

        userService.saveUser(user);
    }

    private boolean verifyWebhookSignature(String svixId, String svixTimestamp, String svixSignature, String payload) {
        return true;  //todo:. Proper execution of verifyWebhookSignature.
    }

}
