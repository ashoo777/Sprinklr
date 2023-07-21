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
import net.minidev.json.parser.JSONParser;
//import org.json.JSONArray;
//import org.json.JSONObject;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PartnerNameById {

    @Autowired
    UserDataModelRepoService userDataModelRepoService;

    @Autowired
    GlobalBaseUri globalBaseUri;

    @Autowired
    GlobalUserId globalUserId;

    public String partnerDetailsByName(String name){

        String url= globalBaseUri.baseUriOfEnv+"/api/v1/partner/findByPartnerNameAcrossAllEnvs";

//        String url="https://qa4-red.sprinklr.com/ext-office/api/v1/sprinklr/partner/findById";
//        String session= userDataModelRepoService.getSessionCookie(globalUserId.getUserId());
//        if(session==null){
//            return "Session Not Made"+ "<br>"+ "Need To Login!!";
//        }

        String session="d725b76c-5e41-4e03-b955-f2174d6f669d";

//        Cookie sprRedCookie= new Cookie.Builder("SPR_RED_COOKIE",sprRed).build();
        Cookie sessionCookie=new Cookie.Builder("SESSION",session).build();
        Response response= RestAssured
                .given()
                .queryParam("id","400002")
//                .queryParam("username","akshit.rewliya%40sprinklr.com")
//                .cookie(sprRedCookie)
                .cookie(sessionCookie)
//                .headers("X-RED-API-TOKEN","ke2an1N0kc3a17LhywOwRSkGcvJQthk73BZ6V6NvHlCZ3bayJeDfihyqz98YilWj89OoUukvtRmdlKU7qDt31y8T524g7Nzm2uMZ")
                .get(url);

        int responseCode= response.getStatusCode();
        if (responseCode>400){
            userDataModelRepoService.deleteCookies(globalUserId.getUserId());
            return "Cookie exhausted!!"+"<br>"+"Please Relogin";
        }

        String responseBody=response.getBody().asString();
        System.out.println(responseBody);
        responseBody=filterResponse(responseBody);
        responseBody= FormatResponse.indentResponse(responseBody,"JSON");
        return responseBody;
    }


    private String filterResponse(String responseBody){
        try{
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(responseBody);
            System.out.println("JSONObject : "+jsonObject);

            System.out.println(jsonObject);
            JSONObject returnObject=new JSONObject();

            jsonObject.keySet().forEach(key -> {

                if (key.equals("qa6")){
                    //returnObject.put(key,jsonObject.getAsString(key));
                }

                else {
                    JSONArray jsonArray = (JSONArray) jsonObject.get(key);

                    System.out.println("JSONArray : "+jsonArray);

                    System.out.println(jsonArray);
                    JSONArray newFilteredArray=new JSONArray();
                    for (int i = 0; i < jsonArray.size(); i++) {

                        // store each object in JSONObject
                        JSONObject eachObject = (JSONObject) jsonArray.get(i);

                        String partnerId= eachObject.getAsString("partnerId");
                        String partnerName=eachObject.getAsString("partnerName");
                        String partnerDesc=eachObject.getAsString("partnerDesc");
                        Boolean isDeleted= (Boolean) eachObject.get("isDeleted");


                        JSONObject newFilteredObject=new JSONObject();
                        newFilteredObject.put("partnerId",partnerId);
                        newFilteredObject.put("partnerName",partnerName);
                        newFilteredObject.put("partnerDesc",partnerDesc);
                        newFilteredObject.put("isDeleted",isDeleted);

                        newFilteredArray.appendElement(newFilteredObject);
                        // get field value from JSONObject using get() method
                    }
                    returnObject.put(key,newFilteredArray);
                }

            });

            return  returnObject.toString();

        }catch (Exception ex){
            System.out.println(ex.getStackTrace());
            System.out.println(ex.getMessage());
        }

        return "error in filtering";
    }
}
