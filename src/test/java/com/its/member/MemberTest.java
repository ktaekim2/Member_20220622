package com.its.member;

import com.its.member.dto.MemberDTO;
import com.its.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*; // assertj: java에서 제공하는 테스트 프레임워크

@SpringBootTest
public class MemberTest {
    @Autowired
    private MemberService memberService;

    public MemberDTO newMember(int i) {
        MemberDTO member = new MemberDTO("테스트용이메일" + i, "테스트용비밀번호" + i, "테스트용이름" + i, 99 + i, "테스트용전화번호" + i);
        return member;
    }

    @Test
    @DisplayName("회원가입 테스트")
    @Transactional // 테스트 클래스에서는 자동롤백이 됨(@Rollback 필요없음)
    @Rollback(value = true)
    public void saveTest() {
        Long saveId = memberService.save(newMember(1));
        MemberDTO findDTO = memberService.findById(saveId);
        assertThat(newMember(1).getMemberEmail()).isEqualTo(findDTO.getMemberEmail());
    }

//    @Test
//    @Transactional
//    @Rollback(value = true)
//    public void loginTest() {
//        MemberDTO memberDTO = new MemberDTO("test@email.com", "testpassword", "testname", 20, "010-2323-2222");
//        Long saveId = memberService.save(memberDTO);
//        MemberDTO findDTO = memberService.findById(saveId);
//        String memberEmail = findDTO.getMemberEmail();
//        String memberPassword = findDTO.getMemberPassword();
//        MemberDTO loginDTO = memberService.login(memberEmail, memberPassword);
//        assertThat(findDTO).isEqualTo(loginDTO);
//    }

    @Test
    @DisplayName("로그인 테스트")
    @Transactional
    @Rollback(value = true)
    public void loginTest() {
        final String memberEmail = "로그인용이메일"; // final: 변수값 못바꿈
        final String memberPassword = "로그인용비번";
        String memberName = "로그인용이름";
        int memberAge = 99;
        String memberPhone = "로그인용전화번호";
        MemberDTO memberDTO = new MemberDTO(memberEmail, memberPassword, memberName, memberAge, memberPhone);
        Long saveId = memberService.save(memberDTO);
        // 로그인 객체 생성 후 로그인
        MemberDTO loginMemberDTO = new MemberDTO();
        loginMemberDTO.setMemberEmail(memberEmail);
        loginMemberDTO.setMemberPassword(memberPassword);
        MemberDTO loginResult = memberService.login(loginMemberDTO);
        // 로그인 결과가 not null 이면 테스트 통과
        assertThat(loginResult).isNotNull();
    }

    @Test
    @DisplayName("회원 데이터 저장")
    public void memberSave() {
        IntStream.rangeClosed(1, 20).forEach(i -> {
            memberService.save(newMember(i));
        });
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    @Transactional
    @Rollback(value = true)
    public void memberDeleteTest() {
        /**
         * 신규 회원 등록
         * 삭제 처리
         * 해당 회원으로 조회시 null 이면 통과
         */
        Long saveId = memberService.save(newMember(999));
        memberService.deleteById(saveId);
        assertThat(memberService.findById(saveId)).isNull();
    }
}
