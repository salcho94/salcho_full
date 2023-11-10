package com.back.salcho.member.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Getter
@Setter

public class MemberEntity {
    private int memberId;
    private String email;
    private String password;
    private String nickName;
    private Date regDate;
    private Date uptDate;
    private String target;

    public MemberEntity hashPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
        return this;
    }

    public boolean checkPassword(String plainPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(plainPassword, this.password);
    }
}
