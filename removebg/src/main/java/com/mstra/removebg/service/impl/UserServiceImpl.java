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
            existingUser.setFirstname(userDTO.getFirstName());
            existingUser.setLastname(userDTO.getLastName());
            existingUser.setPhotoUrl(userDTO.getPhotoUrl());
            if (userDTO.getCredits() != null) existingUser.setCredits(existingUser.getCredits());
            existingUser = userRepository.save(existingUser);
            return mapToUserDTO(existingUser);
        }
        UserEntity savedUser =  userRepository.save(mapToUserEntity(userDTO));

        return mapToUserDTO(savedUser);
    }

    private UserDTO mapToUserDTO(UserEntity savedUser) {
        return UserDTO.builder()
                .clerkId(savedUser.getClerkId())
                .email(savedUser.getEmail())
                .firstName(savedUser.getFirstname())
                .lastName(savedUser.getLastname())
                .photoUrl(savedUser.getPhotoUrl())
                .build();
    }

    private UserEntity mapToUserEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .clerkId(userDTO.getClerkId())
                .email(userDTO.getEmail())
                .firstname(userDTO.getFirstName())
                .lastname(userDTO.getLastName())
                .photoUrl(userDTO.getPhotoUrl())
                .build();
    }
}
