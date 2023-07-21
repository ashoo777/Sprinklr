package com.sprinklr.botintern.Red.Cards;

public class HelpCard {

    public static String getAdaptiveCardJson() {
        String adaptiveCardJson = "{\n" +
                "  \"type\": \"AdaptiveCard\",\n" +
                "  \"version\": \"1.0\",\n" +
                "  \"body\": [\n" +
                "    {\n" +
                "      \"type\": \"ColumnSet\",\n" +
                "      \"columns\": [\n" +
                "        {\n" +
                "          \"type\": \"Column\",\n" +
                "          \"width\": \"auto\",\n" +
                "          \"items\": [\n" +
                "            {\n" +
                "              \"type\": \"TextBlock\",\n" +
                "              \"text\": \"help\",\n" +
                "              \"weight\": \"Bolder\",\n" +
                "              \"wrap\": true\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"TextBlock\",\n" +
                "              \"text\": \"clientName\",\n" +
//                "              \"weight\": \"Bolder\",\n" +
                "              \"wrap\": true\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"TextBlock\",\n" +
                "              \"text\": \"clientId\",\n" +
                "              \"weight\": \"Bolder\",\n" +
                "              \"wrap\": true\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"TextBlock\",\n" +
                "              \"text\": \"partnerName\",\n" +
//                "              \"weight\": \"Bolder\",\n" +
                "              \"wrap\": true\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"TextBlock\",\n" +
                "              \"text\": \"partnerId\",\n" +
                "              \"weight\": \"Bolder\",\n" +
                "              \"wrap\": true\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"TextBlock\",\n" +
                "              \"text\": \"dp\",\n" +
                "              \"weight\": \"Bolder\",\n" +
                "              \"wrap\": true\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"TextBlock\",\n" +
                "              \"text\": \"um\",\n" +
                "              \"weight\": \"Bolder\",\n" +
                "              \"wrap\": true\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"Column\",\n" +
                "          \"width\": \"auto\",\n" +
                "          \"items\": [\n" +
                "            {\n" +
                "              \"type\": \"TextBlock\",\n" +
                "              \"text\": \"Get the list of Commands\",\n" +
                "              \"wrap\": true\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"TextBlock\",\n" +
                "              \"text\": \"Search a client by name\",\n" +
                "              \"wrap\": true\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"TextBlock\",\n" +
                "              \"text\": \"Fetch client by id\",\n" +
                "              \"wrap\": true\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"TextBlock\",\n" +
                "              \"text\": \"Search a partner by name\",\n" +
                "              \"wrap\": true\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"TextBlock\",\n" +
                "              \"text\": \"Fetch partner by id\",\n" +
                "              \"wrap\": true\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"TextBlock\",\n" +
                "              \"text\": \"dynamic properties by partnerId/propertyName\",\n" +
                "              \"wrap\": true\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"TextBlock\",\n" +
                "              \"text\": \"Find Universal Message By UMId\",\n" +
                "              \"wrap\": true\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        return adaptiveCardJson;
    }
}
