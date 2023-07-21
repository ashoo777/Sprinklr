package com.sprinklr.botintern.Database.Services;

import com.mongodb.client.MongoCollection;
import com.sprinklr.botintern.Database.Repository.EnvironmentModelRepo;
import com.sprinklr.botintern.Database.Repository.UserDataModelRepo;
import com.sprinklr.botintern.Database.models.EnvironmentModel;
import com.sprinklr.botintern.Database.models.UserDataModel;
import com.sprinklr.botintern.EchoBot;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentDataModelRepoService {

    @Autowired
    private MongoCollection<Document> envCollection;

    @Autowired
    EnvironmentModelRepo environmentModelRepo;

    public EnvironmentModel getEnvObject(String env){
        Document query=new Document("env",env);
        Document document = envCollection.find((query)).first();

        if(document!=null){
            EnvironmentModel envObject=documentToUser(document);
            return  envObject;
        }
        return null;
    }

    private EnvironmentModel documentToUser(Document document){

        EnvironmentModel envObject=new EnvironmentModel(document.getString("env"),document.getString("link"));
        return envObject;
    }


}