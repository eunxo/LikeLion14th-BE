package com.project.likelion14thbe.domain.member.service.query;

import com.project.likelion14thbe.domain.member.converter.MemberConverter;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.exception.MemberErrorCode;
import com.project.likelion14thbe.domain.member.exception.MemberException;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService{



    private final MemberRepository memberRepository;

    @Override
    public MemberResDTO.MemberPreviewResDTO getMember(Long id) {
        Member member = memberRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        return MemberConverter.toMemberPreviewResponseDTO(member);
    }

    @Override
    public MemberResDTO.MyInfoRes getMyInfo(String email) {
        Member member = memberRepository.findByEmail(email)
                .filter(m -> m.getDeletedAt() == null)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        return MemberConverter.toMyInfoRes(member);
    }
}
