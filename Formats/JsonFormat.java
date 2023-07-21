package com.sprinklr.botintern.Formats;

import static com.sprinklr.botintern.BotStringConstants.*;

public class JsonFormat {

    public static String prettyFormat(String input) {
        int count = 0;
        String result = EMPTY_STRING;
        for (char c : input.toCharArray()) {

            if (c == CLOSE_CURLY || c == CLOSE_BLOCK_BRACKET) {
                count--;
                result += LINE_BREAK;

                int tabs = count;
                while (tabs > 0) {
                    tabs--;
                    result += THREE_SPACES;
                }
            }

            result += c;

            if (c == OPEN_BLOCK_BRACKET || c == OPEN_CURLY) {
                count++;
                result += LINE_BREAK;
                int tabs = count;
                while (tabs > 0) {
                    tabs--;
                    result += THREE_SPACES;
                }
            }
            if (c == COMMA_CHAR) {
                result += LINE_BREAK;
                int tabs = count;
                while (tabs > 0) {
                    tabs--;
                    result += THREE_SPACES;
                }
            }
        }
        return result;
    }
}
