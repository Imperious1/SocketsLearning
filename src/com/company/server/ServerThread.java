package com.company.server;

import com.company.Singleton;
import com.company.UserModelC;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

/**
 * Created by blaze on 5/18/2016.
 */
class ServerThread extends Thread {

    private Socket clientSocket = null;
    private BufferedReader in = null;
    private int id = Singleton.getThreads().size();
    private UserModelC umc = null;

    ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            Singleton.getInstance();
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                umc = fromJson(inputLine.trim());
                if (umc.isRegistration()) {
                    tempRegister();
                } else if (umc.getMessage().equals("exit")) {
                    exit();
                    break;
                } else if (umc.getMessage().startsWith("@")) {
                    writeInNewThread().start();
                } else if (umc.getMessageTo().equals("1")) {
                    for (UserModel m : Singleton.getThreads()) {
                        if (!Objects.equals(m.getName(), umc.getName()))
                            new PrintWriter(m.getClientConnection().getOutputStream(), true).println(umc.getName() + ": " + umc.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Thread writeInNewThread() {
        return new Thread(() -> {
            for (UserModel m : Singleton.getThreads()) {
                if (umc.getMessage().contains(m.getName())) {
                    try {
                        new PrintWriter(m.getClientConnection().getOutputStream(), true).println(umc.getMessage().replace("@" + m.getName(), "").trim());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void tempRegister() {
        Singleton.addToList(new UserModel()
                .setClientConnection(clientSocket)
                .setUserName(umc.getUserName())
                .setName(umc.getName()));
        System.out.println(Singleton.getThreads().size());
        System.out.println(umc.getUserName() + " connected");
    }

    private void exit() throws IOException {
        System.out.println(Singleton.getThreads().get(id).getUserName() + " disconnected");
        Singleton.removeFromList(String.valueOf(id));
        closeConnection();
    }

    private void closeConnection() throws IOException {
        in.close();
        clientSocket.close();
    }

    private UserModelC fromJson(String json) {
        if (json.contains("}{")) {
            int index = json.indexOf("}{") + 1;
            json = json.substring(0, index);
        }
        Gson gson = new Gson();
        return gson.fromJson(json, UserModelC.class);
    }
}
