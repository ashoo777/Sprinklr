package com.sprinklr.botintern.Red.Requests.FinalApis;

import com.sprinklr.botintern.Formats.FormatResponse;
import com.sprinklr.botintern.Red.ExternalUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.sprinklr.botintern.BotStringConstants.*;

@Component
public class ClientDetailsById {

    @Autowired
    ExternalUtils externalUtils;

    public String clientDetails(String id) {

        String url = externalUtils.baseLink + CLIENT_BY_ID_LINK;

        Response response = RestAssured
                .given()
                .queryParam(ID_FIELD, id)
                .queryParam(USERNAME, externalUtils.username)
                .headers(API_TOKEN, externalUtils.authToken)
                .get(url);

        int responseCode = response.getStatusCode();
        if (responseCode > 400) {
            return ERROR_4_XX;
        }

        String responseBody = response.getBody().asString();
        responseBody = filterResponseForId(responseBody);
        responseBody = FormatResponse.indentResponse(responseBody, JSON);
        return responseBody;
    }

    private String filterResponseForId(String responseBody) {
        String clientFields = externalUtils.clientDetails;
        String[] fields = clientFields.split(COMMA);

        try {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(responseBody);
            JSONArray newFilteredArray = new JSONArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject eachObject = (JSONObject) jsonArray.get(i);
                JSONObject newFilteredObject = new JSONObject();

                for (int j = 0; j < fields.length; j++) {
                    String field = fields[j];
                    String valueOfField = eachObject.getAsString(field);
                    newFilteredObject.put(field, valueOfField);
                }

                newFilteredArray.appendElement(newFilteredObject);
            }
            return newFilteredArray.toJSONString();
        } catch (Exception ex) {
            System.out.println(ex.getStackTrace());
            System.out.println(ex.getMessage());
            return FILTER_ERROR;
        }
    }
}
