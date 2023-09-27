package org.spring.securityOauth2_ex1.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.securityOauth2_ex1.entity.MemberEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
@NoArgsConstructor
public class MyUserDetails implements UserDetails, OAuth2User {

    private MemberEntity memberEntity;

    private Map<String, Object> attributes;
    public MyUserDetails(MemberEntity memberEntity){
        this.memberEntity=memberEntity;
    }
    public MyUserDetails(MemberEntity memberEntity, Map<String, Object> attributes) {
        this.memberEntity=memberEntity;
        this.attributes=attributes;
    }

  

    //ROLE
    // 일반회원
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectRole=new ArrayList<>();
        collectRole.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_"+memberEntity.getRole().toString(); // ADMIN -?>"ROLE_
            }
        });
        return collectRole;
    }
    //password
    @Override
    public String getPassword() {
        return memberEntity.getPw();
    }
    //아이디
    @Override
    public String getUsername() {
        return memberEntity.getEmail();
    }

    // 계정 만료 여부 true : 만료 안됨
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // 계정 잠김 여부 true : 잠기지 않음
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // 비밀번호 만료 여부 true : 만료 안됨
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // 사용자 활성화 여부  ture : 활성화
    @Override
    public boolean isEnabled() {
        return true;
    }

    
    // SNS 회원
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    @Override
    public String getName() {
        return memberEntity.getNickName();
    }


}