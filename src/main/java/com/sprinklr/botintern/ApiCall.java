package com.sprinklr.botintern;

import com.sprinklr.botintern.Formats.FormatResponse;
import com.sprinklr.botintern.Formats.KnowFormat;
import net.minidev.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Component
@Scope("singleton")
public class ApiCall {
    public static Boolean success = false;

    public String makeCall(String link,String method,String requestBody,JSONObject headerObject){

        try {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);       //// change the method of call


            // set the headers from headerObject passed as a JSONObject
            if(headerObject!=null){
                for (String key:headerObject.keySet()){
                    conn.setRequestProperty(key,headerObject.getAsString(key));
                }
            }

            // write request body if given
            if(requestBody!=""){
                conn.setDoOutput(true);
                DataOutputStream outputStream=new DataOutputStream(conn.getOutputStream());
                outputStream.writeBytes(requestBody);
                outputStream.flush();
                outputStream.close();
            }

            conn.connect();

            int responseCode=conn.getResponseCode();

            // get headers
            Map<String, List<String>> map = conn.getHeaderFields();
            System.out.println(map.toString());

            if(responseCode>=200 && responseCode<300){
                ApiCall.success=true;
            }

            // STORE THE INPUT
            BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inline;
            String response="";
            while ((inline = reader.readLine())!=null){
                response+=inline;
            }
            reader.close();

            System.out.println("Response : "+response);
            conn.disconnect();

            String format= KnowFormat.typeOfFormat(response);
            System.out.println(format+"..............");
            String replyString= FormatResponse.indentResponse(response,format);
            return replyString;

        }catch (Exception ex){
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
        }
        return "Recheck your parameters and request";
    }

}
