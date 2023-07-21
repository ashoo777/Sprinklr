package com.sprinklr.botintern.Red.Requests.welcomePath;

import com.sprinklr.botintern.Database.Services.UserDataModelRepoService;
import com.sprinklr.botintern.Red.GlobalUserId;
import com.sprinklr.botintern.Red.Requests.GlobalBaseUri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HomePage {

    @Autowired
    UserDataModelRepoService userDataModelRepoService;

    @Autowired
    GlobalUserId globalUserId;

    @Autowired
    GlobalBaseUri globalBaseUri;

    public String homePageLoginButton(){

        String userId=globalUserId.getUserId();
        // yaha se start hoga
        try {

            String baseUri= globalBaseUri.baseUriOfEnv;
            Response response = RestAssured.given()
                    .redirects().follow(false)
                    .get(baseUri+"/");

            Headers headers=response.getHeaders();
            String loginUrl=baseUri+response.getHeaders().getValue("Location");

            //......../oauth2/authorization/keycloak
            int responseCode=response.getStatusCode();
            if(responseCode>=300 && responseCode<400){
                response = RestAssured.given()
                        .redirects().follow(false)
                        .get(loginUrl);
            }

            //SESSION WALI COOKIE DATABASE M DAAL DIO

            Map<String,String> cookies=response.getCookies();
            String sessionCookie=cookies.get("SESSION");

            System.out.println("start wali : "+sessionCookie);
            userDataModelRepoService.updateSessionCookie(userId,sessionCookie);

            headers=response.getHeaders();
            loginUrl=response.getHeaders().getValue("Location");


            // page wala link jisme input dete h
            responseCode=response.getStatusCode();
            if(responseCode>=300 && responseCode<400){
                response = RestAssured.given()
                        .redirects().follow(false)
                        .get(loginUrl);
            }


            Map<String,String> authCookies=response.getCookies();
            userDataModelRepoService.updateAuthAndKc(userId,authCookies.get("AUTH_SESSION_ID"),authCookies.get("KC_RESTART"));
            String htmlResponse=response.getBody().asString();


            Document document= Jsoup.parse(htmlResponse);
            String authPageLink="";
            Elements formTags = document.getElementsByTag("form");


            for (Element formTag : formTags) {
                // Extracting the 'action' attribute value from each 'form' tag
                authPageLink= formTag.attr("action");
            }
            return authPageLink;

        }catch (Exception ex){
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
        }
        return "Login failed"+"<br>"+"Try again later";

    }
}
