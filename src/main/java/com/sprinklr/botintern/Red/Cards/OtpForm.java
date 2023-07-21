package com.sprinklr.botintern.Red.Cards;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.Attachment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@Component
public class OtpForm  {
    private String getAdaptiveCardJson(String otpPageLink) {

        String adaptiveCardJson = "{\n" +
                "  \"type\": \"AdaptiveCard\",\n" +
                "  \"body\": [\n" +
                "    {\n" +
                "      \"type\": \"TextBlock\",\n" +
                "      \"text\": \"Enter Login Credentials\",\n" +
                "      \"weight\": \"Bolder\",\n" +
                "      \"size\": \"Medium\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Input.Text\",\n" +
                "      \"id\": \""+otpPageLink+"\",\n" +
                "      \"placeholder\": \"OTP\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"actions\": [\n" +
                "    {\n" +
                "      \"type\": \"Action.Submit\",\n" +
                "      \"title\": \"Submit\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"$schema\": \"http://adaptivecards.io/schemas/adaptive-card.json\",\n" +
                "  \"version\": \"1.5\"\n" +
                "}";


        return adaptiveCardJson;

    }
    public Activity getForm(TurnContext turnContext,String otpPageLink){
        Attachment cardAttachment = new Attachment();
        cardAttachment.setContentType("application/vnd.microsoft.card.adaptive");
        try {
            JsonNode temp = new ObjectMapper().readTree(getAdaptiveCardJson(otpPageLink));
            cardAttachment.setContent(temp);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

// cardAttachment.setContent(JsonNodeFactory.instance.textNode(getAdaptiveCardJson()));

        Activity reply = MessageFactory.attachment(cardAttachment);
        Map<String, Object> channelData = new HashMap<>();
        channelData.put("isAdaptiveCard", true);
        reply.setChannelData(channelData);

// Send the reply
        return reply;
    }

}

