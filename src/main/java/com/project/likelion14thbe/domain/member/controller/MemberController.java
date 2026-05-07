package com.project.likelion14thbe.domain.member.controller;

import com.project.likelion14thbe.domain.member.controller.docs.MemberDocs;
import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.service.command.MemberCommandService;
import com.project.likelion14thbe.domain.member.service.query.MemberQueryService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController implements MemberDocs {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @Override
    @PostMapping("/members")
    public ResponseEntity<MemberResDTO.SignUpResDTO> signUp(
            @Valid @RequestBody MemberReqDTO.SignUpReqDTO request
    ) {
        MemberResDTO.SignUpResDTO body = memberCommandService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @Override
    @PostMapping("/auth/login")
    public ResponseEntity<MemberResDTO.LoginResDTO> login(
            @Valid @RequestBody MemberReqDTO.LoginReqDTO request
    ) {
        return ResponseEntity.ok(memberCommandService.login(request));
    }

    @Override
    @PatchMapping("/members/{memberId}/password")
    public CustomResponse<String> resetPassword(
            @PathVariable Long memberId,
            @RequestBody MemberReqDTO.PasswordResetDTO request
    ) {
        memberCommandService.updatePassword(memberId, request);
        return CustomResponse.onSuccess("비밀번호 변경 성공");
    }

    @Override
    @DeleteMapping("/members/{memberId}")
    public CustomResponse<String> deleteMember(@PathVariable Long memberId) {
        memberCommandService.deleteMember(memberId);
        return CustomResponse.onSuccess("회원 탈퇴 성공");
    }

    @Override
    @GetMapping("/members/me")
    public ResponseEntity<MemberResDTO.MyInfoResDTO> getMyInfo(
            @RequestParam Long memberId
    ) {
        return ResponseEntity.ok(memberQueryService.getMyInfo(memberId));
    }
}
