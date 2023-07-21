package com.sprinklr.botintern;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class RequestBody {

    public JSONObject requestObject(String body){
        if(body==""){
            return null;
        }

        try {
            int index=body.indexOf(':');
            body=body.substring(index+1,body.length()-1);
            System.out.println("Body : "+body);
            JSONObject requestBody=mapBody(body);
            System.out.println("requestBody : "+requestBody);
            return requestBody;

        }catch (Exception ex){
            System.out.println("Body string problem");
            return null;
        }
    }
    private JSONObject mapBody(String body){
        try {
            JSONParser jsonParser=new JSONParser();
            JSONObject requestBody = (JSONObject) jsonParser.parse(body);
            return  requestBody;
        }catch (Exception ex){
            return null;
        }
    }
}
