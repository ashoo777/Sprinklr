package com.sprinklr.botintern.Database.Repository;

import com.sprinklr.botintern.Database.models.EnvironmentModel;
import com.sprinklr.botintern.Database.models.UserDataModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


//extends MongoRepository<UserDataModel,String>
@Repository
public interface UserDataModelRepo extends MongoRepository<UserDataModel,String>{

}
