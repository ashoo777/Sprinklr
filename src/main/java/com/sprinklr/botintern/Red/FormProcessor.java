package com.sprinklr.botintern.Red;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.schema.Activity;
import com.sprinklr.botintern.Red.Cards.OtpForm;
import com.sprinklr.botintern.Red.Requests.welcomePath.EnterLoginDetails;
import com.sprinklr.botintern.Red.Requests.welcomePath.ReachWelcome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Component
public class FormProcessor {

    @Autowired
    ReachWelcome reachWelcome;

    @Autowired
    GlobalUserId globalUserId;

    @Autowired
    EnterLoginDetails enterLoginDetails;

    @Autowired
    OtpForm otpForm;

    public Activity absorbForm(TurnContext turnContext){

        Object value=turnContext.getActivity().getValue();
        Map<?,?> formData=(Map<?,?>) value;

        System.out.println("Form"+formData);

        if ((formData.size())==1){
            Set keys = formData.keySet();
            Iterator i = keys.iterator();
            String otpPageLink="";
            while (i.hasNext()) {
                otpPageLink=i.next().toString();
            }

            String otp=formData.get(otpPageLink).toString();
            reachWelcome.enterOtp(globalUserId.getUserId(),otp,otpPageLink);

            // handle results from welcome
//            if(){
//
//
//            }
            Activity reply=MessageFactory.text("Login success");
            return reply;

        }else {
            Set keys = formData.keySet();
            Iterator i = keys.iterator();
            String authPageLink="";
            String password="";
            String username="";
            while (i.hasNext()) {
                String val=i.next().toString();
                if(val.equals("password")){
                    password=formData.get(val).toString();
                }else {
                    authPageLink=val;
                    username=formData.get(val).toString();
                }
            }

            String otpPageLink=enterLoginDetails.postLogin(globalUserId.getUserId(),username,password,authPageLink);

            if (otpPageLink.equals("Wrong")){
                return MessageFactory.text("Credentials are wrong"+"<br>"+"Please Relogin");
            }
            return otpForm.getForm(turnContext,otpPageLink);
        }

    }
}
