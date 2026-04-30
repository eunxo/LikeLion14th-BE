package com.project.likelion14thbe.domain.member.controller;

import com.project.likelion14thbe.domain.member.controller.docs.MemberDocs;
import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MemberController implements MemberDocs {

    @Override
    @PostMapping("/members")
    public ResponseEntity<MemberResDTO.SignUpResDTO> signUp(
            @Valid @RequestBody MemberReqDTO.SignUpReqDTO request
    ) {
        MemberResDTO.SignUpResDTO body = new MemberResDTO.SignUpResDTO(
                1L,
                request.email(),
                request.nickname(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @Override
    @PostMapping("/auth/login")
    public ResponseEntity<MemberResDTO.LoginResDTO> login(
            @Valid @RequestBody MemberReqDTO.LoginReqDTO request
    ) {
        MemberResDTO.LoginResDTO body = new MemberResDTO.LoginResDTO(
                1L,
                "bro",
                "eyJhbGciOiJIUzI1NiJ9.dummy.access",
                "eyJhbGciOiJIUzI1NiJ9.dummy.refresh",
                3600L
        );
        return ResponseEntity.ok(body);
    }

    @Override
    @PostMapping("/auth/kakao/login")
    public ResponseEntity<MemberResDTO.KakaoLoginResDTO> kakaoLogin(
            @Valid @RequestBody MemberReqDTO.KakaoLoginReqDTO request
    ) {
        MemberResDTO.KakaoLoginResDTO body = new MemberResDTO.KakaoLoginResDTO(
                1L,
                "bro",
                "eyJhbGciOiJIUzI1NiJ9.dummy.access",
                "eyJhbGciOiJIUzI1NiJ9.dummy.refresh",
                false
        );
        return ResponseEntity.ok(body);
    }

    @Override
    @PatchMapping("/members/me/password")
    public ResponseEntity<MemberResDTO.UpdatePasswordResDTO> updatePassword(
            @Valid @RequestBody MemberReqDTO.UpdatePasswordReqDTO request
    ) {
        MemberResDTO.UpdatePasswordResDTO body = new MemberResDTO.UpdatePasswordResDTO(
                1L,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(body);
    }

    @Override
    @GetMapping("/members/me")
    public ResponseEntity<MemberResDTO.MyInfoResDTO> getMyInfo() {
        MemberResDTO.MyInfoResDTO body = new MemberResDTO.MyInfoResDTO(
                1L,
                "user@example.com",
                "bro",
                LocalDateTime.now()
        );
        return ResponseEntity.ok(body);
    }
}
