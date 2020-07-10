package com.denis.svetikov.tasktracker.mapper;

import com.denis.svetikov.tasktracker.dto.model.TaskDto;
import com.denis.svetikov.tasktracker.model.Task;
import com.denis.svetikov.tasktracker.service.TaskStatusService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class TaskMapper extends AbstractMapper<Task, TaskDto> {


    private final ModelMapper mapper;
    private final TaskStatusService taskStatusService;


    @Autowired
    public TaskMapper(ModelMapper modelMapper, TaskStatusService taskStatusService) {
        super(Task.class, TaskDto.class);
        this.mapper = modelMapper;
        this.taskStatusService = taskStatusService;
    }


    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Task.class, TaskDto.class)
                .addMappings(m -> m.skip(TaskDto::setStatusId))
                .setPostConverter(toDtoConverter());

        mapper.createTypeMap(TaskDto.class, Task.class)
                .addMappings(m -> m.skip(Task::setUser))
                .addMappings(m -> m.skip(Task::setStatus))
                .setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(Task source, TaskDto destination) {
        destination.setStatusId(Objects.isNull(source) || Objects.isNull(source.getStatus().getId()) ? null : source.getStatus().getId());
    }

    @Override
    void mapSpecificFields(TaskDto source, Task destination) {
        if (source.getStatusId() != null) {
            destination.setStatus(taskStatusService.getTaskStatusById(source.getStatusId()));
        }

    }


}
