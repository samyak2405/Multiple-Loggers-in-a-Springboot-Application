package com.javahunter.multipleloggers.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){
        super(message);
    }
    public ResourceNotFoundException(String fieldName,String fieldValue){
        super(String.format("Resource %s not found with value %s",fieldName,fieldValue));
    }
}
