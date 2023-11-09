package com.javahunter.multipleloggers.mapper;

import com.javahunter.multipleloggers.dto.RequestDto;
import com.javahunter.multipleloggers.dto.ResponseDto;
import com.javahunter.multipleloggers.entity.User;

public class UserMapper {

    public static User mapToUser(User user,RequestDto requestDto){
        user.setName(requestDto.getName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());
        user.setLocation(requestDto.getLocation());
        return user;
    }

    public static ResponseDto mapToResponseDto(User user,ResponseDto responseDto){
        responseDto.setName(user.getName());
        responseDto.setEmail(user.getEmail());
        responseDto.setLocation(user.getLocation());
        return responseDto;
    }
}
