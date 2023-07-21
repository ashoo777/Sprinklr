package com.sprinklr.botintern.Red.Forms;

import com.microsoft.bot.schema.Activity;
import com.sprinklr.botintern.Red.Requests.ApiController.DynamicProperties;
import com.sprinklr.botintern.Red.Requests.ApiController.UniversalMessage;
import com.sprinklr.botintern.Trimmer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.sprinklr.botintern.BotStringConstants.*;

@Component
public class TwoParameterForms {

    @Autowired
    DynamicProperties dynamicProperties;

    @Autowired
    UniversalMessage universalMessage;

    public Activity getRedResponse(Map<?, ?> formData) {

        Set keys = formData.keySet();
        Iterator i = keys.iterator();
        String partnerId = EMPTY_STRING;
        String propertyName = EMPTY_STRING;
        String uMId = EMPTY_STRING;
        while (i.hasNext()) {
            String val = i.next().toString();
            if (val.equals(PARTNER_ID)) {
                partnerId = formData.get(val).toString();
            } else if (val.equals(UM)) {
                uMId = formData.get(val).toString();
            } else {
                propertyName = formData.get(val).toString();
            }
        }

        if (StringUtils.isNotEmpty(propertyName)) {
            return dynamicProperties.getResult(propertyName, partnerId);
        } else {
            return universalMessage.getResult(uMId, partnerId);
        }
    }
}
