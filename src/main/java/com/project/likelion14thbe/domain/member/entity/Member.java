package com.project.likelion14thbe.domain.member.entity;

import com.project.likelion14thbe.domain.member.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    @Builder.Default
    private String provider = "local";

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "active";

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateName(String name) {
        this.name = name;
    }


    public void delete() {
        this.deletedAt = LocalDateTime.now();
        this.status = "deleted";
    }

    @Enumerated(EnumType.STRING)
    private Role role;

}