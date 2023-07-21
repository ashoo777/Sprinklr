package com.sprinklr.botintern.Red.Forms;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.schema.Activity;
import com.sprinklr.botintern.Red.Requests.ApiController.ClientDetailsController;
import com.sprinklr.botintern.Red.Requests.ApiController.PartnerDetailsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.sprinklr.botintern.BotStringConstants.*;

@Component
public class SingleParameterForm {

    @Autowired
    ClientDetailsController clientDetailsController;

    @Autowired
    PartnerDetailsController partnerDetailsController;

    public Activity getRedResponse(Map<?, ?> formData) {

        Set keys = formData.keySet();
        Iterator i = keys.iterator();
        String requestApi = EMPTY_STRING;
        while (i.hasNext()) {
            requestApi = i.next().toString();
        }
        String queryParameter = formData.get(requestApi).toString();

        switch (requestApi) {

            case PARTNER_NAME:
                return partnerDetailsController.getResultByName(queryParameter);

            case PARTNER_ID:
                return partnerDetailsController.getResultById(queryParameter);

            case CLIENT_NAME:
                return clientDetailsController.getResultByName(queryParameter);

            case CLIENT_ID:
                return clientDetailsController.getResultById(queryParameter);

            default:
                return MessageFactory.text(INVALID_REQUEST);
        }
    }
}
