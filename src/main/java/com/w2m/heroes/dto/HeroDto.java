package com.w2m.heroes.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@Builder
public class HeroDto {

    Long id;

    @NotBlank
    String name;
}
