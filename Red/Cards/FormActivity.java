package com.sprinklr.botintern.Red.Cards;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.Attachment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.sprinklr.botintern.BotStringConstants.CARD_CONTENT_TYPE;
import static com.sprinklr.botintern.BotStringConstants.IS_ADAPTIVE_CARD;

@Component
public class FormActivity {

    public Activity getForm(String cardBody) {

        //make the attachment
        Attachment cardAttachment = new Attachment();
        cardAttachment.setContentType(CARD_CONTENT_TYPE);
        try {
            JsonNode temp = new ObjectMapper().readTree(cardBody);
            cardAttachment.setContent(temp);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // send the card/form attachment in activity
        Activity reply = MessageFactory.attachment(cardAttachment);
        Map<String, Object> channelData = new HashMap<>();
        channelData.put(IS_ADAPTIVE_CARD, true);
        reply.setChannelData(channelData);

        // Send the reply
        return reply;
    }
}
