package com.its.member.service;

import com.its.member.dto.MemberDTO;
import com.its.member.entity.MemberEntity;
import com.its.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toEntity(memberDTO);
        Long id = memberRepository.save(memberEntity).getId();
        return id;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if(optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
            return memberDTO;
        } else {
            return null;
        }
    }

    public List<TestDTO> findAll() {
        List<MemberEntity> entityList = testRepository.findAll();
        List<TestDTO> testDTOList = new ArrayList<>();
        for(MemberEntity t : entityList) {
            TestDTO testDTO = TestDTO.toDTO(t);
//            testDTO.setColumn1(t.getColumn1());
//            testDTO.setTestColumn2(t.getTestColumn2());
//            testDTO.setId(t.getId());
            testDTOList.add(testDTO);
        }
        return testDTOList;
    }

    public void delete(Long id) {
        testRepository.deleteById(id);
    }

    public Long update(TestDTO testDTO) {
        // save 매서드 호출로 update 쿼리 가능(단, id가 같이 가야함)
        MemberEntity testEntity = MemberEntity.toUpdateEntity(testDTO);
        Long id = testRepository.save(testEntity).getId();
        return id;
    }
}
