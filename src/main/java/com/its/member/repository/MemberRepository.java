package com.its.member.repository;

import com.its.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    @Query("select m from MemberEntity m where memberEmail = :memberEmail and memberPassword = :memberPassword")
    MemberEntity findByMemberEmailAndMemberPassword(String memberEmail, String memberPassword);
}
