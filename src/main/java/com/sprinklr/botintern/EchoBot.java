// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.sprinklr.botintern;

import com.codepoetics.protonpack.collectors.CompletableFutures;
import com.microsoft.bot.builder.ActivityHandler;
import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.ActivityTypes;
import com.microsoft.bot.schema.ChannelAccount;

import com.sprinklr.botintern.Database.Services.UserDataModelRepoService;
import com.sprinklr.botintern.Database.models.UserDataModel;
import com.sprinklr.botintern.Formats.FormatResponse;
import com.sprinklr.botintern.Formats.KnowFormat;
import com.sprinklr.botintern.Red.FormProcessor;
import com.sprinklr.botintern.Red.GlobalUserId;
import com.sprinklr.botintern.Red.RequestController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This class implements the functionality of the Bot.
 *
 * <p>
 * This is where application specific logic for interacting with the users would be added. For this
 * sample, the {@link #onMessageActivity(TurnContext)} echos the text back to the user. The {@link
 * #onMembersAdded(List, TurnContext)} will send a greeting to new conversation participants.
 * </p>
 */

public class EchoBot extends ActivityHandler {

    @Autowired
    StringProcessor stringProcessor;

    @Autowired
    UserDataModelRepoService userDataModelRepoService;


    @Autowired
    GlobalUserId globalUserId;

    @Autowired
    RequestController requestController;

    @Autowired
    FormProcessor formProcessor;

    @Override
    protected CompletableFuture<Void> onMessageActivity(TurnContext turnContext) {

        Activity activity=turnContext.getActivity();
        ChannelAccount user=activity.getFrom();
        globalUserId.setUserId(user.getId());
        String replyString="";
        String userId= globalUserId.getUserId();
        UserDataModel userYesOrNo= userDataModelRepoService.getAllCookies(userId);
        if (userYesOrNo==null){
            System.out.println("UserYesOrNO : " +userYesOrNo.toString());
            userDataModelRepoService.createNewUser(userId,"qa4");
        }

        // check if input is form input
        if (turnContext.getActivity().isType(ActivityTypes.MESSAGE)&&turnContext.getActivity().getText() == null) {
            System.out.println("Form h");
            Activity update = formProcessor.absorbForm(turnContext);;
            update.setId(turnContext.getActivity().getReplyToId());
            return turnContext.updateActivity(update).thenApply(resourceResponse -> null);
        }

        //nhi h toh bhej do process karne ke liye
        else {
            String message=turnContext.getActivity().getText();
            String reply=stringProcessor.process(message);

            if (reply.equals("Red")) {
                Activity redRequestResponse=requestController.processRedRequest(turnContext, removeMention(message));
                return turnContext.sendActivity(redRequestResponse).thenApply(resourceResponse -> null);
            }

            if ((reply==null) && (ApiCall.success.equals(true))){
                replyString="Execution success";
            } else if (reply==null) {
                replyString="Failure";
            } else{
                System.out.println(reply);
                replyString=reply;
            }

        }


        System.out.println(replyString);

        // reply string form m hi jaana h
        return turnContext.sendActivity(
            MessageFactory.text(replyString)
        ).thenApply(sendResult -> null);
    }

    @Override
    protected CompletableFuture<Void> onMembersAdded(
        List<ChannelAccount> membersAdded,
        TurnContext turnContext
    ) {
        return membersAdded.stream()
            .filter(
                member -> !StringUtils
                    .equals(member.getId(), turnContext.getActivity().getRecipient().getId())
            ).map(channel -> turnContext.sendActivity(MessageFactory.text("Hello and welcome!")))
            .collect(CompletableFutures.toFutureList()).thenApply(resourceResponses -> null);
    }

    private String removeMention(String inputMessage){

        int index=inputMessage.indexOf(' ');
        String message=inputMessage.substring(index+1).trim();
        return message;
    }

}
