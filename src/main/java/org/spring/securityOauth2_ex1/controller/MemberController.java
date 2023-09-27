package org.spring.securityOauth2_ex1.controller;

import lombok.RequiredArgsConstructor;
import org.spring.securityOauth2_ex1.dto.MemberDto;
import org.spring.securityOauth2_ex1.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String join(){
        return "member/join";
    }

    @PostMapping("/join")
    public String joinPost(MemberDto memberDto){

        memberService.memberInsert(memberDto);

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login(){
        return "member/login";
    }

}
