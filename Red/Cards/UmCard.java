package com.sprinklr.botintern.Red.Cards;

public class UmCard {

    public static String getAdaptiveCardJson() {
        String adaptiveCardJson = "{\n" +
                "  \"type\": \"AdaptiveCard\",\n" +
                "  \"body\": [\n" +
                "    {\n" +
                "      \"type\": \"TextBlock\",\n" +
                "      \"text\": \"Find Universal Message By UMId from Elastic Search\",\n" +
                "      \"weight\": \"Bolder\",\n" +
                "      \"size\": \"Medium\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Input.Text\",\n" +
                "      \"id\": \"um\",\n" +
                "      \"placeholder\": \"uMId*\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Input.Text\",\n" +
                "      \"id\": \"partnerId\",\n" +
                "      \"placeholder\": \"PartnerId*\"\n" +
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
}
