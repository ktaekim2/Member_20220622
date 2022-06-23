package com.its.member.service;

import com.its.member.dto.MemberDTO;
import com.its.member.entity.MemberEntity;
import com.its.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toEntity(memberDTO);
        Long saveId = memberRepository.save(memberEntity).getId();
        return saveId;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

//    public MemberDTO login(String memberEmail, String memberPassword) {
//        MemberEntity memberEntity = memberRepository.findByMemberEmailAndMemberPassword(memberEmail, memberPassword);
//        if (memberEntity != null) {
//            System.out.println("로그인 성공");
//            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
//            return memberDTO;
//        } else {
//            System.out.println("로그인 실패");
//            return null;
//        }
//    }

    public MemberDTO login(MemberDTO memberDTO) {
        /**
         * login.html에서 이메일, 비번을 받아오고
         * db로 부터 해당 이메일의 정보를 가져와서
         * 입력받은 비번과 db에서 조회한 비번의 일치여부를 판단하여
         * 일치하면 로그인 성공, 일치하지 않으면 로그인 실패로 처리
         */
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (optionalMemberEntity.isPresent()) {
            MemberEntity loginEntity = optionalMemberEntity.get();
            if (loginEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                memberRepository.findByMemberEmail(loginEntity.getMemberEmail());
                return MemberDTO.toMemberDTO(loginEntity);
            } else {
                return null; // 비밀번호 불일치
            }
        } else {
            return null; // 이메일 불일치
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity m : memberEntityList) {
            MemberDTO memberDTO = MemberDTO.toMemberDTO(m);
            memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public void update(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toUpdateEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    public boolean duplicateCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if (optionalMemberEntity.isPresent()) {
            return false;
        } else {
            return true;
        }
    }
}
