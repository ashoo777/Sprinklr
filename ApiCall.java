package com.sprinklr.botintern;

import com.sprinklr.botintern.Formats.FormatResponse;
import com.sprinklr.botintern.Formats.KnowFormat;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.sprinklr.botintern.BotStringConstants.EMPTY_STRING;
import static com.sprinklr.botintern.BotStringConstants.GENERIC_API_FAILURE;

@Component
@Scope("singleton")
public class ApiCall {
    public static Boolean success = false;

    public String makeCall(String link, String method, String requestBody, JSONObject headerObject) {
        String replyString = EMPTY_STRING;
        try {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);       // set the method of call


            // set the headers from headerObject passed as a JSONObject
            if (headerObject != null) {
                for (String key : headerObject.keySet()) {
                    conn.setRequestProperty(key, headerObject.getAsString(key));
                }
            }

            // write request body if given
            if (StringUtils.isNotEmpty(requestBody)) {
                conn.setDoOutput(true);
                DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
                outputStream.writeBytes(requestBody);
                outputStream.flush();
                outputStream.close();
            }

            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode >= 200 && responseCode < 300) {
                ApiCall.success = true;
            } else {
                throw new Exception("Error occurred while making api call");
                // if api request fails then message is sent to user to recheck the request
            }

            // STORE THE INPUT
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inline;
            String response = EMPTY_STRING;
            while ((inline = reader.readLine()) != null) {
                response += inline;
            }
            reader.close();
            conn.disconnect();

            String format = KnowFormat.typeOfFormat(response);
            replyString = FormatResponse.indentResponse(response, format);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
            replyString = GENERIC_API_FAILURE;
        } finally {
            return replyString;
        }
    }
}
