package com.sprinklr.botintern;

import com.sprinklr.botintern.Red.RequestController;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class StringProcessor {
    @Autowired
    QueryString queryString;
    @Autowired
    ApiCall apiCall;
    @Autowired
    HeaderClass headerClass;

    public String process(String message) {

        //SEPARATE METHOD
        int index = message.indexOf(" ");
        String method="";
        String request="";
        if(index==-1){
            method=message;
        }else {
            method = message.substring(0, index);
            request = message.substring(index + 1, message.length());
        }

        if(method.equals("Red")){
            return method;
        }
        //SEPARATE LINK
        index = request.indexOf("{");

        if (index == -1) {
            index = request.length();
        }
        String link = request.substring(0, index);
        link = link.trim();

        String parameters = request.substring(index, request.length());

        //SEPARATE QUERY ,REQUEST AND HEADER BODY
        String query = "";
        String body = "";
        String header = "";

        String currentBlock = "";
        int i = 0;

        for (int x=0;x<3;x++){
            if (parameters.length() > 1) {
                i = breakRequest(parameters);
                currentBlock = parameters.substring(0, i);
                //ASSIGN ONE OF THE 2
                if (currentBlock.contains("request")) {
                    body = currentBlock;
                } else if (currentBlock.contains("header")) {
                    header = currentBlock;
                } else {
                    query = currentBlock;
                }
                parameters = parameters.substring(i, parameters.length());
                parameters = parameters.trim();
            }else {
                break;
            }
        }

        //STORE KEYWORDS
        ArrayList<String> keywords = new ArrayList<String>();

        for (String val : parameters.split(" ")) {
            keywords.add(val);
        }

        //TRIM THE STRINGS
        query = query.trim();
        body = body.trim();
        header = header.trim();


        //DESIRED FORMAT
        query = queryString.queryEndpoint(query);
        JSONObject headerObject = headerClass.headerMaker(header);
        link += query;

        //MAKING THE CALL
        String reply = apiCall.makeCall(link, method.toUpperCase(), body, headerObject);
        return reply;
    }


    private int breakRequest(String parameters) {
        int i;
        int openingBrackets = 0;
        if (parameters.charAt(0) != '{') {
            return 0;
        }
        for (i = 0; i < parameters.length(); i++) {
            char currentChar = parameters.charAt(i);
            if (currentChar == '{') {
                openingBrackets++;
            }
            if (parameters.charAt(i) == '}') {
                openingBrackets--;
                if (openingBrackets == 0) {
                    break;
                }
            }
        }
        return i + 1;
    }

}
