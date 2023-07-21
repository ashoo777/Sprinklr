package com.sprinklr.botintern.Red.Requests;


import com.sprinklr.botintern.Database.Services.EnvironmentDataModelRepoService;
import com.sprinklr.botintern.Database.Services.UserDataModelRepoService;
import com.sprinklr.botintern.Database.models.EnvironmentModel;
import com.sprinklr.botintern.Database.Repository.EnvironmentModelRepo;
import com.sprinklr.botintern.Database.Repository.UserDataModelRepo;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GlobalBaseUri {
    public String baseUriOfEnv;
    @Autowired
    UserDataModelRepoService userDataModelRepoService;

    @Autowired
    EnvironmentDataModelRepoService environmentDataModelRepoService;
    @Autowired
    EnvironmentModelRepo environmentModelRepo;

    public void setBaseUri(String userID) {
        String env=userDataModelRepoService.getEnv(userID);

        EnvironmentModel envObject=environmentDataModelRepoService.getEnvObject(env);
        baseUriOfEnv= envObject.getLink();
    }

}
