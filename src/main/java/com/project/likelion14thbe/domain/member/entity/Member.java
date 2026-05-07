package com.project.likelion14thbe.domain.member.entity;

import com.project.likelion14thbe.global.entity.BaseEntity;
import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "Member")

public class Member extends BaseEntity {
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
}
