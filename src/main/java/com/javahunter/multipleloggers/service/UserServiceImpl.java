package com.javahunter.multipleloggers.service;


import com.javahunter.multipleloggers.dto.RequestDto;
import com.javahunter.multipleloggers.dto.ResponseDto;
import com.javahunter.multipleloggers.entity.User;
import com.javahunter.multipleloggers.exception.ResourceNotFoundException;
import com.javahunter.multipleloggers.exception.UserAlreadyExistsException;
import com.javahunter.multipleloggers.mapper.UserMapper;
import com.javahunter.multipleloggers.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger serverLogger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    /**
     * @param requestDto
     * @return
     */
    @Override
    public User saveUser(RequestDto requestDto) {
        serverLogger.info("Request hitting Save User service");
        if(userRepository.existsByEmail(requestDto.getEmail())){
            serverLogger.debug("User already present");
            throw new UserAlreadyExistsException("Email",requestDto.getEmail());
        }
        User user = new User();
        UserMapper.mapToUser(user, requestDto);
        return userRepository.save(user);
    }

    /**
     * @param email
     * @return
     */
    @Override
    public ResponseDto getUserByEmail(String email) {
        serverLogger.info("Request hitting the get user by email service");
        if(!userRepository.existsByEmail(email)){
            serverLogger.debug("User with email {} not found",email);
            throw new ResourceNotFoundException("Email",email);
        }
        User user = userRepository.findByEmail(email);
        ResponseDto responseDto = new ResponseDto();
        responseDto = UserMapper.mapToResponseDto(user,responseDto);
        return responseDto;
    }

    /**
     * @return
     */
    @Override
    public List<ResponseDto> getAllUsers() {
        serverLogger.info("Request hitting the get all user service");
        if(userRepository.count()==0){
            serverLogger.debug("No user present at all");
            throw new ResourceNotFoundException("No user present");
        }
        List<User> users = userRepository.findAll();
        List<ResponseDto> responseDtos = new ArrayList<>();
        users.forEach(user->{
            ResponseDto responseDto = new ResponseDto();
            responseDtos.add(UserMapper.mapToResponseDto(user,responseDto));
        });
        return responseDtos;
    }

    /**
     * @param requestDto
     * @return
     */
    @Override
    public boolean updateUser(RequestDto requestDto) {
        serverLogger.info("Request hitting the update  user by email service");
        if(userRepository.existsByEmail(requestDto.getEmail())){
            serverLogger.debug("User with email {} not present",requestDto.getEmail());
            throw new ResourceNotFoundException("Email",requestDto.getEmail());
        }
        User user = userRepository.findByEmail(requestDto.getEmail());
        User updatedUser = UserMapper.mapToUser(user,requestDto);
        userRepository.save(updatedUser);
        return true;
    }

    /**
     * @param email
     * @return
     */
    @Override
    public boolean userDeleted(String email) {
        serverLogger.info("Request hitting the update  user by email service");
        if(userRepository.existsByEmail(email)){
            serverLogger.debug("User with email {} not present",email);
            throw new ResourceNotFoundException("Email",email);
        }
        userRepository.deleteByEmail(email);
        return true;
    }
}
