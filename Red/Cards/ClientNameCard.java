package com.sprinklr.botintern.Red.Cards;

public class ClientNameCard {

    public static String getAdaptiveCardJson() {
        String adaptiveCardJson = "{\n" +
                "  \"type\": \"AdaptiveCard\",\n" +
                "  \"body\": [\n" +
                "    {\n" +
                "      \"type\": \"TextBlock\",\n" +
                "      \"text\": \"Search a client by name\",\n" +
                "      \"weight\": \"Bolder\",\n" +
                "      \"size\": \"Medium\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Input.Text\",\n" +
                "      \"id\": \"clientName\",\n" +
                "      \"placeholder\": \"ClientName*\"\n" +
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
