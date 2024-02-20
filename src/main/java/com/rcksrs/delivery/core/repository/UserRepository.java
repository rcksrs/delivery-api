package com.rcksrs.delivery.core.repository;

import com.rcksrs.delivery.core.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndActiveTrue(Long id);
    Optional<User> findByEmailAndActiveTrue(String email);
    Optional<User> findByEmailAndPasswordAndActiveTrue(String email, String password);

}
