package com.sprinklr.botintern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

import static com.sprinklr.botintern.BotStringConstants.*;

@Component
public class QueryString {

    public String queryEndpoint(String query) {
        if (StringUtils.isEmpty(query)) {
            return query;
        }

        String queryString = "?";
        try {
            JsonNode queryNode = mapQuery(query);
            Iterator<Map.Entry<String, JsonNode>> fields = queryNode.fields();

            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();

                queryString += field.getKey() + "=";
                String val = field.getValue().toString();
                String arrayVal = EMPTY_STRING;
                if (val.charAt(0) == OPEN_BLOCK_BRACKET) {
                    for (int k = 0; k < val.length(); k++) {
                        char x = val.charAt(k);
                        if ((x != '\"') && (x != OPEN_BLOCK_BRACKET) && (x != CLOSE_BLOCK_BRACKET)) {
                            arrayVal += x;
                        }
                    }
                    queryString += arrayVal;
                } else {
                    queryString += field.getValue().asText();
                }

                if (fields.hasNext()) {
                    queryString += "&";
                }
            }
        } catch (Exception ex) {
            queryString = WRONG_QUERY_FORMAT;
            System.out.println(ex.getMessage());
        } finally {
            return queryString;
        }
    }


    private JsonNode mapQuery(String query) {
        try {
            JsonMapper jsonMapper = new JsonMapper();
            JsonNode newNode = jsonMapper.readTree(query);
            return newNode;
        } catch (Exception ex) {
            return null;
        }
    }
}
