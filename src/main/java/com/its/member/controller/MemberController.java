package com.its.member.controller;

import com.its.member.dto.MemberDTO;
import com.its.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/save-form")
    public String saveForm(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("member", memberDTOList);
        return "/memberPages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return "/memberPages/login";
    }

    @GetMapping("/login-form")
    public String loginForm(@RequestParam(value = "redirectURL", defaultValue = "/") String redirectURL, Model model) {
        // value = "redirectURL": 로그인 안하고 올 경우
        // defaultValue: 로그인을 하고 올 경우
        model.addAttribute("redirectURL", redirectURL); // 로그인 후 마지막 페이지로 가기 위해
        return "/memberPages/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session,
                        @RequestParam(value = "redirectURL", defaultValue = "/") String redirectURL) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            session.setAttribute("id", loginResult.getId());
//            return "/memberPages/main";
            return "redirect:" + redirectURL; // 로그인 하지 않은 사용자가 로그인 직전에 요청한 주소로 보내줌
        } else return "/memberPages/login";
    }

    @GetMapping("/")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "/memberPages/list";
    }

    // /member/3
    // /member?id=3
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "/memberPages/detail";
    }

    // ajax상세조회
    @PostMapping("/ajax/{id}")
    public @ResponseBody MemberDTO findByIdAjax(@PathVariable Long id) {
        MemberDTO memberDTO = memberService.findById(id);
        return memberDTO;
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return "redirect:/member/";
    }

    /**
     * member/3: 조회(get), 저장(post), 수정(put), 삭제(delete)
     * 겉으로는 같지만 다름
     */

    // ajax삭제
    @DeleteMapping("/{id}")
    public ResponseEntity deleteByIdAjax(@PathVariable Long id) {
        memberService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
        // OK: ajax 호출한 부분에 리턴으로 200 응답을 줌
        // BAD_REQUEST: ajax 호출한 부분에 리턴으로 400 에러 status code를 줌
        // ResponseEntity: @ResponseBody + status code
    }

    // 수정화면 요청
    @GetMapping("/update")
    public String updateForm(HttpSession session, Model model) {
        Long id = (Long) session.getAttribute("id");
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "/memberPages/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "redirect:/member/" + memberDTO.getId();
    }

    // put수정
    @PutMapping("/{id}")
    public ResponseEntity updateByAjax(@RequestBody MemberDTO memberDTO) { // json은 @RequestBody
        // 스프링의 메세지 컨버터가 알아서 옮겨 담아줌
        memberService.update(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 이메일 중복체크
    @PostMapping("/duplicate-check")
    public ResponseEntity duplicateCheck(@RequestParam String memberEmail) {
        boolean checkResult = memberService.duplicateCheck(memberEmail);
        if (checkResult) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 전체를 무효화
//        session.removeAttribute("loginEmail"); // loginEmail만 세션값 삭제
        // 위 두개 세션 같이 쓰면 에러 뜸.. 인터셉터가 에러페이지도 인식해서 로그인 페이지로 보냄
        return "redirect:/";
    }

    @GetMapping("/main")
    public String main() {
        return "/memberPages/main";
    }
}
