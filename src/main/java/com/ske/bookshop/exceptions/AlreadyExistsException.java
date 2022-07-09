package com.ske.bookshop.exceptions;


public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String name){
        super("Object: <" + name + "> already exist.");
    }
}
