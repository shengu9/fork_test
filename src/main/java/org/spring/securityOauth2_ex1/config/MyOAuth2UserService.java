package org.spring.securityOauth2_ex1.config;

import org.spring.securityOauth2_ex1.constrant.Role;
import org.spring.securityOauth2_ex1.entity.MemberEntity;
import org.spring.securityOauth2_ex1.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MyOAuth2UserService extends DefaultOAuth2UserService {
    // Google사용자 정보를 받아서 DB에 저장
    // google -> email, 비밀번호는 임의로, role: MEMBER -> sec_member08 저장
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest); // google 사용자 정보

        System.out.println("userRequest: " + userRequest);
        System.out.println("getAccessToken: " + userRequest.getAccessToken());
        System.out.println("getAdditionalParameters: " + userRequest.getAdditionalParameters());
        System.out.println("getClientRegistration: " + userRequest.getClientRegistration());
        System.out.println("getClientRegistration: " + userRequest.getClientRegistration());


        //실제 필요한 정보
        ClientRegistration clientRegistration = userRequest.getClientRegistration();// 사용자
        String clientId = clientRegistration.getClientId();
        String registrationId = clientRegistration.getRegistrationId();

        System.out.println("==========");

        Map<String, Object> attributes = oAuth2User.getAttributes();
        
        // {키:값키:값},
        for (String key : attributes.keySet()) {
            System.out.println(key + ": " + attributes.get(key));  // 키: 값
        }

        return snsUserAccess(oAuth2User, registrationId);

    }

    // DB에 저장 -> 저장된 정보를  MyUserDetails 연결....
    public OAuth2User snsUserAccess(OAuth2User oAuth2User, String registrationId) {


        String email = "";
        String name = "";
        String sub = "";
        if (registrationId.equals("google")) {
            System.out.println("구글 정보 ");
            email = oAuth2User.getAttribute("email");   // google 이메일
            name = oAuth2User.getAttribute("name");     // google 닉네임
            sub = oAuth2User.getAttribute("sub");       // google 아이디
        }

        System.out.println("==========");
        System.out.println("email: " + email);
        System.out.println("name: " + name);
        System.out.println("sub: " + sub);



        Optional<MemberEntity> optionalMemberEntity= memberRepository.findByEmail(email);
        if(optionalMemberEntity.isPresent()){
            // 구글 로그인 정보가 있으면 // 그정보 ->UserDetail에 저장
            return new MyUserDetails(optionalMemberEntity.get());   // 등록된 사용자 인증
        }


//        MemberEntity memberEntity= MemberEntity.builder()
//                .email(email)
//                .pw(passwordEncoder.encode("dkanrjsk"))
//                .role(Role.MEMBER)
//                .nickName(name)
//                .build();

//        MemberEntity.toMemberEntity();
        MemberEntity memberEntity= MemberEntity.builder()
                .email(email)
                .pw(passwordEncoder.encode("dkanrjsk"))
                .role(Role.MEMBER)
                .nickName(name)
                .build();



        return new MyUserDetails(memberEntity,oAuth2User.getAttributes());

    }


}
