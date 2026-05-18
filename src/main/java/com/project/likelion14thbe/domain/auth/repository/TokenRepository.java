package com.project.likelion14thbe.domain.auth.repository;

import com.project.likelion14thbe.domain.auth.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {

    Optional<Token> findByEmail(String email);
}
