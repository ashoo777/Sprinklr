package com.sprinklr.botintern;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("singleton")
public class HeaderClass {

    public JSONObject headerMaker(String header){
        if(header==""){
            return null;
        }
        try {
            int index=header.indexOf(':');
            header=header.substring(index+1,header.length()-1);
            JSONObject headerObject=mapHeader(header);
            return headerObject;

        }catch (Exception ex){
            System.out.println("Body string problem");
            return null;
        }
    }
    private JSONObject mapHeader(String body){
        try {
            JSONParser jsonParser=new JSONParser();
            JSONObject headerObject = (JSONObject) jsonParser.parse(body);
            return  headerObject;
        }catch (Exception ex){
            return null;
        }
    }

}
