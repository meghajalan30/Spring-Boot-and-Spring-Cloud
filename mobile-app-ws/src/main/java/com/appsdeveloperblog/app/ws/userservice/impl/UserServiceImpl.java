package com.appsdeveloperblog.app.ws.userservice.impl;


import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;
import com.appsdeveloperblog.app.ws.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    Map<String,UserRest> users;
    Utils utils;
    public UserServiceImpl()
    {}

    @Autowired
    public UserServiceImpl(Utils utils)
    {
        this.utils=utils;
    }

    @Override
    public UserRest createUser(UserDetailsRequestModel userDetails) {

        UserRest returnVal=new UserRest();
        returnVal.setEmail(userDetails.getEmail());
        returnVal.setFirstName(userDetails.getFirstName());
        returnVal.setLastName(userDetails.getLastName());
        String userId= utils.generateUserId();
        returnVal.setUserId(userId);
        if(users==null)
            users=new HashMap<>();
        users.put(userId,returnVal);
        return returnVal;
    }
}
