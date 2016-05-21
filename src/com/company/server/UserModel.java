package com.company.server;

import java.net.Socket;

/**
 * Created by blaze on 5/20/2016.
 */
public class UserModel {
    private Socket clientConnection;
    private String userName;
    private String name;

    public static UserModel Builder() {
        return new UserModel();
    }

    Socket getClientConnection() {
        return clientConnection;
    }

    UserModel setClientConnection(Socket clientConnection) {
        this.clientConnection = clientConnection;
        return this;
    }

    String getUserName() {
        return userName;
    }

    UserModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    String getName() {
        return name;
    }

    UserModel setName(String name) {
        this.name = name;
        return this;
    }
}
