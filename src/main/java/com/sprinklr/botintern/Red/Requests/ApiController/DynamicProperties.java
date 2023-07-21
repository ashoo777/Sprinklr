package com.sprinklr.botintern.Red.Requests.ApiController;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.schema.Activity;
import com.sprinklr.botintern.Red.Requests.FinalApis.DPByEnv;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class DynamicProperties {

    @Autowired
    DPByEnv dpByEnv;

    public Activity getResult(TurnContext turnContext, String request){

        String replyString="";
        int idx=request.indexOf(" ");
        String partnerId="";
        String propertyName="";
        if(idx==-1){
            replyString="Specify both the parameters";
        }

        String param1=request.substring(0,idx);
        try {
            int strToInt=Integer.parseInt(param1);
            partnerId=param1;
            propertyName=request.substring(idx+1,request.length());
        }catch (Exception ex){
            propertyName=param1;
            partnerId=request.substring(idx+1,request.length());
        }

        replyString=dpByEnv.dynamicPropertyValues(propertyName,partnerId);

        Activity reply=MessageFactory.text(replyString);
        return  reply;
    }
}
