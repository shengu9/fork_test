package org.spring.securityOauth2_ex1.service;

import lombok.RequiredArgsConstructor;
import org.spring.securityOauth2_ex1.constrant.Role;
import org.spring.securityOauth2_ex1.dto.MemberDto;
import org.spring.securityOauth2_ex1.entity.MemberEntity;
import org.spring.securityOauth2_ex1.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional // 확정일 때 실행, 중간에 오류있으면 아에 시작 안함
    public void memberInsert(MemberDto memberDto) {

        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDto, passwordEncoder);

        MemberEntity.builder()
                .email(memberDto.getEmail())
                .pw(passwordEncoder.encode(memberDto.getPw()))
                .nickName(memberDto.getNicKName())
                .role(Role.MEMBER)
                .build();


        String email = memberRepository.save(memberEntity).getEmail();

        memberRepository.findByEmail(email).orElseThrow(()->{
            throw new IllegalArgumentException("이메일이 존재하지 않습니다.");
        });


    }
}
