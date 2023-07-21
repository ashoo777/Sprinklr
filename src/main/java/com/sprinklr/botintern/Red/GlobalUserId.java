package com.sprinklr.botintern.Red;

import org.springframework.stereotype.Component;

@Component
public class GlobalUserId {


    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
