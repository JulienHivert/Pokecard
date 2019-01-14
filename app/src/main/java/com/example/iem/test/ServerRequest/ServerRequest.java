package com.example.iem.test.ServerRequest;

import com.example.iem.test.Model.User;

/**
 * Created by iem on 26/01/2018.
 */

public class ServerRequest {

    private String operation;
    private User user ;

    public void setOperation(String operation){
        this.operation = operation;
    }

    public void setUser(User user){
        this.user = user;
    }
}
