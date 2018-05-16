package com.bdurdu;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private Image image;
    private BufferedImage bufferedImage;
    private Byte[] bytes;
    private int port;

    private final static String SERVER_NAME = "localhost";

    public Client(int port) {
        this.port = port;
    }

    private void onCreateConnection() {
        try {

            System.out.println("Connection : " + SERVER_NAME + " PORT : " +port);
            Socket client = new Socket(SERVER_NAME, port);

            System.out.println("Just connected to "
                    + client.getRemoteSocketAddress());

            DataInputStream in = new DataInputStream(client.getInputStream());
            System.out.println(in.readUTF());
            System.out.println(in.readUTF());

            DataOutputStream out = new DataOutputStream(client.getOutputStream());

            out.writeUTF("Hello from "
                    + client.getLocalSocketAddress());
            out.writeUTF("client: hello to server");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getImage() {

    }

    private void sendImage() {

    }
}
