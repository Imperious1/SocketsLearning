package com.company;

/**
 * Created by blaze on 5/20/2016.
 */
public class UserModelC {
    private String message;
    private String userName;
    private String messageTo;
    private String name;
    private boolean isRegistration;

    public static UserModelC Builder() {
        return new UserModelC();
    }

    public String getMessage() {
        return message;
    }

    public UserModelC setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserModelC setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getMessageTo() {
        return messageTo;
    }

    public UserModelC setMessageTo(String messageTo) {
        this.messageTo = messageTo;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserModelC setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isRegistration() {
        return isRegistration;
    }

    public UserModelC setRegistration(boolean registration) {
        isRegistration = registration;
        return this;
    }
}
