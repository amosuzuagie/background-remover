package com.mstra.removebg.controller;

import com.mstra.removebg.dto.UserDTO;
import com.mstra.removebg.response.RemoveBgResponse;
import com.mstra.removebg.service.RemoveBackgroundService;
import com.mstra.removebg.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {
    private final RemoveBackgroundService removeBackgroundService;
    private final UserService userService;

    @PostMapping("/remove-background")
    public ResponseEntity<?> removeBackground(@RequestParam("file")MultipartFile file, Authentication authentication) {
        RemoveBgResponse response = null;
        Map<String, Object> responseMap = new HashMap<>();

        try {
            // Validate User Authentication
            if (authentication.getName().isEmpty() || authentication.getName() == null) {
                response = RemoveBgResponse.builder()
                        .statusCode(HttpStatus.FORBIDDEN)
                        .data("User does not have permission/access to this resource")
                        .success(false)
                        .build();
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }

            // Validate User credits
            UserDTO userDTO = userService.getUserByClerkId(authentication.getName());
            System.out.println("AUTH: " + authentication.getName());
            System.out.println("CONFIRMING_USER_DTO: " + userDTO);
            if (userDTO.getCredits() == 0) {
                responseMap.put("message", "Insufficient credit balance");
                responseMap.put("Credit Balance: ", userDTO.getCredits());
                response = RemoveBgResponse.builder()
                        .success(false)
                        .data(responseMap)
                        .statusCode(HttpStatus.OK)
                        .build();
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

            byte[] imageBytes = removeBackgroundService.removeBackground(file);
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            userDTO.setCredits(userDTO.getCredits() -1);

            System.out.println("CONFIRMING_CREDIT_CHANGE: " + userDTO.getCredits());

            userService.saveUser(userDTO);
            System.out.println("DEBUG_CREDIT_IN_DATABASE: " + userService.getUserByClerkId(authentication.getName()).getCredits());
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(base64Image);
        }
        catch (Exception e) {
            e.printStackTrace();
            response = RemoveBgResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                    .data("Something went wrong.")
                    .success(false)
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
