package com.denis.svetikov.tasktracker.mapper;

import com.denis.svetikov.tasktracker.dto.model.AbstractDto;
import com.denis.svetikov.tasktracker.model.AbstractEntity;

public interface Mapper<E extends AbstractEntity, D extends AbstractDto> {

    E toEntity(D dto);

    D toDto(E entity);

    void updateEntity(D dto, E entity);
}
