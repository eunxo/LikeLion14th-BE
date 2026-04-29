package com.project.likelion14thbe.domain.member.controller;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "유저 API", description = "유저 관련 API")
@RequestMapping("/api/v1")
public class MemberController {

    @PostMapping("/users/signup")
    @Operation(summary = "회원 가입", description = "유저가 회원 가입을 합니다.")
    public ResponseEntity<String> signup (
            @RequestBody MemberReqDTO.UserSignupReq userSignupReq
    ){
        // 리뷰 생성 로직~~~
        return ResponseEntity.ok("회원가입 성공");
    }
}
