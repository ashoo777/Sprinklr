package com.sprinklr.botintern.Database.models;

import java.lang.annotation.Documented;
import java.text.AttributedString;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Document("Users")
public class UserDataModel {

    @Id
    public String userId;
    private String sessionCookie;
    private String sprRedCookie;
    private String authSessionIdCookie;
    private String kcRestartCookie;

    private String environment;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getSessionCookie() {
        return sessionCookie;
    }

    public void setSessionCookie(String sessionCookie) {
        this.sessionCookie = sessionCookie;
    }

    public String getSprRedCookie() {
        return sprRedCookie;
    }

    public void setSprRedCookie(String sprRedCookie) {
        this.sprRedCookie = sprRedCookie;
    }

    public String getAuthSessionIdCookie() {
        return authSessionIdCookie;
    }

    public void setAuthSessionIdCookie(String authSessionIdCookie) {
        this.authSessionIdCookie = authSessionIdCookie;
    }

    public String getKcRestartCookie() {
        return kcRestartCookie;
    }

    public void setKcRestartCookie(String kcRestartCookie) {
        this.kcRestartCookie = kcRestartCookie;
    }


    public UserDataModel(String userId,String env){
        this.userId=userId;
        sessionCookie=null;
        sprRedCookie=null;
        authSessionIdCookie=null;
        kcRestartCookie=null;
        environment=env;
    }
}
