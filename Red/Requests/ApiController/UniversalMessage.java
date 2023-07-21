package com.sprinklr.botintern.Red.Requests.ApiController;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.schema.Activity;
import com.sprinklr.botintern.Red.Requests.FinalApis.UMByUMId;
import com.sprinklr.botintern.Trimmer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.sprinklr.botintern.BotStringConstants.INCOMPLETE_PARAMETERS;

@Component
public class UniversalMessage {

    @Autowired
    UMByUMId umByUMId;

    public Activity getResult(String messageId, String partnerId) {

        messageId = Trimmer.removeWhiteSpaces(messageId);
        partnerId = Trimmer.removeWhiteSpaces(partnerId);
        if ((StringUtils.isEmpty(messageId)) || (StringUtils.isEmpty(partnerId))) {
            return MessageFactory.text(INCOMPLETE_PARAMETERS);
        }

        return MessageFactory.text(umByUMId.universalMessageById(messageId, partnerId));
    }

}
