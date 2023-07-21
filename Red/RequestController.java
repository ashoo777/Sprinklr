package com.sprinklr.botintern.Red;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.schema.Activity;
import com.sprinklr.botintern.BotStringConstants;
import com.sprinklr.botintern.Red.Cards.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.sprinklr.botintern.BotStringConstants.*;

@Component
public class RequestController {

    @Autowired
    FormActivity formActivity;

    public Activity processRedRequest(String command) {

        switch (command) {

            case HELP:
                return formActivity.getForm(HelpCard.getAdaptiveCardJson());

            case DP:
                return formActivity.getForm(DpCard.getAdaptiveCardJson());

            case PARTNER_NAME:
                return formActivity.getForm(PartnerNameCard.getAdaptiveCardJson());

            case PARTNER_ID:
                return formActivity.getForm(PartnerIdCard.getAdaptiveCardJson());

            case UM:
                return formActivity.getForm(UmCard.getAdaptiveCardJson());

            case CLIENT_NAME:
                return formActivity.getForm(ClientNameCard.getAdaptiveCardJson());

            case CLIENT_ID:
                return formActivity.getForm(ClientIdCard.getAdaptiveCardJson());

            default:
                return MessageFactory.text(BotStringConstants.INVALID_REQUEST);
        }

    }
}
