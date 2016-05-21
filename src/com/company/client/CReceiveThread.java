package com.company.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by blaze on 5/20/2016.
 */
class CReceiveThread extends Thread {

    private Socket clientConnection = null;
    private BufferedReader br = null;

    CReceiveThread(Socket clientConnection) {
        this.clientConnection = clientConnection;
    }

    @Override
    public void run() {
        try {
            br = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
            String inputReceived;
            while ((inputReceived = br.readLine()) != null) {
                if (inputReceived.equals("-1028310479")) {
                    closeConnections();
                } else {
                    System.out.println(inputReceived);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void closeConnections() throws IOException {
        br.close();
        clientConnection.close();
        System.exit(0);
    }

}
