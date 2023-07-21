package com.sprinklr.botintern.Red;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.TurnContext;
//import com.sprinklr.botintern.Database.DatabaseServiceLayer;
import com.microsoft.bot.schema.Activity;
import com.sprinklr.botintern.Database.Repository.UserDataModelRepo;
import com.sprinklr.botintern.Database.Services.UserDataModelRepoService;
import com.sprinklr.botintern.Red.Cards.AuthForm;
import com.sprinklr.botintern.Red.Requests.ApiController.DynamicProperties;
import com.sprinklr.botintern.Red.Requests.ApiController.PartnerDetails;
import com.sprinklr.botintern.Red.Requests.ApiController.UniversalMessage;
import com.sprinklr.botintern.Red.Requests.FinalApis.DPByEnv;
import com.sprinklr.botintern.Red.Requests.FinalApis.PartnerNameById;
import com.sprinklr.botintern.Red.Requests.GlobalBaseUri;
import com.sprinklr.botintern.Red.Requests.welcomePath.HomePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class RequestController {

    @Autowired
    GlobalBaseUri globalBaseUri;


    @Autowired
    GlobalUserId globalUserId;
    @Autowired
    UserDataModelRepoService userDataModelRepoService;
    @Autowired
    HomePage homePage;
    @Autowired
    AuthForm authForm;
    @Autowired
    DynamicProperties dynamicProperties;
    @Autowired
    PartnerDetails partnerDetails;

    @Autowired
    UniversalMessage universalMessage;

    public Activity processRedRequest(TurnContext turnContext, String message){

        String userId=globalUserId.getUserId();
        globalBaseUri.setBaseUri(userId);

        int index = message.indexOf(" ");
        String command="";
        String request="";
        if(index==-1){
            command=message;
        }else {
            command = message.substring(0, index);
            request = message.substring(index + 1, message.length());
        }

        String replyString="";

        switch (command){

            case "help" : {
//                return turnContext.sendActivity(
//                        MessageFactory.text("Help")
//                ).thenApply(sendResult -> null);
                return MessageFactory.text("Help");
            }

            case "logout" : {
                userDataModelRepoService.deleteCookies(userId);
                replyString= "Successfully logged out!!<br>";
                return MessageFactory.text(replyString);

            }

            case "login" : {

                System.out.println("login");
                if(request.equals("")){
                    //default env
                    request="qa4";
                }

                userDataModelRepoService.changeEnvironment(userId,request);
                globalBaseUri.setBaseUri(userId);
                String authPageLink=homePage.homePageLoginButton();
                return authForm.getForm(turnContext,authPageLink);
            }

            case "dp" : {
                return dynamicProperties.getResult(turnContext,request);
            }

            case "partner" : {
                return partnerDetails.getResult(turnContext,request);
            }

            case "um" : {
                return universalMessage.getResult(turnContext,request);
            }

            default: {
                return MessageFactory.text("No such Request");
            }

        }

    }
}
