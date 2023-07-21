package com.sprinklr.botintern.Formats;

public class HtmlFormat {

    public static String prettyFormat(String input){
        StringBuilder stringBuilder=new StringBuilder();
        for (char c:input.toCharArray()){
            if (c=='<'){
                stringBuilder.append("&lt");
            } else if (c=='>') {
                stringBuilder.append("&gt");
            } else if (c=='&') {
                stringBuilder.append("&");
            } else if (c=='"') {
                stringBuilder.append("&quot");
            } else if (c=='\'') {
                stringBuilder.append("&#39;");
            }else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }
}
