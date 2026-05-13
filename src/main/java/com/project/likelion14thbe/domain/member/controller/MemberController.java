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
    public CustomResponse<MemberResDTO.SignUpResDTO> signUp(
            @Valid @RequestBody MemberReqDTO.SignUpReqDTO request
    ) {
        MemberResDTO.SignUpResDTO body = memberCommandService.signUp(request);
        return CustomResponse.onSuccess(HttpStatus.CREATED, "회원가입 성공", body);
    }

    @Override
    @PostMapping("/auth/login")
    public CustomResponse<MemberResDTO.LoginResDTO> login(
            @Valid @RequestBody MemberReqDTO.LoginReqDTO request
    ) {
        return CustomResponse.onSuccess(HttpStatus.OK, "로그인 성공", memberCommandService.login(request));
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
    public CustomResponse<MemberResDTO.MyInfoResDTO> getMyInfo(
            @RequestParam Long memberId
    ) {
        return CustomResponse.onSuccess(HttpStatus.OK, "내 정보 조회 성공", memberQueryService.getMyInfo(memberId));
    }
}
