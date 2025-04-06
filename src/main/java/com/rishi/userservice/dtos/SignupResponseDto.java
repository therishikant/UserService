package com.rishi.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponseDto {
    private String name;
    private String email;
    private String password;
}
