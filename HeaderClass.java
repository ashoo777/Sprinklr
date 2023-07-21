package com.sprinklr.botintern;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import static com.sprinklr.botintern.BotStringConstants.COLON;


@Component
public class HeaderClass {

    public JSONObject headerMaker(String header) {
        if (StringUtils.isEmpty(header)) {
            return null;
        }
        try {
            int index = header.indexOf(COLON);
            header = header.substring(index + 1, header.length() - 1);
            return mapHeader(header);

        } catch (Exception ex) {
            return null;
        }
    }

    private JSONObject mapHeader(String body) {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject headerObject = (JSONObject) jsonParser.parse(body);
            return headerObject;
        } catch (Exception ex) {
            return null;
        }
    }

}
