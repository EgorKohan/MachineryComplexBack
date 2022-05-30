package com.bsuir.security.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Setter
@Getter
public class JwtRequest {

    @Email
    private String email;
    @Size(min = 7, message = "Password must be greater than 7 letters")
    private String password;

}
