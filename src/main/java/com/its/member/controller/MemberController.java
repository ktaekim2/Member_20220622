package com.its.member.controller;

import com.its.member.dto.MemberDTO;
import com.its.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    // TestController는 TestService를 호출
    // TestService는 TestRepository를 호출
    // 의존 관계를 만들어주세요. (생성자 주입을 이용)
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

    @GetMapping("/findAll")
    public String findAll(Model model) {
        List<TestDTO> findList = testService.findAll();
        model.addAttribute("findList", findList);
        return "findAll";
    }

    // 요청주소형태(id가 1인 데이터를 삭제하고자 한다면): localhost:8080/delete/1
    // query string 형식: localhost:8080/delete?id=1
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) { // @RequestParam 대신 @PathVariable
     testService.delete(id);
     return "index";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
    TestDTO testDTO = testService.findById(id);
    model.addAttribute("updateDTO", testDTO);
    return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute TestDTO testDTO) {
    Long updateId = testService.update(testDTO);
    return "redirect:/" + updateId;
    }
}
