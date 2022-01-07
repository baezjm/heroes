package com.challenge.heroes.mapper;

import com.challenge.heroes.dto.HeroDto;
import com.challenge.heroes.entity.Hero;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class HeroMapperTest {

    private static final HeroMapper MAPPER = Mappers.getMapper(HeroMapper.class);

    @Test
    void test_mapper_to_heroDto() {
        Hero hero = Hero.builder().name("name").id(1L).build();
        HeroDto heroDto = MAPPER.toDto(hero);

        assertThat(hero.getId()).isEqualTo(heroDto.getId());
        assertThat(hero.getName()).isEqualTo(heroDto.getName());
    }

    @Test
    void test_mapper_from_heroDto() {
        HeroDto heroDto = HeroDto.builder().name("name").id(1L).build();
        Hero hero = MAPPER.fromDto(heroDto);

        assertThat(hero.getId()).isEqualTo(heroDto.getId());
        assertThat(hero.getName()).isEqualTo(heroDto.getName());
    }

    @Test
    void test_mapper_merge_hero() {
        HeroDto heroDto = HeroDto.builder().name("name1").build();
        Hero hero = Hero.builder().name("name").id(1L).build();
        Hero hero1 = MAPPER.merge(heroDto, hero);

        assertThat(hero1.getId()).isEqualTo(1L);
        assertThat(hero1.getName()).isEqualTo("name1");
    }
}