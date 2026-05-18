package com.project.likelion14thbe.domain.member.service.query;

import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;

public interface MemberQueryService {

    MemberResDTO.MemberGetRes getProfile(String email);
}
