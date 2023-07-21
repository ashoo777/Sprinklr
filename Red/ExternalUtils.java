package com.sprinklr.botintern.Red;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("file:/Users/akshit.rewliya/Downloads/BotProjectExternalData/fixed.properties")
public class ExternalUtils {

    @Value("${username}")
    public String username;

    @Value("${baseLink}")
    public String baseLink;

    @Value("${authToken}")
    public String authToken;

    @Value("${clientDetails}")
    public String clientDetails;

    @Value("${partnerDetails}")
    public String partnerDetails;

    @Value("${umDetails}")
    public String umDetails;

}
