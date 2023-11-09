package com.javahunter.multipleloggers.service;

import com.javahunter.multipleloggers.dto.RequestDto;
import com.javahunter.multipleloggers.dto.ResponseDto;
import com.javahunter.multipleloggers.entity.User;

import java.util.List;

public interface UserService {

    public User saveUser(RequestDto requestDto);

    public ResponseDto getUserByEmail(String email);

    public List<ResponseDto> getAllUsers();

    public boolean updateUser(RequestDto requestDto);

    public boolean userDeleted(String email);
}
