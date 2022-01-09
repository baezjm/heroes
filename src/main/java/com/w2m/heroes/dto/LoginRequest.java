package com.w2m.heroes.dto;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@RequiredArgsConstructor
public class LoginRequest {
    @NotBlank
    String username;

    @NotBlank
    String password;
}
