package com.bsuir.security.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {

    private static final String TYPE = "Bearer";
    private String accessToken;
    private String refreshToken;

}
