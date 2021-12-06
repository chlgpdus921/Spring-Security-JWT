package com.example.securityspring.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberSignupRequestDto {

    private String email;
    private String password;
    private String name;
    private String authorityGroup;
    private List<String> authorityList;
}
