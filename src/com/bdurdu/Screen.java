package com.bdurdu;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Screen extends JFrame implements ActionListener {

    private JMenuItem mainImageMenuItem;
    private JMenuItem hiddenImageMenuItem;
    private JMenuItem encryptMenuItem;
    private JMenuItem decryptMenuItem;

    private JRadioButtonMenuItem radioButtonMode5Item;
    private JRadioButtonMenuItem radioButtonMode7Item;
    private JRadioButtonMenuItem radioButtonAutoItem;
    private JRadioButtonMenuItem radioButtonServerItem;
    private JRadioButtonMenuItem radioButtonClientItem;

    private JTextField inputPortText;

    private JButton connection;

    private JLabel mainImageBox, hiddenImageBox, encryptedImageBox;
    private BufferedImage mainImage, hiddenImage, encryptedImage;

    private enum Mod{
        MOD5, MOD7, AUTO
    }

    private Mod currentMod;

    Screen() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1280, 720));

        JMenuBar menuBar = onCreateMenu();
        this.setJMenuBar(menuBar);

        mainImageBox = new JLabel();
        hiddenImageBox = new JLabel();
        encryptedImageBox = new JLabel();

        add(mainImageBox, BorderLayout.WEST);
        add(hiddenImageBox, BorderLayout.CENTER);
        add(encryptedImageBox, BorderLayout.EAST);

        setVisible(true);
    }

    private JMenuBar onCreateMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu open = new JMenu("Open");
        JMenu mod = new JMenu("Mod");
        JMenu crypt = new JMenu("Crypt");
        JMenu send = new JMenu("Send");


        ButtonGroup groupMod = new ButtonGroup();
        ButtonGroup groupSend = new ButtonGroup();


        mainImageMenuItem = new JMenuItem("Main Image");
        mainImageMenuItem.addActionListener(this);

        hiddenImageMenuItem = new JMenuItem("Hidden Image");
        hiddenImageMenuItem.addActionListener(this);

        encryptMenuItem = new JMenuItem("Encrypt");
        encryptMenuItem.addActionListener(this);

        decryptMenuItem = new JMenuItem("Decrypt");
        decryptMenuItem.addActionListener(this);


        radioButtonMode5Item = new JRadioButtonMenuItem("Mod5");
        radioButtonMode5Item.addActionListener(this);

        radioButtonMode7Item = new JRadioButtonMenuItem("Mod7");
        radioButtonMode7Item.addActionListener(this);

        radioButtonAutoItem = new JRadioButtonMenuItem("Automatic");
        radioButtonAutoItem.addActionListener(this);

        radioButtonServerItem = new JRadioButtonMenuItem("Server");
        radioButtonServerItem.addActionListener(this);

        radioButtonClientItem = new JRadioButtonMenuItem("Client");
        radioButtonClientItem.addActionListener(this);

        inputPortText = new JTextField("Input Port");
        inputPortText.setPreferredSize(new Dimension(200, 24));

        connection = new JButton("Connection");

        menuBar.add(open);
        menuBar.add(mod);
        menuBar.add(crypt);
        menuBar.add(send);

        open.add(mainImageMenuItem);
        open.add(hiddenImageMenuItem);

        mod.add(radioButtonMode5Item);
        mod.add(radioButtonMode7Item);
        mod.add(radioButtonAutoItem);

        groupMod.add(radioButtonMode5Item);
        groupMod.add(radioButtonMode7Item);
        groupMod.add(radioButtonAutoItem);

        groupSend.add(radioButtonServerItem);
        groupSend.add(radioButtonClientItem);

        crypt.add(encryptMenuItem);
        crypt.add(decryptMenuItem);

        send.add(inputPortText);
        send.add(radioButtonServerItem);
        send.add(radioButtonClientItem);
        send.add(connection);

        return menuBar;
    }

    private void openFile(int control) {
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                if (control == 0) {
                    mainImageBox.setIcon(new ImageIcon(ImageIO.read(file)));
                    mainImage = ImageIO.read(file);
                } else {
                    hiddenImageBox.setIcon(new ImageIcon(ImageIO.read(file)));
                    hiddenImage = ImageIO.read(file);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(mainImageMenuItem)) {
            openFile(0);
        } else if (e.getSource().equals(hiddenImageMenuItem)) {
            openFile(1);
        } else if(e.getSource().equals(encryptMenuItem)) {
            if(encryptedImage != null) {
                Encryption encryption = new Encryption(mainImage, hiddenImage);
                if (currentMod == Mod.MOD5) {
                    String message = encryption.mod5SizeControl();
                    if (message.equals("TRUE")) {
                        encryptedImage = encryption.getEncImageMod5();
                        encryptedImageBox.setIcon(new ImageIcon(encryptedImage));
                    } else JOptionPane.showMessageDialog(null, message);
                } else if (currentMod == Mod.MOD7) {
                    String message = encryption.mod7SizeControl();
                    if (message.equals("TRUE")) {
                        encryptedImage = encryption.getEncImageMod7();
                        encryptedImageBox.setIcon(new ImageIcon(encryptedImage));
                    } else JOptionPane.showMessageDialog(null, message);
                } else if (currentMod == Mod.AUTO) {

                }
            } else JOptionPane.showMessageDialog(null, "Resim yok!");
        } else if(e.getSource().equals(decryptMenuItem)) {
            if(encryptedImage != null) {
                Decryption decryption = new Decryption(encryptedImage);
                if(currentMod == Mod.MOD5) {
                    new ShowImage(decryption.getDecImageMod5(), this);
                } else if(currentMod == Mod.MOD7) {
                    new ShowImage(decryption.getDecImageMod7(), this);
                }
            } else JOptionPane.showMessageDialog(null, "Sifreli resim yok!");

        }
        else if(e.getSource().equals(radioButtonMode5Item)) currentMod = Mod.MOD5;
        else if(e.getSource().equals(radioButtonMode7Item)) currentMod = Mod.MOD7;
        else if(e.getSource().equals(radioButtonAutoItem)) currentMod = Mod.AUTO;
    }
}
