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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberResDTO.SignUpRes signUp(MemberReqDTO.SignUpReq req) {
        if (memberRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new MemberException(MemberErrorCode.MEMBER_EMAIL_DUPLICATE);
        }

        Member member = MemberConverter.toMember(req, passwordEncoder.encode(req.getPassword()));

        memberRepository.save(member);

        return MemberConverter.toMemberResponseDTO(member);
    }

    @Override
    public MemberResDTO.UpdateRes updateMyInfo(String email, MemberReqDTO.UpdateReq req) {
        Member member = memberRepository.findByEmail(email)
                .filter(m -> m.getDeletedAt() == null)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        member.updateName(req.getName());
        member.updatePassword(passwordEncoder.encode(req.getPassword()));

        return MemberConverter.toUpdateRes(member);
    }

    @Override
    public void updatePassword(Long memberId, MemberReqDTO.PasswordResetDTO dto){

        // 회원 정보 조회
        Member member = memberRepository.findByIdAndNotDeleted(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 비밀번호 변경
        member.updatePassword(passwordEncoder.encode(dto.password()));

    }

    @Override
    public void deleteMember(Long memberId) {

        // 회원 정보 조회
        Member member = memberRepository.findByIdAndNotDeleted(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // soft delete 처리
        member.delete();
    }

    @Override
    public long purgeExpiredDeletedMembers() {
        LocalDateTime threshold = LocalDateTime.now().minusDays(30);
        long deletedCount = memberRepository.deleteByDeletedAtBefore(threshold);
        if (deletedCount > 0) {
            log.info("Deleted {} members that passed 30 days from soft deletion.", deletedCount);
        }
        return deletedCount;
    }

}
