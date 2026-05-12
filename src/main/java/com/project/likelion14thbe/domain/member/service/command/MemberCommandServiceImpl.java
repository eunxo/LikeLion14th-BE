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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandServiceImpl implements MemberCommandService{

    private final MemberRepository memberRepository;

    public MemberResDTO.MemberCreateRes createMember(MemberReqDTO.MemberCreateReq memberCreateReq){
        Member member = MemberConverter.toMember(memberCreateReq);

        memberRepository.save(member);

        return MemberConverter.toMemberResponceDTO(member);
    }

    @Override
    public void updatePassword(Long memberId, MemberReqDTO.PasswordResetDTO dto){
        // 회원 정보 조회
        Member member = memberRepository.findByIdAndNotDeleted(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        member.updatePassword(dto.password());
    }

    @Override
    public void deleteMember(Long memberId){
        //회원 정보 조회
        Member member = memberRepository.findByIdAndNotDeleted(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        //soft delete 처리
        member.delete();
    }
}
