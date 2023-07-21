package com.sprinklr.botintern.Formats;

import com.google.common.html.HtmlEscapers;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

public class FormatResponse {

    public static String indentResponse(String reply,String format){
        String replyString="";
        switch (format){

            case "XML" :{
                replyString=XmlFormat.prettyFormat(reply,3);
                replyString= HtmlEscapers.htmlEscaper().escape(replyString);
                replyString= newLine(replyString);
                replyString="<pre>"+replyString+"</pre>";
                break;
            }
            case "HTML" :{
                Document doc = (Document) Jsoup.parseBodyFragment(reply);
                System.out.println(doc);
                replyString=doc.toString();
                replyString= newLine(replyString);
                replyString="<pre>"+replyString+"</pre>";
                break;
            }
            case "JSON" :{
                replyString=newLineJson(reply);
                replyString="<pre>"+replyString+"</pre>";
                break;
            }
            default:{
                replyString=reply;
            }
        }
        return replyString;
    }

    private  static String newLine(String input){

        String result="";
        for(char c:input.toCharArray()){
            if (c=='\n'){
                result+="<br>";
            }else {
                result+=c;
            }
        }
        return result;
    }

    private  static String newLineJson(String input){
        int count=0;
        String result="";
        for(char c:input.toCharArray()){
            boolean brAdded=false;
            if (c=='}' || c==']'){
                count--;
                result+="<br>";

                int tabs=count;
                while (tabs>0){
                    tabs--;
                    result+="&nbsp;&nbsp;&nbsp;";
                }
            }

            result+=c;
            if (c=='{' || c=='[' ){
                count++;
                result+="<br>";
                int tabs=count;
                while (tabs>0){
                    tabs--;
                    result+="&nbsp;&nbsp;&nbsp;";
                }
            }
            if(c==','){
                result+="<br>";
                int tabs=count;
                while (tabs>0){
                    tabs--;
                    result+="&nbsp;&nbsp;&nbsp;";
                }
            }

        }
        return result;
    }
}
