package com.project.likelion14thbe.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 값을 자동으로 생성
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column( name = "password", nullable = false)
    private String password;

    @Column(name = "photo_img", nullable = true)
    private String photoImg;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "age",nullable = false)
    private Integer age;
}


