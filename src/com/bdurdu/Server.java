package com.bdurdu;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server extends Thread {

    private ServerSocket serverSocket;

    public Server(int port)
    {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(180000);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        while(true)
        {
            try
            {
                Socket server = serverSocket.accept();
                DataInputStream din=new DataInputStream(server.getInputStream());
                DataOutputStream dout=new DataOutputStream(server.getOutputStream());

                dout.writeUTF("server: -i am greeting server");
                dout.writeUTF("server:- hi! hello client");

                System.out.println(din.readUTF());
                System.out.println(din.readUTF());

                BufferedImage img=ImageIO.read(ImageIO.createImageInputStream(server.getInputStream()));

                System.out.println("Image received!!!!");
                //lblimg.setIcon(img);
            }
            catch(SocketTimeoutException st)
            {
                System.out.println("Socket timed out!");
                break;
            }
            catch(IOException e)
            {
                e.printStackTrace();
                break;
            }
            catch(Exception ex)
            {
                System.out.println(ex);
            }
        }
    }
}
