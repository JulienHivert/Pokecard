package com.example.iem.test.ServerRequest;

import com.example.iem.test.Model.User;

/**
 * Created by iem on 26/01/2018.
 */

public class ServerResponse {

    private String result;
    private String message;
    private User user;

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
