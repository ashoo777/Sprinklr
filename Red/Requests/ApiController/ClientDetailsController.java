package com.sprinklr.botintern.Red.Requests.ApiController;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.schema.Activity;
import com.sprinklr.botintern.Red.Requests.FinalApis.ClientDetailsById;
import com.sprinklr.botintern.Red.Requests.FinalApis.ClientDetailsByName;
import com.sprinklr.botintern.Trimmer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.sprinklr.botintern.BotStringConstants.INCOMPLETE_PARAMETERS;

@Component
public class ClientDetailsController {

    @Autowired
    ClientDetailsByName clientDetailsByName;

    @Autowired
    ClientDetailsById clientDetailsById;

    public Activity getResultById(String request) {
        request = Trimmer.removeWhiteSpaces(request);
        return StringUtils.isEmpty(request) ? MessageFactory.text(INCOMPLETE_PARAMETERS) : MessageFactory.text(clientDetailsById.clientDetails(request));
    }

    public Activity getResultByName(String request) {
        request = Trimmer.removeWhiteSpaces(request);
        return StringUtils.isEmpty(request) ? MessageFactory.text(INCOMPLETE_PARAMETERS) : MessageFactory.text(clientDetailsByName.clientDetails(request));
    }
}
