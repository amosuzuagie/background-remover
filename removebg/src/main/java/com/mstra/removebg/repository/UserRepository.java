package com.mstra.removebg.repository;

import com.mstra.removebg.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByClerkId(String clerkId);
    boolean existsByClerkId(String clerkId);
}
