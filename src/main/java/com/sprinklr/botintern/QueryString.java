package com.sprinklr.botintern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import java.util.Iterator;
import java.util.Map;
@Component
@Scope("singleton")
public class QueryString {

    public String queryEndpoint(String query){
        if(query==""){
            return query;
        }

        String queryString="?";
        try {
            JsonNode queryNode=mapQuery(query);
            Iterator<Map.Entry<String, JsonNode>> fields = queryNode.fields();

            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();

                queryString+=field.getKey()+"=";
                String val=field.getValue().toString();
                String arrayVal="";
                if(val.charAt(0)=='['){
                    for(int k=0;k<val.length();k++){
                        char x=val.charAt(k);
                        if(x!='\"' && x!='[' && x!=']'){
                            arrayVal+=x;
                        }
                    }
                    queryString+=arrayVal;
                }else {
                    queryString+=field.getValue().asText();
                }

                if(fields.hasNext()){
                    queryString+="&";
                }
            }
        }catch (Exception ex){
            System.out.println("Query string problem");
            System.out.println(ex.getMessage());
        }
        return queryString;
    }


    private JsonNode mapQuery(String query) {
        try {
            JsonMapper jsonMapper=new JsonMapper();
            JsonNode newNode=jsonMapper.readTree(query);
            return newNode;
        }catch (Exception ex){
            return null;
        }
    }
}
