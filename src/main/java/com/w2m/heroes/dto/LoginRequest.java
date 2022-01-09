package com.w2m.heroes.dto;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@RequiredArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank
    String username;

    @NotBlank
    String password;
}
