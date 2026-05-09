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
    public MemberResDTO.MemberCreateResDTO createMember(MemberReqDTO.MemberCreateReqDTO memberCreateReqDTO) {

        Member member = MemberConverter.toMember(memberCreateReqDTO);

        memberRepository.save(member);

        return MemberConverter.toMemberCreateResDTO(member);
    }
    @Override
    public void updatePassword(Long memberId, MemberReqDTO.PasswordResetDTO dto){
        Member member = memberRepository.findByIdAndNotDeleted(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOTFOUND));

        member.updatePassword(dto.getPassword());
    }

    @Override
    public void deleteMember(Long memberId){
        Member member = memberRepository.findByIdAndNotDeleted(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOTFOUND));
        member.delete();

    }


}



