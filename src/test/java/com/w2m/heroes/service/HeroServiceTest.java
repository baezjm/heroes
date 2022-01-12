package com.w2m.heroes.service;

import com.w2m.heroes.dto.HeroDto;
import com.w2m.heroes.entity.Hero;
import com.w2m.heroes.exception.HeroNotFoundException;
import com.w2m.heroes.mapper.HeroMapper;
import com.w2m.heroes.repository.HeroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.argThat;

@ExtendWith(MockitoExtension.class)
class HeroServiceTest {

    private HeroService service;

    @Mock
    private HeroRepository repository;

    @BeforeEach
    void setUp() {
        this.service = new HeroService(Mappers.getMapper(HeroMapper.class), repository);
    }

    @Test
    void test_creation_of_a_hero() {
        given(repository.save(any(Hero.class))).willReturn(Hero.builder().id(1L).name("name").build());

        final var result = service.create(HeroDto.builder().name("name").build());

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("name");
    }

    @Test
    public void test_update_when_exist_hero() {

        final var hero = Hero.builder().id(1L).name("hero").build();

        given(repository.findById(1L)).willReturn(Optional.of(hero));
        given(repository.save(argThat((Hero h) -> "hero2".equals(h.getName()))))
                .willReturn(Hero.builder().id(1L).name("hero2").build());

        final HeroDto result = service.update(1L, HeroDto.builder().name("hero2").build());

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("hero2");
    }

    @Test
    public void test_update_when_not_exist_hero() {

        given(repository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(1, HeroDto.builder().name("hero2").build()))
                .isExactlyInstanceOf(HeroNotFoundException.class);
    }

    @Test
    public void test_delete_when_exist_hero() {

        final var hero1 = Hero.builder().id(1L).name("hero").build();
        given(repository.findById(1L)).willReturn(Optional.of(hero1));

        var heroDeleted = service.delete(1L);

        assertThat(heroDeleted).isNotNull();
        assertThat(heroDeleted.getId()).isEqualTo(hero1.getId());
    }

    @Test
    public void test_delete_when_does_not_exist_hero() {
        given(repository.findById(1L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> service.delete(1L))
                .isExactlyInstanceOf(HeroNotFoundException.class);
    }

    @Test
    public void test_find_hero_byId_exist() {

        final var hero1 = Hero.builder().id(1L).name("hero").build();
        given(repository.findById(1L)).willReturn(Optional.of(hero1));

        HeroDto result = service.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(hero1.getId());
    }

    @Test
    public void test_find_hero_byId_does_not_exist() {
        given(repository.findById(1L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(1L))
                .isExactlyInstanceOf(HeroNotFoundException.class);
    }

    @Test
    public void test_find_hero_byName_exist() {

        final List<Hero> heroes = Arrays.asList(
                Hero.builder().id(1L).name("myHero").build(),
                Hero.builder().id(2L).name("myHero").build());

        given(repository.findByNameContainsIgnoreCase("hero")).willReturn(heroes);

        final var results = service.findByName("hero");
        assertThat(results).isNotNull();
        assertThat(results.size()).isEqualTo(2);
    }

    @Test
    public void test_find_hero_ByName_dont_exist() {

        given(repository.findByNameContainsIgnoreCase("man")).willReturn(Collections.emptyList());

        final List<HeroDto> results = service.findByName("man");
        assertThat(results).isNotNull();
        assertThat(results).isEmpty();
    }

    @Test
    public void test_findAll() {
        final var heroes = List.of(
                Hero.builder().id(1L).name("myHero").build(),
                Hero.builder().id(2L).name("myHero").build());

        given(repository.findAll()).willReturn(heroes);

        var all = service.findAll();
        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(2);
    }
}