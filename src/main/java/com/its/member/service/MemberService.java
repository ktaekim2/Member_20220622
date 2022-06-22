package com.its.member.service;

import com.its.member.dto.MemberDTO;
import com.its.member.entity.MemberEntity;
import com.its.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) {
        // DTO -> Entity
        MemberEntity memberEntity = MemberEntity.toEntity(memberDTO);
        Long saveId = memberRepository.save(memberEntity).getId();
        return saveId;
    }

    public MemberDTO findById(Long saveId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(saveId);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            // Entity -> DTO
            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
            return memberDTO;
        } else {
            return null;
        }
    }
}
