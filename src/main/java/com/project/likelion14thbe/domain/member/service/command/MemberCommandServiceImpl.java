package com.project.likelion14thbe.domain.member.service.command;

import com.project.likelion14thbe.domain.member.converter.MemberConverter;
import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;

    @Override
    public MemberResDTO.SignUpResDTO signUp(MemberReqDTO.SignUpReqDTO request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 가입된 이메일입니다.");
        }
        Member member = MemberConverter.toMember(request);
        Member saved = memberRepository.save(member);
        return MemberConverter.toSignUpResDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResDTO.LoginResDTO login(MemberReqDTO.LoginReqDTO request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."));
        if (!member.getPassword().equals(request.password())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }
        String accessToken = "eyJhbGciOiJIUzI1NiJ9.dummy.access";
        String refreshToken = "eyJhbGciOiJIUzI1NiJ9.dummy.refresh";
        Long expiresIn = 3600L;
        return MemberConverter.toLoginResDTO(member, accessToken, refreshToken, expiresIn);
    }
}
