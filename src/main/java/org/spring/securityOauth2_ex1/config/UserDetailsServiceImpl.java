package org.spring.securityOauth2_ex1.config;

import lombok.RequiredArgsConstructor;
import org.spring.securityOauth2_ex1.entity.MemberEntity;
import org.spring.securityOauth2_ex1.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        MemberEntity memberEntity = memberRepository.findByEmail(email).orElseThrow(()->{
//            MemberEntity memberEntity1 = memberRepository.findByEmail(email).orElseThrow(IllegalAccessError::new);
            throw new UsernameNotFoundException("이메일이 존재하지 않습니다.");
        });


        return new MyUserDetails(memberEntity);
//        return User.builder()
//                .username(memberEntity.getPw())
//                .password(memberEntity.getPw())
//                .roles(memberEntity.getRole().toString())
//                .build();
    }

}
