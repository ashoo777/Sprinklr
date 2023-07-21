package com.sprinklr.botintern.Formats;

import com.google.common.html.HtmlEscapers;

import static com.sprinklr.botintern.BotStringConstants.*;

public class FormatResponse {

    public static String indentResponse(String reply, String format) {
        String replyString;
        switch (format) {

            case XML:
                replyString = XmlFormat.prettyFormat(reply, 3);
                replyString = HtmlEscapers.htmlEscaper().escape(replyString);
                replyString = newLine(replyString);
                replyString = PRE_START_TAG + replyString + PRE_END_TAG;
                break;
            case JSON:
                replyString = JsonFormat.prettyFormat(reply);
                replyString = PRE_START_TAG + replyString + PRE_END_TAG;
                break;
            default:
                replyString = reply;
        }
        return replyString;
    }

    private static String newLine(String input) {

        String result = EMPTY_STRING;
        for (char c : input.toCharArray()) {
            if (c == '\n') {
                result += LINE_BREAK;
            } else {
                result += c;
            }
        }
        return result;
    }


}
