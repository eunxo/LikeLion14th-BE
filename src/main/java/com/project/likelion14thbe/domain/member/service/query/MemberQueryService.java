package com.project.likelion14thbe.domain.member.service.query;

import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;

public interface MemberQueryService {
    MemberResDTO.ProfileRes getMember(Long userId);
}