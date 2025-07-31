package com.mstra.removebg.controller;

import com.mstra.removebg.dto.UserDTO;
import com.mstra.removebg.response.RemoveBgResponse;
import com.mstra.removebg.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
