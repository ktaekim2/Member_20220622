package com.its.member.dto;

import com.its.member.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor // 모든 매개변수를 가진 생성자
@NoArgsConstructor // 매개변수가 없는 생성자
@Data
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private int memberAge;
    private String memberPhone;

    public MemberDTO(String memberEmail, String memberPassword, String memberName, int memberAge, String memberPhone) {
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.memberAge = memberAge;
        this.memberPhone = memberPhone;
    }

}
