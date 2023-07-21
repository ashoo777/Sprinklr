package com.sprinklr.botintern.Red.Forms;

import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.schema.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FormProcessor {

    @Autowired
    SingleParameterForm singleParameterForm;

    @Autowired
    TwoParameterForms twoParameterForms;

    public Activity absorbForm(TurnContext turnContext) {

        Object value = turnContext.getActivity().getValue();
        Map<?, ?> formData = (Map<?, ?>) value;

        if ((formData.size()) == 1) {
            return singleParameterForm.getRedResponse(formData);
        } else {
            return twoParameterForms.getRedResponse(formData);
        }
    }
}
