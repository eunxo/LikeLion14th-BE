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
}
