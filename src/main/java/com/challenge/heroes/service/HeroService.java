package com.challenge.heroes.service;

import com.challenge.heroes.dto.HeroDto;
import com.challenge.heroes.entity.Hero;
import com.challenge.heroes.exception.HeroNotFoundException;
import com.challenge.heroes.mapper.HeroMapper;
import com.challenge.heroes.repository.HeroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = "heroCache")
public class HeroService {

    private static final HeroMapper MAPPER = Mappers.getMapper(HeroMapper.class);

    private final HeroRepository heroRepository;

    public HeroDto create(final HeroDto heroDto) {
        final Hero hero = MAPPER.fromDto(heroDto);
        return MAPPER.toDto(heroRepository.save(hero));
    }

    public HeroDto update(final long id, final HeroDto heroDto) {
        log.info("updating hero with id: {}", id);
        final Hero hero = heroRepository.findById(id).orElseThrow(() -> new HeroNotFoundException(id));
        ;
        final Hero updated = heroRepository.save(MAPPER.merge(heroDto, hero));
        return MAPPER.toDto(updated);
    }

    public HeroDto delete(final long id) {
        log.info("trying to delete hero {}", id);
        final Hero hero = heroRepository.findById(id).orElseThrow(() -> new HeroNotFoundException(id));
        heroRepository.deleteById(id);
        return MAPPER.toDto(hero);
    }

    @Cacheable
    public List<HeroDto> findAll() {
        return heroRepository.findAll().stream().map(MAPPER::toDto).collect(Collectors.toList());
    }

    public HeroDto findById(final long id) {
        log.info("finding hero with id: {}", id);
        final Hero hero = heroRepository.findById(id).orElseThrow(() -> new HeroNotFoundException(id));
        return MAPPER.toDto(hero);
    }

    public List<HeroDto> findByName(final String name) {
        log.info("finding heroes with name: {}", name);
        if (isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("name can not be empty");
        }
        return heroRepository.findByNameContains(name).stream().map(MAPPER::toDto).collect(Collectors.toList());
    }
}
