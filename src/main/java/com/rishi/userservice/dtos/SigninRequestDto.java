package com.rishi.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SigninRequestDto {
    private String email;
    private String password;
}
