package com.w2m.heroes.exception;

public class HeroNotFoundException extends RuntimeException {
    public HeroNotFoundException(Long id) {
        super("Could not find hero " + id);
    }
}
