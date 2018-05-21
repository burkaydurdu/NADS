package com.bdurdu;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private Image image;
    private Byte[] bytes;
    private int port;
    private Socket client;

    private final static String SERVER_NAME = "localhost";

    Client(int port) {
        this.port = port;
    }

    void onCreateConnection() {


        try {
            client = new Socket(SERVER_NAME, port);
            System.out.println("Connection to host established.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            byte[] byteImage = (byte[])ois.readObject();
            InputStream in = new ByteArrayInputStream(byteImage);
            BufferedImage bufferedImage = ImageIO.read(in);
            ImageIO.write(bufferedImage, "bmp", new File(
                    "gelen.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


//        try {
//
//            System.out.println("Connection : " + SERVER_NAME + " PORT : " +port);
//            Socket client = new Socket(SERVER_NAME, port);
//
//            System.out.println("Just connected to "
//                    + client.getRemoteSocketAddress());
//
//            DataInputStream in = new DataInputStream(client.getInputStream());
//            System.out.println(in.readUTF());
//            System.out.println(in.readUTF());
//
//            DataOutputStream out = new DataOutputStream(client.getOutputStream());
//
//            out.writeUTF("Hello from "
//                    + client.getLocalSocketAddress());
//            out.writeUTF("client: hello to server");
//
//            ImageIO.write(bufferedImage,"jpg", client.getOutputStream());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
