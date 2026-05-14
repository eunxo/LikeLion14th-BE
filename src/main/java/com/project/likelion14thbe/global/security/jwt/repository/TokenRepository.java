package com.project.likelion14thbe.global.security.jwt.repository;

import com.project.likelion14thbe.global.security.jwt.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
