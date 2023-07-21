package com.sprinklr.botintern.Database.Services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.sprinklr.botintern.Database.Repository.UserDataModelRepo;
import com.sprinklr.botintern.Database.models.UserDataModel;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDataModelRepoService {

    @Autowired
    private MongoCollection<Document> userCollection;

    @Autowired
    UserDataModelRepo userDataModelRepo;



    public void createNewUser(String userId,String env){
        UserDataModel newUser=new UserDataModel(userId,env);
//        mongoTemplate.insert(newUser,"Users");
        Document document=userToDocument(newUser);

        System.out.println("nya user");
        System.out.println(env);
        System.out.println(document);
        userCollection.insertOne(document);
    }


    public void updateSessionCookie(String userId,String sessionCookie){

//        Query query = new Query(Criteria.where("userId").is(userId));
//        Update update = new Update();
//        update.set("sessionCookie", sessionCookie);
//
//        UpdateResult result = mongoTemplate.updateFirst(query, update, UserDataModel.class);

//        if (result != null)
//            return result.getModifiedCount();
//        else
//            return 0;
        System.out.println("Update session cookie");
        Document document = userCollection.find(Filters.eq("userId", userId)).first();
        System.out.println("This is document " + document.toJson() );
        UserDataModel user=documentToUser(document);
        user.setSessionCookie(sessionCookie);
        document=userToDocument(user);
        System.out.println(document.toJson());
        System.out.println(userId);
        Bson temp = Filters.eq("userId", userId);
        userCollection.findOneAndReplace(temp, document);
    }


    public void updateSprRedCookie(String userId,String sprRedCookie){

//        Query query = new Query(Criteria.where("userId").is(userId));
//        Update update = new Update();
//        update.set("sprRedCookie", sprRedCookie);
//        UpdateResult result = mongoTemplate.updateFirst(query, update, UserDataModel.class);
        Document document = userCollection.find(Filters.eq("userId", userId)).first();
        System.out.println("This is document " + document.toJson() );
        UserDataModel user=documentToUser(document);
        user.setSprRedCookie(sprRedCookie);
        document=userToDocument(user);
        System.out.println(document.toJson());
        System.out.println(userId);
        Bson temp = Filters.eq("userId", userId);
        userCollection.findOneAndReplace(temp, document);
    }


    public void updateAuthAndKc(String userId,String authSessionIdCookie,String kcRestartCookie){
//        Query query = new Query(Criteria.where("userId").is(userId));
//        Update update = new Update();
//        update.set("authSessionCookie", authSessionIdCookie);
//        update.set("kcRestartCookie",kcRestartCookie);
//
//        UpdateResult result = mongoTemplate.updateFirst(query, update, UserDataModel.class);

        Document document = userCollection.find(Filters.eq("userId", userId)).first();
        System.out.println("This is document " + document.toJson() );
        UserDataModel user=documentToUser(document);
        user.setAuthSessionIdCookie(authSessionIdCookie);
        user.setKcRestartCookie(kcRestartCookie);
        document=userToDocument(user);
        System.out.println(document.toJson());
        System.out.println(userId);
        Bson temp = Filters.eq("userId", userId);
        userCollection.findOneAndReplace(temp, document);
    }


    public String getEnv(String userId){
//        Query query = new Query(Criteria.where("userId").is(userId));
//        UserDataModel result=(UserDataModel) mongoTemplate.find(query,UserDataModel.class);
//
//        return result.getEnvironment();
        Document query=new Document("userId",userId);
        Document document=userCollection.find((query)).first();
        String env=document.getString("Environment");
        return env;
    }



    public String getSessionCookie(String userId){

//        Query query = new Query(Criteria.where("userId").is(userId));
//        UserDataModel result=(UserDataModel) mongoTemplate.find(query,UserDataModel.class);
//
//        return result.getSessionCookie();
        Document query=new Document("userId",userId);
        Document document=userCollection.find((query)).first();

        if (document!=null){
            String sessionCookie=document.getString("sessionCookie");
            if(sessionCookie!=null){
                return sessionCookie;
            }
        }
        return "Session cookie not established yet!!";
    }


    public String getSprRedCookie(String userId){
//        Query query = new Query(Criteria.where("userId").is(userId));
//        UserDataModel result=(UserDataModel) mongoTemplate.find(query,UserDataModel.class);
//
//        return result.getSprRedCookie();
        Document query=new Document("userId",userId);
        Document document = userCollection.find((query)).first();

        if (document!=null){
            String sprRedCookie=document.getString("sprRedCookie");
            if(sprRedCookie!=null){
                return sprRedCookie;
            }
        }
        return "SprRedCookie cookie not established yet!!";
    }


    public UserDataModel getAllCookies(String userId){
//        Query query = new Query(Criteria.where("userId").is(userId));
//        UserDataModel result=(UserDataModel) mongoTemplate.find(query,UserDataModel.class);
//
//        return result;
        Document query=new Document("userId",userId);
        Document document = userCollection.find((query)).first();

        if(document!=null){
            UserDataModel user=documentToUser(document);
            return  user;
        }
        return null;
    }


    public void deleteCookies(String userId){
//        Query query = new Query(Criteria.where("userId").is(userId));
//        Update update = new Update();
//        update.set("authSessionCookie", null);
//        update.set("kcRestartCookie",null);
//        update.set("sprRedCookie",null);
//        update.set("sessionCookie",null);
//
//        UpdateResult result = mongoTemplate.updateFirst(query, update, UserDataModel.class);

        Document query=new Document("userId",userId);
        String env=query.getString("env");
        userCollection.deleteOne(query);
        UserDataModel newUser=new UserDataModel(userId,env);
        Document document=userToDocument(newUser);
        userCollection.insertOne(document);
    }


    public void changeEnvironment(String userId,String env){
//
//        Query query = new Query(Criteria.where("userId").is(userId));
//        Update update = new Update();
//        update.set("authSessionCookie", null);
//        update.set("kcRestartCookie",null);
//        update.set("sprRedCookie",null);
//        update.set("sessionCookie",null);
//        update.set("environment",env);
//
//        UpdateResult result = mongoTemplate.updateFirst(query, update, UserDataModel.class);
        Document query=new Document("userId",userId);
        userCollection.deleteOne(query);
        UserDataModel newUser=new UserDataModel(userId,env);
        newUser.setEnvironment(env);
        Document document=userToDocument(newUser);
        System.out.println("new start"+document);
        userCollection.insertOne(document);
    }



    private UserDataModel documentToUser(Document document){
        String userId=document.getString("userId");
        String env=document.getString("env");
        UserDataModel user=new UserDataModel(userId,env);

        user.setSessionCookie(document.getString("sessionCookie"));
        user.setAuthSessionIdCookie(document.getString("authSessionIdCookie"));
        user.setKcRestartCookie(document.getString("kcRestartCookie"));
        user.setSprRedCookie(document.getString("sprRedCookie"));
        user.setEnvironment(document.getString("Environment"));

        return user;
    }


    private Document userToDocument(UserDataModel user){
        Document document=new Document();
        if(user.userId!=null){
            document.append("userId",user.userId);
        }
        document.append("Environment",user.getEnvironment());
        document.append("sprRedCookie",user.getSprRedCookie());
        document.append("kcRestartCookie",user.getKcRestartCookie());
        document.append("authSessionIdCookie",user.getAuthSessionIdCookie());
        document.append("sessionCookie",user.getSessionCookie());

        return  document;
    }

}
