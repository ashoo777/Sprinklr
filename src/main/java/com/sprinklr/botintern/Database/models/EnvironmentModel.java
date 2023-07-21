package com.sprinklr.botintern.Database.models;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Document(collection = "Environment")
@Component
public class EnvironmentModel {
    @Id
    private String env;
    private String link;

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public EnvironmentModel(String env,String link){
        this.env=env;
        this.link=link;
    }

    public EnvironmentModel(){
        System.out.println("no args called");
    }
}
