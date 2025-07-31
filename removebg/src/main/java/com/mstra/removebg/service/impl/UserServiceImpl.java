package com.mstra.removebg.service.impl;

import com.mstra.removebg.dto.UserDTO;
import com.mstra.removebg.entities.UserEntity;
import com.mstra.removebg.repository.UserRepository;
import com.mstra.removebg.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        Optional<UserEntity> optionalUser = userRepository.findByClerkId(userDTO.getClerkId());
        if (optionalUser.isPresent()) {
            UserEntity existingUser = optionalUser.get();
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setFirstname(userDTO.getFirstname());
            existingUser.setLastname(userDTO.getLastname());
            existingUser.setPhotoUrl(userDTO.getPhotoUrl());
            if (userDTO.getCredits() != null) existingUser.setCredits(existingUser.getCredits());
            existingUser = userRepository.save(existingUser);
            return mapToUserDTO(existingUser);
        }
        UserEntity savedUser = mapToUserEntity(userDTO);

        return mapToUserDTO(savedUser);
    }

    private UserDTO mapToUserDTO(UserEntity savedUser) {
        return UserDTO.builder()
                .clerkId(savedUser.getClerkId())
                .email(savedUser.getEmail())
                .firstname(savedUser.getFirstname())
                .lastname(savedUser.getLastname())
                .photoUrl(savedUser.getPhotoUrl())
                .build();
    }

    private UserEntity mapToUserEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .clerkId(userDTO.getClerkId())
                .email(userDTO.getEmail())
                .firstname(userDTO.getFirstname())
                .lastname(userDTO.getLastname())
                .photoUrl(userDTO.getPhotoUrl())
                .build();
    }
}
