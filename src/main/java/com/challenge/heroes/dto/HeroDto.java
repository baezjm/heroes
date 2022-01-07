package com.challenge.heroes.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HeroDto {

    private Long id;

    private String name;
}
