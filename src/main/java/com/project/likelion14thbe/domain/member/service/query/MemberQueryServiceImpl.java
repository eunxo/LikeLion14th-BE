package com.project.likelion14thbe.domain.member.service.query;

import com.project.likelion14thbe.domain.member.converter.MemberConverter;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.global.apiPayload.code.GeneralErrorCode;
import com.project.likelion14thbe.global.apiPayload.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberQueryServiceImpl implements MemberQueryService{

    private final MemberRepository memberRepository;

    @Override
    public MemberResDTO.MemberPreviewResDTO getMember(Long id){
        Member member = memberRepository.findById(1L).get();

        return MemberConverter.toMemberPreviewResDTO(member);
    }

    @Override
    public MemberResDTO.MemberPreviewResDTO getMyInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(GeneralErrorCode.MEMBER_NOT_FOUND_404));
        return MemberConverter.toMemberPreviewResDTO(member);
    }
}
