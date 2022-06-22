package com.its.member.test;

import com.its.member.dto.MemberDTO;
import com.its.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestClass {
    @Autowired
    private MemberService memberService;

    @Test
    @Transactional
    @Rollback(value = true)

    public void saveTest() {

        MemberDTO memberDTO = new MemberDTO("test@email.com", "testpasword", "testname", 20, "010-4444-3333");
        Long saveId = memberService.save(memberDTO);
        MemberDTO findDTO = memberService.findById(saveId);
        assertThat(memberDTO).isEqualTo(findDTO);
    }

    // DB가 깨끗한 상태로 테스트가 되어야 함. 테스트가 끝나면 테스트 데이터가 사라져야 함.
    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("findAll 테스트")
    public void findAllTest() {
        /**
         * 1. 3개의 테스트 데이터 저장
         * 2. findAll 호출
         * 3. 호출한 리스트의 크기가 3인지를 판단
         * 4. 3이면 테스트 통과, 아니면 테스트 실패
         */

        // 3개의 테스트 데이터를 저장해봅시다. 반복문을 써서
//        for (int i = 1; i < 4; i++) {
//            TestDTO testDTO = new TestDTO("테스트데이터" + i, "테스트데이터" + i);
//            testService.save(testDTO);
//        }

        // 자바 람다식(화살표함수), IntStream
        IntStream.rangeClosed(1, 3).forEach(i -> {
            // 익명함수
            // IntStream: 정수를 만들어주는 인터페이스, rangeClosed: 범위 포함, forEach: "forEach"를 돌린다
            testService.save(new TestDTO("테스트데이터" + i, "테스트데이터" + i));
        });

        // findAll 호출해서 리스트 크기가 3과 일치하는지 확인
        List<TestDTO> testDTOList = testService.findAll(); // 우항 먼저 쓰고 alt + enter
        assertThat(testDTOList.size()).isEqualTo(3);
        // "assertThat"의 매개변수는 첫번째 비교대상
        // chaining method: 연결해서 매서드를 계속 쓸 수 있다
    }

    @Test
    @DisplayName("삭제 테스트")
    @Transactional
    @Rollback(value = true)
    public void deleteTest() {
        TestDTO testDTO = new TestDTO("데이터1", "데이터2");
        Long saveId = testService.save(testDTO);
        testService.delete(saveId);
        TestDTO testDTO1 = testService.findById(saveId);
        assertThat(testDTO1).isNull();
    }

    @Test
    @Transactional
    @Rollback(value = true) // value = true/false로 롤백 기능을 키고 끄는 스위치 느낌
    @DisplayName("수정 테스트")
    public void updateTest() {
        /**
         * 수정 테스트를 어떻게 할지 시나리오 써보고
         * assertThat().isNotEqualTo()
         */

        // 1. TestDTO 데이터를 저장하고 saveId를 리턴받음
        // 2. findById(saveId)로 수정전 TestDTO 가져옴
        // 3. 새로운 TestDTO 객체 선언하고 수정할 데이터와 saveId를 저장함
        // 4. 수정용 TestDTO에 update 메서드 실행
        // 5. saveId로 수정후 TestDTO 가져옴
        // 6. 수정전 TestDTO와 수정후 TestDTO 비교

        TestDTO testDTO = new TestDTO("데이터1", "데이터2");
        Long saveId = testService.save(testDTO);
        TestDTO findDTO = testService.findById(saveId);
        TestDTO updateTestDTO = new TestDTO(saveId, "수정데이터1", "수정데이터2");
        testService.update(updateTestDTO);
        TestDTO updatedTestDTO = testService.findById(saveId);
        System.out.println(findDTO);
        System.out.println(updatedTestDTO);
        assertThat(findDTO).isNotEqualTo(updatedTestDTO);
    }
}
