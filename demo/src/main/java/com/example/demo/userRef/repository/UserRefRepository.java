package com.example.demo.userRef.repository;

import com.example.demo.userRef.entity.UserRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRefRepository extends JpaRepository<UserRef, Long> {
    List<UserRef> findByUserId(Long userId);
}

