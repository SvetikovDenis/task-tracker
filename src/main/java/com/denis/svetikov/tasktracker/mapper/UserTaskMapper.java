package com.denis.svetikov.tasktracker.mapper;

import com.denis.svetikov.tasktracker.dto.model.UserTaskDto;
import com.denis.svetikov.tasktracker.model.UserTask;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class UserTaskMapper  {

    @Autowired
    private ModelMapper mapper;

    public UserTaskDto toDto(UserTask entity) {
        return Objects.isNull(entity)
                ? null
                : mapper.map(entity, UserTaskDto.class);
    }


    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(UserTask.class, UserTaskDto.class)
                .addMappings(m -> m.skip(UserTaskDto::setUserId))
                .addMappings(m -> m.skip(UserTaskDto::setUsername))
                .addMappings(m -> m.skip(UserTaskDto::setFirstName))
                .addMappings(m -> m.skip(UserTaskDto::setLastName))
                .addMappings(m -> m.skip(UserTaskDto::setTaskId))
                .addMappings(m -> m.skip(UserTaskDto::setTitle))
                .addMappings(m -> m.skip(UserTaskDto::setStatus))
                .setPostConverter(toDtoConverter());
    }


    private Converter<UserTask, UserTaskDto> toDtoConverter() {
        return context -> {
            UserTask source = context.getSource();
            UserTaskDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }


    private void mapSpecificFields(UserTask source, UserTaskDto destination) {

        destination.setUserId(Objects.isNull(source) || Objects.isNull(source.getUser().getId()) ? null : source.getUser().getId());
        destination.setUsername(Objects.isNull(source) || Objects.isNull(source.getUser().getUsername()) ? null : source.getUser().getUsername());
        destination.setFirstName(Objects.isNull(source) || Objects.isNull(source.getUser().getFirstName()) ? null : source.getUser().getFirstName());
        destination.setLastName(Objects.isNull(source) || Objects.isNull(source.getUser().getLastName()) ? null : source.getUser().getLastName());
        destination.setTaskId(Objects.isNull(source) || Objects.isNull(source.getTask().getId()) ? null : source.getTask().getId());
        destination.setTitle(Objects.isNull(source) || Objects.isNull(source.getTask().getTitle()) ? null : source.getTask().getTitle());
        destination.setStatus(Objects.isNull(source) || Objects.isNull(source.getTask().getStatus()) ? null : source.getTask().getStatus().getStatus());
    }


}
