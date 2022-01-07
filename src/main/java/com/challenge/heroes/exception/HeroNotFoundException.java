package com.challenge.heroes.exception;

public class HeroNotFoundException extends RuntimeException {
    public HeroNotFoundException(Long id) {
        super("Could not find hero " + id);
    }
}
