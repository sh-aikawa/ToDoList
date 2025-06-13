package com.example.todolist.exception;

public class UniqueException extends RuntimeException {
    public UniqueException(String message){
        super(message);
    }
}
