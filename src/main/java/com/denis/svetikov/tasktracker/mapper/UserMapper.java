package com.denis.svetikov.tasktracker.mapper;

import com.denis.svetikov.tasktracker.dto.model.UserDto;
import com.denis.svetikov.tasktracker.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<User,UserDto> {


    private final ModelMapper modelMapper;


    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        super(User.class, UserDto.class);
        this.modelMapper = modelMapper;
    }

    @Override
    void mapSpecificFields(User source, UserDto destination) {
        super.mapSpecificFields(source, destination);
    }

    @Override
    void mapSpecificFields(UserDto source, User destination) {
        super.mapSpecificFields(source, destination);
    }
}
