package com.project.likelion14thbe.domain.member.entity;

import com.project.likelion14thbe.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "social_provider")
    private String socialProvider;

    @Column(name = "social_id")
    private String socialId;

    @Column(name = "profile_image")
    private String profileImage;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
