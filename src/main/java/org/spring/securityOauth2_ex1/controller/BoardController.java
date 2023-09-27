package org.spring.securityOauth2_ex1.controller;

import lombok.RequiredArgsConstructor;
import org.spring.securityOauth2_ex1.config.MyUserDetails;
import org.spring.securityOauth2_ex1.dto.BoardDto;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.spring.securityOauth2_ex1.service.BoardService;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @GetMapping({"","/index"})
    public String board(){
        return "board/index";
    }

    @GetMapping("/write")
    public String write(){
        return "board/write";
    }

    // insert
    @PostMapping("/write")
    public String writePost(@AuthenticationPrincipal MyUserDetails myUserDetails,
                            BoardDto boardDto){
        //로그인 email
        String email=myUserDetails.getUsername();
        boardService.insertBoard(boardDto,email);
        return "redirect:/board/boardList";
    }
    // select
    @GetMapping("/boardList")
    public String boardList(Model model){
        List<BoardDto> boardList=boardService.boardListDo();

        model.addAttribute("boardList",boardList);

        return "board/boardList";
    }
    // select
    @GetMapping("/detail/{id}")
    public String detail(@AuthenticationPrincipal MyUserDetails myUserDetails,
                         @PathVariable("id") Long id, Model model){
        BoardDto boardDto=boardService.boardDetail(id);
        //해당 게시글
        model.addAttribute("boardDto",boardDto);
        // 로그인 -> 시큐리티 UserDetals
        model.addAttribute("myUserDetails",myUserDetails);
        return "/board/detail";
    }


    // delete
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){

        boardService.boardDelete(id);

        return "redirect:/board/boardList";
    }






}