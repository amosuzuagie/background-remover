package com.mstra.removebg.repository;

import com.mstra.removebg.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByClerkId(String clerkId);

    boolean existsByClerkId(String clerkId);

    @Query("""
            SELECT u.email FROM UserEntity u
            WHERE u.clerkId = :clerkId
            """)
    Optional<String> getEmailByClerkId(String clerkId);
}
