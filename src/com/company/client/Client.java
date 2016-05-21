package com.company.client;

import com.company.UserModelC;
import com.google.gson.Gson;
import com.sun.istack.internal.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private static String userName, name, userInput = null;
    private static BufferedReader stdIn = null;
    private static Socket socket = null;
    private static PrintWriter out = null;
    private static boolean nameTyped = false;

    public static void main(String[] args) throws IOException {
        connect("127.0.0.1", 4044);
        System.out.println("What shall be your username?");
        new CReceiveThread(socket).start();
        while ((userInput = stdIn.readLine()) != null) {
            checkRegistration();
            if (userInput.equals("exit")) {
                out.println(toJson(getModel(null, null, userInput, "101011", false)));
                close();
            } else if (userInput.startsWith("@"))
                out.println(toJson(getModel(userName, name, userInput, null, false)));
            else {
                out.println(toJson(getModel(userName, name, userInput, "1", false)));
            }
        }

    }

    private static UserModelC getModel(@Nullable String userName, @Nullable String name, String message, @Nullable String messageTo, boolean isReg) {
        return new UserModelC().Builder()
                .setUserName(userName)
                .setName(name)
                .setMessage(message)
                .setMessageTo(messageTo)
                .setRegistration(isReg);
    }

    private static String toJson(UserModelC data) {
        Gson gson = new Gson();
        return gson.toJson(data);
    }

    private static void affirmUserName() throws IOException {
        while (userName.isEmpty()) {
            System.out.println("You must enter a username!");
            userName = stdIn.readLine();
        }
    }

    private static void connect(String inetAddress, int port) throws IOException {
        socket = new Socket(inetAddress, port);
        stdIn = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    private static void affirmName() throws IOException {
        while (name.isEmpty()) {
            System.out.println("You must enter a name!");
            name = stdIn.readLine();
        }
    }

    private static void close() throws IOException {
        stdIn.close();
        out.close();
        socket.close();
        System.exit(0);
    }

    private static void checkRegistration() throws IOException {
        if (!nameTyped) {
            userName = userInput;
            affirmUserName();
            System.out.println("What  do you wish your display name to be?");
            name = stdIn.readLine();
            affirmName();
            out.println(toJson(getModel(userName, name, userInput, "101011", true)));
            nameTyped = true;
        }
    }
}
