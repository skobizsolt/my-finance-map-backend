package com.myfinancemap.app.mapper;

import org.mapstruct.MapperConfig;

import java.util.List;

@MapperConfig()
public interface BaseDtoMapper<D, E> {

    D toDto(E entity);

    E toEntity(D dto);

    List<E> toEntity(List<D> dtos);

    List<D> toDto(List<E> entities);
}
