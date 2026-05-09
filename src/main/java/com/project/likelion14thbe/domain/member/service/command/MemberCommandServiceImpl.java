package com.project.likelion14thbe.domain.member.service.command;

import com.project.likelion14thbe.domain.member.converter.MemberConverter;
import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.exception.MemberErrorCode;
import com.project.likelion14thbe.domain.member.exception.MemberException;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.global.apiPayload.code.GeneralErrorCode;
import com.project.likelion14thbe.global.apiPayload.exception.CustomException;
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
    @Transactional(readOnly = true)
    public MemberResDTO.LoginRes login(MemberReqDTO.LoginReq request) {
        // 1. 이메일로 사용자 조회
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(GeneralErrorCode.MEMBER_NOT_FOUND_404)); // 에러 코드에 맞춤 조율

        // 2. 비밀번호 평문 비교 (Security PasswordEncoder가 연동되어 있다면 matches()로 대체 가능)
        if (!member.getPassword().equals(request.getPassword())) {
            throw new CustomException(GeneralErrorCode.UNAUTHORIZED_401); // 401 인증 실패 오류 발생
        }

        String mockToken = "mock-jwt-token-for-" + member.getId();

        return MemberConverter.toLoginRes(member, mockToken);
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



