package com.project.likelion14thbe.domain.member.service.command;

import com.project.likelion14thbe.domain.member.converter.MemberConverter;
import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.exception.MemberErrorCode;
import com.project.likelion14thbe.domain.member.exception.MemberException;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public MemberResDTO.ProfileRes signUp(MemberReqDTO.SignupReq signupReq) {
        Member member = MemberConverter.toMember(signupReq, passwordEncoder);
        memberRepository.save(member);
        return MemberConverter.toMemberResDTO(member);
    }

    @Override
    public void updatePassword(String email, MemberReqDTO.PasswordResetDTO dto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        member.updatePassword(passwordEncoder.encode(dto.getPassword()));
    }

    @Override
    public void deleteMember(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        member.delete();
    }

    @Override
    public void updateMember(String email, MemberReqDTO.UpdateReq updateReq) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        member.updateMember(updateReq.getName(), updateReq.getProfileImage());
    }
}