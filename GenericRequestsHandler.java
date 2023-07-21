package com.sprinklr.botintern;

import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.sprinklr.botintern.BotStringConstants.*;

@Component
public class GenericRequestsHandler {

    @Autowired
    QueryString queryString;
    @Autowired
    ApiCall apiCall;
    @Autowired
    HeaderClass headerClass;

    public String response(String request, String method) {

        //SEPARATE LINK
        int index = request.indexOf(OPEN_CURLY);

        if (index == -1) {
            index = request.length();
        }
        String link = request.substring(0, index);
        link = Trimmer.removeWhiteSpaces(link);

        String parameters = request.substring(index, request.length());

        //SEPARATE QUERY ,REQUEST AND HEADER BODY
        String query = EMPTY_STRING;
        String body = EMPTY_STRING;
        String header = EMPTY_STRING;

        String currentBlock = "";
        int i = 0;

        for (int x = 0; x < 3; x++) {
            if (parameters.length() > 1) {
                i = breakRequest(parameters);
                currentBlock = parameters.substring(0, i);
                //ASSIGN ONE OF THE 2
                if (currentBlock.contains(REQUEST_AS_STRING)) {
                    body = currentBlock;
                } else if (currentBlock.contains(HEADER_AS_STRING)) {
                    header = currentBlock;
                } else {
                    query = currentBlock;
                }
                parameters = parameters.substring(i, parameters.length());
                parameters = Trimmer.removeWhiteSpaces(parameters);
            } else {
                break;
            }
        }

        //TRIM THE STRINGS
        query = Trimmer.removeWhiteSpaces(query);
        body = Trimmer.removeWhiteSpaces(body);
        header = Trimmer.removeWhiteSpaces(header);


        //DESIRED FORMAT
        query = queryString.queryEndpoint(query);
        if (query.equals(WRONG_QUERY_FORMAT)) {
            return query;
        }

        JSONObject headerObject = headerClass.headerMaker(header);
        if (headerObject == null && StringUtils.isNotEmpty(header)) {
            return WRONG_HEADER_FORMAT;
        }
        link += query;

        //MAKING THE CALL
        return apiCall.makeCall(link, method.toUpperCase(), body, headerObject);
    }


    private int breakRequest(String parameters) {
        int i;
        int openingBrackets = 0;
        if (parameters.charAt(0) != OPEN_CURLY) {
            return 0;
        }
        for (i = 0; i < parameters.length(); i++) {
            char currentChar = parameters.charAt(i);
            if (currentChar == OPEN_CURLY) {
                openingBrackets++;
            }
            if (parameters.charAt(i) == CLOSE_CURLY) {
                openingBrackets--;
                if (openingBrackets == 0) {
                    break;
                }
            }
        }
        return i + 1;
    }
}
