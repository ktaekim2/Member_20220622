package com.its.member.controller;

import com.its.member.dto.MemberDTO;
import com.its.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Member;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/save-form")
    public String saveForm() {
        return "/memberPages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return "index";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "/memberPages/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String memberEmail, @RequestParam String memberPassword, HttpSession session) {
        MemberDTO memberDTO = memberService.login(memberEmail, memberPassword);
        if (memberDTO != null) {
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
            return "/memberPages/main";
        } else
            return null;
    }
}
