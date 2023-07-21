package com.sprinklr.botintern.Formats;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;

public class KnowFormat {

    public static String typeOfFormat(String input){
        String format="";
        if(isJSONValid(input)){
            format="JSON";
        } else if (convertStringToXMLDocument(input)!=null) {
            format="XML";
        }else if (isHTML(input)){
            format="HTML";
        }else {
            format="OTHER";
        }
        return format;
    }


    public static boolean isJSONValid(String jsonInString ) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonInString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static Document convertStringToXMLDocument(String xmlString) {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean isHTML(String htmlString){
        try {
            Document document= (Document) Jsoup.parse(htmlString,"", Parser.htmlParser());
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
