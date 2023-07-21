package com.sprinklr.botintern.Red.Requests.welcomePath;


import com.sprinklr.botintern.Database.Services.UserDataModelRepoService;
import com.sprinklr.botintern.Database.models.UserDataModel;
import com.sprinklr.botintern.Red.Requests.GlobalBaseUri;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class ReachWelcome {

    @Autowired
    UserDataModelRepoService userDataModelRepoService;

    @Autowired
    GlobalBaseUri globalBaseUri;


    public void enterOtp(String userId,String otp,String otpPageLink){

//        RestAssured.given().redirects().follow(false);

        //isme bhi saala query kaha se aa rhi h
        String url=otpPageLink.replace(" ","&");
        String requestBody= "totp="+otp+"&login=Log%20In";

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
        //cookie bhi set karni h AUTH_SESSION_ID aur KC_RESTART wali

        System.out.println(response.getBody().asString());
        String loginUrl=response.getHeaders().getValue("Location");
        int responseCode=response.getStatusCode();

        Cookie sessionCookieOld=new Cookie.Builder("SESSION",user.getSessionCookie()).build();
        if(responseCode>=300 && responseCode<400){
            response = RestAssured.given()
                    .cookie(sessionCookieOld)
                    .headers("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")
                    .get(loginUrl);
        }

        Map<String,String> cookies=response.getCookies();
        String sessionCookie=cookies.get("SESSION");
        //isse cookie ko database m daal do-- SESSION cookie update

        userDataModelRepoService.updateSessionCookie(userId,sessionCookie);

        loginUrl=response.getHeaders().getValue("Location");


        RestAssured.baseURI= globalBaseUri.baseUriOfEnv;
        responseCode=response.getStatusCode();

        user=userDataModelRepoService.getAllCookies(userId);

        Cookie sessionCookieNew=new Cookie.Builder("SESSION",user.getSessionCookie()).build();
        if(responseCode>=300 && responseCode<400){
            response = RestAssured.given()
                    .cookie(sessionCookieNew)
                    .get(loginUrl);
        }


        loginUrl=response.getHeaders().getValue("Location");
        responseCode=response.getStatusCode();
        if(responseCode>=300 && responseCode<400){
            response = RestAssured.given()
                    .cookie(sessionCookieNew)
                    .get(loginUrl);
        }

        String sprRedCookie=response.getCookie("SPR_RED_COOKIE");
        userDataModelRepoService.updateSprRedCookie(userId,sprRedCookie);

        //welcome pohoch gye
        //cookie mil gye h
        /// SPR_RED_COOKIE save kar lo ab reply ki
    }
}
