package com.javahunter.multipleloggers.exception;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(){}
    public UserAlreadyExistsException(String fieldName,String fieldValue){
        super(String.format("User with %s equals %s already exist",fieldName,fieldValue));
    }
}
