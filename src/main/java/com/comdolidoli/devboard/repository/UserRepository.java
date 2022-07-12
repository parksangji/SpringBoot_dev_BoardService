package com.comdolidoli.devboard.repository;

import com.comdolidoli.devboard.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByusername(String username);
}
