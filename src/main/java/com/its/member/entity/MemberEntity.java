package com.its.member.entity;

import com.its.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "memberEmail", length = 30)
    private String memberEmail;

    @Column(name = "memberPassword", length = 30)
    private String memberPassword;

    @Column(name = "memberName", length = 30)
    private String memberName;

    @Column(name = "memberAge")
    private int memberAge;

    @Column(name = "memberPhone", length = 30)
    private String memberPhone;

    // 목적: TestDTO 객체를 받아서 Entity 객체에 옮겨 담은 후 Entity 객체를 리턴
    // Service 클래스에서 toEntity 메서드를 호출해서 리턴받은 객체를
    // memberRepository의 save 메서드로 전달하세요.
    public static MemberEntity toEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberAge(memberDTO.getMemberAge());
        memberEntity.setMemberPhone(memberDTO.getMemberPhone());
        return memberEntity;
    }

    public static MemberEntity toUpdateEntity(TestDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setColumn1(memberDTO.getColumn1());
        memberEntity.setTestColumn2(memberDTO.getTestColumn2());
        return memberEntity;
    }
}
