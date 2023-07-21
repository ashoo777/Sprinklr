// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.sprinklr.botintern;

import com.microsoft.bot.builder.ActivityHandler;
import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.ActivityTypes;
import com.sprinklr.botintern.Red.Forms.FormProcessor;
import com.sprinklr.botintern.Red.RequestController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.sprinklr.botintern.BotStringConstants.*;

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
    RequestController requestController;

    @Autowired
    FormProcessor formProcessor;

    @Override
    protected CompletableFuture<Void> onMessageActivity(TurnContext turnContext) {

        Activity activity = turnContext.getActivity();

        String replyString = EMPTY_STRING;
        // check if input is in a form that is not handled

        if (activity == null || !(activity.isType(ActivityTypes.MESSAGE))) {
            Activity reply = MessageFactory.text(INVALID_ACTIVITY);
            return turnContext.sendActivity(reply).thenApply(resourceResponse -> null);
        }

        // check if input is reply of a form
        if (StringUtils.isEmpty(activity.getText())) {
            Activity update = formProcessor.absorbForm(turnContext);
            update.setId(activity.getReplyToId());
            return turnContext.updateActivity(update).thenApply(resourceResponse -> null);
        }

        // normal string input
        else {
            String message = turnContext.getActivity().getText();
            String reply = stringProcessor.process(message);

            if (reply.equals(RED)) {
                Activity redRequestResponse = requestController.processRedRequest(removeMention(message));
                return turnContext.sendActivity(redRequestResponse).thenApply(resourceResponse -> null);
            }

            if (StringUtils.isEmpty(reply) && (ApiCall.success.equals(true))) {
                replyString = SUCCESS_MESSAGE;
            } else {
                replyString = reply;
            }
        }

        return turnContext.sendActivity(
                MessageFactory.text(replyString)
        ).thenApply(sendResult -> null);
    }

    private String removeMention(String inputMessage) {

        int index = inputMessage.indexOf(SPACE);
        return Trimmer.removeWhiteSpaces(inputMessage.substring(index + 1));
    }

}
