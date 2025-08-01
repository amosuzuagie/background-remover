package com.mstra.removebg.controller;

import com.mstra.removebg.dto.UserDTO;
import com.mstra.removebg.response.RemoveBgResponse;
import com.mstra.removebg.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createOrUpdateUser(@RequestBody UserDTO userDTO, Authentication authentication) {
        System.out.println("Got to Controller");
        System.out.println(userDTO);
        RemoveBgResponse response = null;
        if (!authentication.getName().equals(userDTO.getClerkId())) {
            response = RemoveBgResponse.builder()
                    .success(false)
                    .data("User does nit have the permission to access the resources.")
                    .statusCode(HttpStatus.FORBIDDEN)
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        try {
            UserDTO user = userService.saveUser(userDTO);
            response = RemoveBgResponse.builder()
                    .success(true)
                    .data(user)
                    .statusCode(HttpStatus.OK)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e) {
            response = RemoveBgResponse.builder()
                    .success(true)
                    .data(e.getMessage())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/credits")
    public ResponseEntity<?> getUserCredits(Authentication authentication) {
        RemoveBgResponse response = null;

        try {
            if (authentication.getName().isEmpty() || authentication.getName() == null) {
                response = RemoveBgResponse.builder()
                        .statusCode(HttpStatus.FORBIDDEN)
                        .success(false)
                        .data("User does not have permission/access to this resources")
                        .build();
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
            String clerkId = authentication.getName();
            UserDTO user = userService.getUserByClerkId(clerkId);
            Map<String, Integer> map = new HashMap<>();
            map.put("credits", user.getCredits());
            response = RemoveBgResponse.builder()
                    .statusCode(HttpStatus.OK)
                    .success(true)
                    .data(map)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e) {
            response = RemoveBgResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                    .success(false)
                    .data("Something went wrong")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
