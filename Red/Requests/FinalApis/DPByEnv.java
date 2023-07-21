package com.sprinklr.botintern.Red.Requests.FinalApis;

import com.sprinklr.botintern.Formats.FormatResponse;
import com.sprinklr.botintern.Red.ExternalUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.sprinklr.botintern.BotStringConstants.*;

@Component
public class DPByEnv {
    @Autowired
    ExternalUtils externalUtils;

    public String dynamicPropertyValues(String propertyName, String partnerId) {

        String url = externalUtils.baseLink + DP_LINK;
        Response response = RestAssured
                .given()
                .queryParam(USERNAME, externalUtils.username)
                .headers(API_TOKEN, externalUtils.authToken)
                .queryParam(QUERY_FIELD, BOTH_QUERY_FIELD_PARAM)
                .queryParam(PARTNER_ID, partnerId)
                .queryParam(PROPERTY_NAME, propertyName)
                .get(url);
        int responseCode = response.getStatusCode();
        if (responseCode > 400) {
            return ERROR_4_XX;
        }
        String responseBody = response.getBody().asString();

        responseBody = FormatResponse.indentResponse(responseBody, JSON);
        return responseBody;
    }
}
