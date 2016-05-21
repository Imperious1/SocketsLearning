package com.company.server;

import com.company.Singleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by blaze on 5/21/2016.
 */
class ServerControl extends Thread {
    @Override
    public void run() {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String input;
        try {
            while((input = stdIn.readLine()) != null) {
                if(input.equals("killall")) {
                    for(UserModel m : Singleton.getThreads()) {
                        synchronized (this) {
                            new PrintWriter(m.getClientConnection().getOutputStream(), true).println("Overlord: Going down for system halt NOW!");
                        } synchronized (this) {
                            new PrintWriter(m.getClientConnection().getOutputStream(), true).println("-1028310479");
                        }
                    }
                    Singleton.removeAll();
                    System.out.println("All clients terminated.");
                }
                else if(input.startsWith("!")) {
                    for(UserModel m : Singleton.getThreads()) {
                        new PrintWriter(m.getClientConnection().getOutputStream(), true).println("Overlord: " + input.replaceFirst("!", ""));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
