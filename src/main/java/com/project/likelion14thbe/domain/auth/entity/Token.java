package com.project.likelion14thbe.domain.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "token")
public class Token {

    @Id
    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "token", nullable = false, length = 500)
    private String token;

    public void updateToken(String newToken) {
        this.token = newToken;
    }
}
