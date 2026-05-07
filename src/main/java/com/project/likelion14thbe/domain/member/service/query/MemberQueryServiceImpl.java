package com.project.likelion14thbe.domain.member.service.query;

import com.project.likelion14thbe.domain.member.converter.MemberConverter;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional

public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;

    public static MemberResDTO.ProfileRes toProfileRes(Member member) {
        return MemberResDTO.ProfileRes.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .profileImage(member.getProfileImage())
                .build();
    }

    public MemberResDTO.ProfileRes getMember(Long userId) {
        Member member = memberRepository.findById(userId).get();

        return MemberConverter.toMemberResDTO(member);
    }
}
