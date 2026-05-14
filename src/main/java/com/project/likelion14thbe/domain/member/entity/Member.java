package com.project.likelion14thbe.domain.member.entity;

import com.project.likelion14thbe.domain.member.enums.Role;
import com.project.likelion14thbe.global.entity.BaseEntity;
import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "Member")

public class Member extends BaseEntity {
    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateMember(String name, String profileImage) {
        this.name = name;
        this.profileImage = profileImage;
    }

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profileImage")
    private String profileImage;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    private Role role;
}
