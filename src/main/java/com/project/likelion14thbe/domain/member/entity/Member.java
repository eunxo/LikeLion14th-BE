package com.project.likelion14thbe.domain.member.entity;

import com.project.likelion14thbe.domain.member.enums.Role;
import com.project.likelion14thbe.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "photo")
    private String photo;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void updatePassword(String newPassword){
        this.password = newPassword;
    }

    public void updateProfile(String newName, String newEmail, String newPhoto){
        this.name = newName;
        this.email = newEmail;
        this.photo = newPhoto;
    }
}
