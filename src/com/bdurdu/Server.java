package com.bdurdu;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    private ServerSocket serverSocket;
    private BufferedImage bufferedImage;

    Server(int port, BufferedImage bufferedImage)
    {
        this.bufferedImage = bufferedImage;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(180000);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        try {
            Socket server = serverSocket.accept();
            System.out.println("Server socket ready on port: " + server.getPort());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "bmp", baos);
            byte[] byteImage = baos.toByteArray();

            OutputStream os = server.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(byteImage);
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BufferedImage getImage() {
        return bufferedImage;
    }
}
