package com.bdurdu;

import javax.imageio.ImageIO;
import javax.swing.*;
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


    private JLabel mainImageBox, hiddenImageBox, cryptedImageBox;
    private BufferedImage mainImage, hiddenImage, cryptedImage;
    private Encryption encryption;
    private Decryption decryption;

    public Screen() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1280, 720));

        JMenuBar menuBar = onCreateMenu();
        this.setJMenuBar(menuBar);

        mainImageBox = new JLabel();
        hiddenImageBox = new JLabel();
        cryptedImageBox = new JLabel();

        add(mainImageBox, BorderLayout.WEST);
        add(hiddenImageBox, BorderLayout.CENTER);
        add(cryptedImageBox, BorderLayout.EAST);

        setVisible(true);
    }

    private JMenuBar onCreateMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu open = new JMenu("Open");

        JMenu crypt = new JMenu("Crypt");

        mainImageMenuItem = new JMenuItem("Main Image");
        mainImageMenuItem.addActionListener(this);

        hiddenImageMenuItem = new JMenuItem("Hidden Image");
        hiddenImageMenuItem.addActionListener(this);

        encryptMenuItem = new JMenuItem("Encrypt");
        encryptMenuItem.addActionListener(this);

        decryptMenuItem = new JMenuItem("Decrypt");
        decryptMenuItem.addActionListener(this);

        menuBar.add(open);
        menuBar.add(crypt);

        open.add(mainImageMenuItem);
        open.add(hiddenImageMenuItem);

        crypt.add(encryptMenuItem);
        crypt.add(decryptMenuItem);

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
        }
        else if(e.getSource().equals(encryptMenuItem)) {
            encryption = new Encryption(mainImage, hiddenImage);
            encryption.mainImageControl();
            cryptedImage = encryption.getEncImageMod5();
            if (cryptedImage != null)
                cryptedImageBox.setIcon(new ImageIcon(cryptedImage));
            else
                JOptionPane.showMessageDialog(null, "Olusturulamiyor...");
        } else if(e.getSource().equals(decryptMenuItem)) {
            decryption = new Decryption(cryptedImage);
            new ShowImage(decryption.getDecImageMod5(), this);
        }
    }
}
