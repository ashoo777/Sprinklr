package com.sprinklr.botintern.Red.Requests.ApiController;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.schema.Activity;
import com.sprinklr.botintern.Red.Requests.FinalApis.DPByEnv;
import com.sprinklr.botintern.Trimmer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.sprinklr.botintern.BotStringConstants.EMPTY_STRING;
import static com.sprinklr.botintern.BotStringConstants.INCOMPLETE_PARAMETERS;

@Component
public class DynamicProperties {

    @Autowired
    DPByEnv dpByEnv;

    public Activity getResult(String propertyName, String partnerId) {

        propertyName = Trimmer.removeWhiteSpaces(propertyName);
        partnerId = Trimmer.removeWhiteSpaces(partnerId);
        if ((StringUtils.isEmpty(propertyName)) || (StringUtils.isEmpty(partnerId))) {
            return MessageFactory.text(INCOMPLETE_PARAMETERS);
        }

        return MessageFactory.text(dpByEnv.dynamicPropertyValues(propertyName, partnerId));
    }
}
