package com.sprinklr.botintern.Database.Repository;

import com.sprinklr.botintern.Database.models.EnvironmentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentModelRepo extends MongoRepository<EnvironmentModel,String> {
    EnvironmentModel findByEnv(String env);
}
