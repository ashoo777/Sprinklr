package com.sprinklr.botintern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.sprinklr.botintern.BotStringConstants.*;

@Component
public class StringProcessor {
    @Autowired
    GenericRequestsHandler genericRequestsHandler;

    public String process(String message) {

        //SEPARATE METHOD
        int index = message.indexOf(SPACE);
        String method;
        String request = EMPTY_STRING;

        if (index == -1) {
            method = message;
        } else {
            method = message.substring(0, index);
            request = message.substring(index + 1, message.length());
        }
        // if method is Red then it will be handled separately
        if (method.equals(RED)) {
            return method;
        }
        return genericRequestsHandler.response(request, method);
    }
}
