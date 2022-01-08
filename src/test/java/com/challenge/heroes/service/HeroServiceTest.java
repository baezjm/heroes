package com.challenge.heroes.service;

import com.challenge.heroes.dto.HeroDto;
import com.challenge.heroes.entity.Hero;
import com.challenge.heroes.exception.HeroNotFoundException;
import com.challenge.heroes.mapper.HeroMapper;
import com.challenge.heroes.repository.HeroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.argThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@MockBean({HeroRepository.class})
@SpyBean({HeroMapper.class})
class HeroServiceTest {

    private final HeroService service;
    private final HeroRepository repository;
    private final HeroMapper heroMapper;

    @Autowired
    public HeroServiceTest(HeroService service, HeroRepository repository, HeroMapper heroMapper) {
        this.service = service;
        this.repository = repository;
        this.heroMapper = heroMapper;
    }

    @Test
    void test_creation_of_a_hero() {
        given(repository.save(any(Hero.class))).willReturn(Hero.builder().id(1L).name("name").build());

        final HeroDto result = service.create(HeroDto.builder().name("name").build());

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("name");
    }

    @Test
    public void test_update_when_exist_hero() {

        final Hero hero = Hero.builder().id(1L).name("hero").build();

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

        Hero hero1 = Hero.builder().id(1L).name("hero").build();
        given(repository.findById(1L)).willReturn(Optional.of(hero1));

        HeroDto heroDeleted = service.delete(1L);

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

        Hero hero1 = Hero.builder().id(1L).name("hero").build();
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

        final List<HeroDto> results = service.findByName("hero");
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
        final List<Hero> heroes = Arrays.asList(
                Hero.builder().id(1L).name("myHero").build(),
                Hero.builder().id(2L).name("myHero").build());

        given(repository.findAll()).willReturn(heroes);

        List<HeroDto> all = service.findAll();
        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(2);
    }
}