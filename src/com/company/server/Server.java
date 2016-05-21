package com.company.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        new ServerControl().start();
        boolean listening = true;
        try(ServerSocket socket = new ServerSocket(4044)) {
            while(listening) {
                Socket client = socket.accept();
                new ServerThread(client).start();
            }
        }
    }
}
