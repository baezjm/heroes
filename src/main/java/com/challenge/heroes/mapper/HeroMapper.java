package com.challenge.heroes.mapper;

import com.challenge.heroes.dto.HeroDto;
import com.challenge.heroes.entity.Hero;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper
public interface HeroMapper {

    HeroDto toDto(Hero entity);

    Hero fromDto(HeroDto dto);

    @Mappings({@Mapping(target = "id", ignore = true)})
    Hero merge(HeroDto dto, @MappingTarget Hero entity);
}
