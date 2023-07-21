package com.sprinklr.botintern.Red.Requests.ApiController;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.schema.Activity;
import com.sprinklr.botintern.Red.Requests.FinalApis.PartnerNameById;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class PartnerDetails {

    @Autowired
    PartnerNameById partnerNameById;

    public Activity getResult(TurnContext turnContext, String request){

        String replyString="";

        if(request==null){
            replyString="Specify partner name to search by";
        }

        replyString= partnerNameById.partnerDetailsByName(request);

        Activity reply=MessageFactory.text(replyString);
        return  reply;
    }}
