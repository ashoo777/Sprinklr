package com.sprinklr.botintern.Red.Requests.FinalApis;


import com.sprinklr.botintern.Database.Services.UserDataModelRepoService;
import com.sprinklr.botintern.Database.models.UserDataModel;
import com.sprinklr.botintern.Database.Repository.UserDataModelRepo;
import com.sprinklr.botintern.Formats.FormatResponse;
import com.sprinklr.botintern.Red.GlobalUserId;
import com.sprinklr.botintern.Red.Requests.GlobalBaseUri;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DPByEnv {

    @Autowired
    UserDataModelRepoService userDataModelRepoService;

    @Autowired
    GlobalUserId globalUserId;

    @Autowired
    GlobalBaseUri globalBaseUri;

    public String dynamicPropertyValues(String propertyName,String partnerId){

        String url= globalBaseUri.baseUriOfEnv+"/api/v1/property/findDynamicPropertiesByPartnerIdOrPropertyName";
//        String session= userDataModelRepoService.getSessionCookie(globalUserId.getUserId());
//        if(session==null){
//            return "Session Not Made"+ "<br>"+ "Need To Login!!";
//        }

        String session="d725b76c-5e41-4e03-b955-f2174d6f669d";
//        Cookie sprRedCookie= new Cookie.Builder("SPR_RED_COOKIE",sprRed).build();
        Cookie sessionCookie=new Cookie.Builder("SESSION",session).build();
        Response response= RestAssured
                .given()
                .queryParam("queryField","both")
                .queryParam("partnerId",partnerId)
                .queryParam("propertyName",propertyName)
//                .cookie(sprRedCookie)
                .cookie(sessionCookie)
                .get(url);
        int responseCode= response.getStatusCode();
        if (responseCode>400){
            userDataModelRepoService.deleteCookies(globalUserId.getUserId());
            return "Cookie exhausted!!"+"<br>"+"Please Relogin";
        }
        String responseBody=response.getBody().asString();

        System.out.println("responseBody  : "+responseBody);

        responseBody= FormatResponse.indentResponse(responseBody,"JSON");

        System.out.println("responsebody : "+responseBody);
        System.out.println(responseBody);
        return responseBody;
    }
}
