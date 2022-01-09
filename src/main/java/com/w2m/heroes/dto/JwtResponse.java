package com.w2m.heroes.dto;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@Builder
@RequiredArgsConstructor
public class JwtResponse {
    String token;
    String type;
    Long id;
    String username;
    String email;
    List<String> roles;
}
