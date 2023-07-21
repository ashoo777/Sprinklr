package com.sprinklr.botintern;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.connection.SocketSettings;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;

@Configuration
public class SpringMongoConfig {
    //@Value("${spring.data.mongodb.uri}")
    private final String mongodbUri ="mongodb://localhost:27017/RedApi";

    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(mongodbUri);

//        ServerAddress serverAddress = new ServerAddress("api.qvfhe2l.mongodb.net", 27017);
//        MongoCredential mongoCredential = MongoCredential.createCredential("admin", "admin", "admin".toCharArray());
//        MongoClientSettings settings = MongoClientSettings.builder()
//                .applyToClusterSettings(builder ->
//                        builder.hosts(Arrays.asList(serverAddress)))
//                .credential(mongoCredential)
//                .build();
//        MongoClient mongoClient = MongoClients.create(settings);

        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }



    @Bean
    public MongoCollection<Document> userCollection() {
        MongoClient mongoClient = mongoClient();
        MongoDatabase database = mongoClient.getDatabase("RedApi");
        return database.getCollection("Users");
    }

    @Bean
    public MongoCollection<Document> envCollection() {
        MongoClient mongoClient = mongoClient();
        MongoDatabase database = mongoClient.getDatabase("RedApi");
        return database.getCollection("Environment");
    }


    @Bean
    public MongoTemplate mongoTemplate(){
        MongoClient mongoClient = mongoClient();
        MongoTemplate mongoTemplate=new MongoTemplate(mongoClient,"RedApi");
        return mongoTemplate;

    }
}