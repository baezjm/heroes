package com.w2m.heroes.dto;

import lombok.*;

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
