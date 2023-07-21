package com.sprinklr.botintern.Formats;

import net.minidev.json.parser.JSONParser;
import org.json.JSONObject;

public class JsonFormat {

    public static String prettyFormat(String input){

        String jsonString=getJsonString(input);
        return jsonString;
    }


    private static String getJsonString(String input){
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(input);
            String jsonString=json.toString();
            return jsonString;
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
        return null;
    }
}
