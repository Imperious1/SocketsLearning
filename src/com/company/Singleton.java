package com.company;

import com.company.server.UserModel;

import java.util.ArrayList;

/**
 * Created by blaze on 5/20/2016.
 */
public class Singleton {

    private static ArrayList<UserModel> threads = new ArrayList<>();

    public static void removeAll() {
        threads.clear();
    }

    public static ArrayList<UserModel> getThreads() {
        return threads;
    }

    public static void addToList(UserModel userModel) {
        threads.add(userModel);
    }

    public static void removeFromList(String index) {
        threads.remove(Integer.valueOf(index).intValue());
    }

    private static Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }
}
