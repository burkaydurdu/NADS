package com.bdurdu;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    private ServerSocket serverSocket;
    private BufferedImage bufferedImage;
    private byte[] byteImage;


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
            byteImage = baos.toByteArray();

            OutputStream os = server.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(byteImage);

        } catch (IOException e) {
            e.printStackTrace();
        }
//        while(true)
//        {
//            try
//            {
//                Socket server = serverSocket.accept();
//                DataInputStream din=new DataInputStream(server.getInputStream());
//                DataOutputStream dout=new DataOutputStream(server.getOutputStream());
//
//                dout.writeUTF("server: -i am greeting server");
//                dout.writeUTF("server:- hi! hello client");
//
//                System.out.println(din.readUTF());
//                System.out.println(din.readUTF());
//
//                bufferedImage = ImageIO.read(ImageIO.createImageInputStream(server.getInputStream()));
//
//                System.out.println("Image received!!!!");
//                //lblimg.setIcon(img);
//            }
//            catch(SocketTimeoutException st)
//            {
//                System.out.println("Socket timed out!");
//                break;
//            }
//            catch(IOException e)
//            {
//                e.printStackTrace();
//                break;
//            }
//            catch(Exception ex)
//            {
//                System.out.println(ex);
//            }
//        }
    }

    public BufferedImage getImage() {
        return bufferedImage;
    }
}
