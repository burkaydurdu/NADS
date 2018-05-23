package com.bdurdu;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class Client {

    private Image image;
    private Byte[] bytes;
    private int port;
    private String host;
    private Socket client;


    Client(String host, int port) {
        this.port = port;
        this.host = host;
    }

    void onCreateConnection() {

        try {
            client = new Socket(host, port);
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
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
