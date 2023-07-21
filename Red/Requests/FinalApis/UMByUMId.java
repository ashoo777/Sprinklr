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
public class UMByUMId {

    @Autowired
    ExternalUtils externalUtils;

    public String universalMessageById(String umId, String partnerId) {

        String url = externalUtils.baseLink + UM_BY_ID_LINK;

        Response response = RestAssured
                .given()
                .queryParam(PARTNER_ID, partnerId)
                .queryParam(UMID_FIELD, umId)
                .queryParam(QUERY_FIELD, MESSAGE)
                .queryParam(USERNAME, externalUtils.username)
                .headers(API_TOKEN, externalUtils.authToken)
                .get(url);


        int responseCode = response.getStatusCode();
        if (responseCode > 400) {
            return ERROR_4_XX;
        }
        String responseBody = response.getBody().asString();
        responseBody = filterResponse(responseBody);

        responseBody = FormatResponse.indentResponse(responseBody, JSON);
        return responseBody;
    }

    private String filterResponse(String responseBody) {

        String umFields = externalUtils.umDetails;
        String[] fields = umFields.split(COMMA);
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(responseBody);
            JSONObject allHits = (JSONObject) jsonObject.get(HITS);

            int totalHits = (int) allHits.getAsNumber(TOTAL);
            JSONArray hitsArray = (JSONArray) allHits.get(HITS);

            JSONObject returnObject = new JSONObject();
            JSONArray newFilteredArray = new JSONArray();
            for (int i = 0; i < totalHits; i++) {
                JSONObject eachObject = (JSONObject) hitsArray.get(i);
                eachObject = (JSONObject) eachObject.get(SOURCE);

                JSONObject newFilteredObject = new JSONObject();

                for (int j = 0; j < fields.length; j++) {
                    String field = fields[j];
                    String valueOfField = eachObject.getAsString(field);
                    newFilteredObject.put(field, valueOfField);
                }

                newFilteredArray.appendElement(newFilteredObject);
            }
            returnObject.put(HITS, newFilteredArray);

            return returnObject.toString();

        } catch (Exception ex) {
            System.out.println(ex.getStackTrace());
            System.out.println(ex.getMessage());
            return FILTER_ERROR;
        }
    }
}
