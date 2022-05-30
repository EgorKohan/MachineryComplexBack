package com.bsuir.security.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RefreshJwtRequest {

    @NotNull(message = "Refresh token must be not empty!")
    public String refreshToken;

}
