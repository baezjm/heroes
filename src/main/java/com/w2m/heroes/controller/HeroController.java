package com.w2m.heroes.controller;

import com.w2m.heroes.annotations.CustomTimed;
import com.w2m.heroes.dto.HeroDto;
import com.w2m.heroes.service.HeroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/hero")
@RequiredArgsConstructor
public class HeroController {

    private final HeroService heroService;

    @PostMapping
    ResponseEntity<HeroDto> create(@RequestBody @Valid HeroDto hero) {
        final HeroDto created = heroService.create(hero);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    ResponseEntity<HeroDto> update(@PathVariable(value = "id") long id, @RequestBody @Valid HeroDto hero) {
        HeroDto updated = heroService.update(id, hero);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @CustomTimed
    @GetMapping(value = "/{id}")
    HeroDto findById(@PathVariable(value = "id") long id) {
        return heroService.findById(id);
    }

    @GetMapping
    List<HeroDto> findByName(@RequestParam(value = "name") String name) {
        return heroService.findByName(name);
    }

    @DeleteMapping("/{id}")
    HeroDto delete(@PathVariable(value = "id") Long id) {
        return heroService.delete(id);
    }

    @GetMapping("/all")
    List<HeroDto> all() {
        return heroService.findAll();
    }
}
