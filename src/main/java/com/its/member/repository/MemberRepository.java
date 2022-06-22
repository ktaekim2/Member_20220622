package com.its.member.repository;

import com.its.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
//    @Query("select m from MemberEntity m where memberEmail = :memberEmail and memberPassword = :memberPassword")
//
//    MemberEntity findByMemberEmailAndMemberPassword(String memberEmail, String memberPassword);

    // select * from member_table where memberEmail = ?
    // 리턴타입: MemberEntity
    // 매개변수: memberEmail(String)
    // 인터페이스에서 정의하는 매서드는 실행블록을 가질 수 없다: 추상메서드
    // 추상메서드: 메서드의 형태만 정의, 구체적인 내용은 그 인터페이스를 구현하는 구현 클래스에서
    Optional<MemberEntity> findByMemberEmail(String memberEmail); // 추상메서드라서 중괄호X
}
