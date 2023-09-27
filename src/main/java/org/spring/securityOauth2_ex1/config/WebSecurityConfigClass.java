package org.spring.securityOauth2_ex1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class WebSecurityConfigClass {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();
        // 사용자 요청 URL
        http.authorizeHttpRequests()
                // 모든 사용자 접근 허용
                .antMatchers("/","/member/login","/member/join").permitAll()
                // css,image,js,,
                .antMatchers("/css/**","/images/**","/js/**").permitAll()
                // 로그인 -> 권한과 상관없이
                .antMatchers("/member/logOut").authenticated()
                // 관리자만   //ROLE_ 제외하고 ADMIN
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                // 판매자, 관리자
                .antMatchers("/shop/**").hasAnyRole("ADMIN","SELLER")
                // 관리자 , 회원
                .antMatchers("/board/**").hasAnyRole("ADMIN","MEMBER")
        // 위에 설정 제외하고 모두 접근 허용
//            .anyRequest().permitAll()
        ;

        // 로그인
        http.formLogin()
                // 로그인 페이지로 이동
                .loginPage("/member/login")
                .usernameParameter("email")
                .passwordParameter("pw")
                //form ,POST, URL
                .loginProcessingUrl("/member/login")
                .defaultSuccessUrl("/")
                .failureUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/member/login")
                .userInfoEndpoint()
                .userService(myAuth2UserService())
        ;


        // 로그아웃 /logout
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logOut"))
                .logoutSuccessUrl("/")
        ;

        return http.build();
    }

    // 비빌번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // oAuth2.0  SNS로그인 설정
    @Bean                   // 요청, User
    public OAuth2UserService<OAuth2UserRequest, OAuth2User>  myAuth2UserService(){
        return new MyOAuth2UserService();
    }


}
