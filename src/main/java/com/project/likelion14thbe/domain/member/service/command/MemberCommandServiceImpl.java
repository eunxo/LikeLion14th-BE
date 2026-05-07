package com.project.likelion14thbe.domain.member.service.command;

import com.project.likelion14thbe.domain.member.converter.MemberConverter;
import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.exception.MemberErrorCode;
import com.project.likelion14thbe.domain.member.exception.MemberException;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;

    @Override
    public MemberResDTO.SignUpResDTO signUp(MemberReqDTO.SignUpReqDTO request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new MemberException(MemberErrorCode.MEMBER_EMAIL_DUPLICATE);
        }
        Member member = MemberConverter.toMember(request);
        Member saved = memberRepository.save(member);
        return MemberConverter.toSignUpResDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResDTO.LoginResDTO login(MemberReqDTO.LoginReqDTO request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        if (!member.getPassword().equals(request.password())) {
            throw new MemberException(MemberErrorCode.MEMBER_WRONG_PASSWORD);
        }
        String accessToken = "eyJhbGciOiJIUzI1NiJ9.dummy.access";
        String refreshToken = "eyJhbGciOiJIUzI1NiJ9.dummy.refresh";
        Long expiresIn = 3600L;
        return MemberConverter.toLoginResDTO(member, accessToken, refreshToken, expiresIn);
    }
}
