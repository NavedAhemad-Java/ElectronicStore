package com.bikkadIt.electronic.store.exception;

import lombok.Builder;

public class EmailNotFoundException extends  RuntimeException{
    @Builder
    public EmailNotFoundException(){
        super("Email Not Found!!");

    }

    public EmailNotFoundException(String message) {
        super(message);
    }
}
