package org.spring.securityOauth2_ex1.controller;

import org.spring.securityOauth2_ex1.config.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;

@Controller
public class HomeController {

    @GetMapping({"","/index"})
    public String index(){

        return "index";
    }

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model){

        // 기본 시큐리티 저장 MyUserDetails
        System.out.println(myUserDetails.getUsername());
        System.out.println(myUserDetails.getPassword());
        System.out.println(myUserDetails.getAuthorities()
                .stream().map(role -> role.getAuthority()).collect(Collectors.toList()));

        // 커스텀
        System.out.println(myUserDetails.getMemberEntity());
        System.out.println(myUserDetails.getMemberEntity().getId());
        System.out.println(myUserDetails.getMemberEntity().getEmail());
        System.out.println(myUserDetails.getMemberEntity().getPw());
        System.out.println(myUserDetails.getMemberEntity().getNickName());
        System.out.println(myUserDetails.getMemberEntity().getRole());


        model.addAttribute("myUserDetails",myUserDetails);
        return "test";


    }
    @GetMapping("/test2")
    public String test2(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();

        // 기본 시큐리티 저장 MyUserDetails
        System.out.println(myUserDetails.getUsername());
        System.out.println(myUserDetails.getPassword());
        System.out.println(myUserDetails.getAuthorities()
                .stream().map(role -> role.getAuthority()).collect(Collectors.toList()));

        // 커스텀
        System.out.println(myUserDetails.getMemberEntity());
        System.out.println(myUserDetails.getMemberEntity().getId());
        System.out.println(myUserDetails.getMemberEntity().getEmail());
        System.out.println(myUserDetails.getMemberEntity().getPw());
        System.out.println(myUserDetails.getMemberEntity().getNickName());
        System.out.println(myUserDetails.getMemberEntity().getRole());


        model.addAttribute("myUserDetails",myUserDetails);
        return "test";

    }


}
