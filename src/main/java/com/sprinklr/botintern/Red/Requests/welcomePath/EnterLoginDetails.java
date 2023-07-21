package com.sprinklr.botintern.Red.Requests.welcomePath;


import com.sprinklr.botintern.Database.Services.UserDataModelRepoService;
import com.sprinklr.botintern.Database.models.UserDataModel;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Component
public class EnterLoginDetails {
    @Autowired
    UserDataModelRepoService userDataModelRepoService;

    public String postLogin(String userId,String id,String pass,String loginPageLink){

        String url=loginPageLink.replace(" ","&");
        System.out.println(url);
        String requestBody= "username="+id+"&password="+pass;

        UserDataModel user=userDataModelRepoService.getAllCookies(userId);

        String authSessionId=user.getAuthSessionIdCookie();
        String kcRestartCookie=user.getKcRestartCookie();

        Cookie authCookie= new Cookie.Builder("AUTH_SESSION_ID",authSessionId).build();
        Cookie kcCookie=new Cookie.Builder("KC_RESTART",kcRestartCookie).build();
        Response response= RestAssured
                .given()
                .cookie(authCookie)
                .cookie(kcCookie)
                .contentType(ContentType.URLENC)
                .body(requestBody)
                .post(url);


        int contentLength = Integer.parseInt(response.header("Content-Length"));
        String htmlResponse=response.getBody().asString();
        Document document= Jsoup.parse(htmlResponse);

        System.out.println(document);
        // Wrong Credentials
        if(contentLength==4770){
            return "Wrong!!";
        }

        String otpPageLink="";
        Elements formTags = document.getElementsByTag("form");
        for (Element formTag : formTags) {
            // Extracting the 'action' attribute value from each 'form' tag
            otpPageLink= formTag.attr("action");
        }

        System.out.println(otpPageLink);
        return otpPageLink;
    }


}
