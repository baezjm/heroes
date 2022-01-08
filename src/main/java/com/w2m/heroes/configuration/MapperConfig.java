package com.w2m.heroes.configuration;

import com.w2m.heroes.mapper.HeroMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public HeroMapper heroMapper(){
        return Mappers.getMapper(HeroMapper.class);
    }
}
