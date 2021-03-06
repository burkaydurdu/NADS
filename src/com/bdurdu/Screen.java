package com.bdurdu;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class Screen extends JFrame implements ActionListener {

    private JMenuItem mainImageMenuItem;
    private JMenuItem hiddenImageMenuItem;
    private JMenuItem cryptedImageMenuItem;
    private JMenuItem encryptMenuItem;
    private JMenuItem decryptMenuItem;
    private JMenuItem psnrMenuItem;

    private JRadioButtonMenuItem radioButtonMode5Item;
    private JRadioButtonMenuItem radioButtonMode7Item;
    private JRadioButtonMenuItem radioButtonAutoItem;
    private JRadioButtonMenuItem radioButtonServerItem;
    private JRadioButtonMenuItem radioButtonClientItem;

    private JTextField inputPortText;
    private JTextField inputHostText;

    private JButton connection;
    private JButton showMainImage;
    private JButton showHiddenImage;
    private JButton showEncImage;

    private JLabel mainImageBox, hiddenImageBox, encryptedImageBox;
    private BufferedImage mainImage, hiddenImage, encryptedImage;

    private enum Mod { MOD5, MOD7, AUTO }

    private enum Role { CLIENT, SERVER }

    private Mod currentMod;
    private Role currentRole;

    Screen() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1280, 720));

        JMenuBar menuBar = onCreateMenu();
        setJMenuBar(menuBar);

        mainImageBox = new JLabel();
        hiddenImageBox = new JLabel();
        encryptedImageBox = new JLabel();

        add(mainImageBox, BorderLayout.WEST);
        add(hiddenImageBox, BorderLayout.CENTER);
        add(encryptedImageBox, BorderLayout.EAST);
        onCreateBottomMenu();

        currentMod = Mod.AUTO;

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private JMenuBar onCreateMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu open = new JMenu("Open");
        JMenu mod = new JMenu("Mod");
        JMenu crypt = new JMenu("Crypt");
        JMenu send = new JMenu("Send");
        JMenu info = new JMenu("Info");

        open.setIcon(new ImageIcon("icon/openi.png"));
        mod.setIcon(new ImageIcon("icon/modi.png"));
        crypt.setIcon(new ImageIcon("icon/encrypy2i.png"));
        send.setIcon(new ImageIcon("icon/send2i.png"));
        info.setIcon(new ImageIcon("icon/infoi.png"));

        ButtonGroup groupMod = new ButtonGroup();
        ButtonGroup groupSend = new ButtonGroup();

        mainImageMenuItem = new JMenuItem("Main Image", new ImageIcon("icon/addi.png"));
        mainImageMenuItem.addActionListener(this);

        hiddenImageMenuItem = new JMenuItem("Hidden Image", new ImageIcon("icon/addi.png"));
        hiddenImageMenuItem.addActionListener(this);

        cryptedImageMenuItem = new JMenuItem("Crypted Image", new ImageIcon("icon/addi.png"));
        cryptedImageMenuItem.addActionListener(this);

        encryptMenuItem = new JMenuItem("Encrypt", new ImageIcon("icon/encrypti.png"));
        encryptMenuItem.addActionListener(this);

        decryptMenuItem = new JMenuItem("Decrypt", new ImageIcon("icon/decrypti.png"));
        decryptMenuItem.addActionListener(this);

        psnrMenuItem = new JMenuItem("PSNR", new ImageIcon("icon/psnri.png"));
        psnrMenuItem.addActionListener(this);

        radioButtonMode5Item = new JRadioButtonMenuItem("Mod5", new ImageIcon("icon/mod5i.png"));
        radioButtonMode5Item.addActionListener(this);

        radioButtonMode7Item = new JRadioButtonMenuItem("Mod7", new ImageIcon("icon/mod7i.png"));
        radioButtonMode7Item.addActionListener(this);

        radioButtonAutoItem = new JRadioButtonMenuItem("Automatic", new ImageIcon("icon/autoi.png"));
        radioButtonAutoItem.addActionListener(this);

        radioButtonServerItem = new JRadioButtonMenuItem("Server", new ImageIcon("icon/sendi.png"));
        radioButtonServerItem.addActionListener(this);

        radioButtonClientItem = new JRadioButtonMenuItem("Client", new ImageIcon("icon/receivei.png"));
        radioButtonClientItem.addActionListener(this);

        inputPortText = new JTextField("Input Port");
        inputPortText.setPreferredSize(new Dimension(200, 24));

        inputHostText = new JTextField("Input Host");
        inputHostText.setPreferredSize(new Dimension(200, 24));

        connection = new JButton("Connection", new ImageIcon("icon/connecti.png"));
        connection.addActionListener(this);

        menuBar.add(open);
        menuBar.add(mod);
        menuBar.add(crypt);
        menuBar.add(send);
        menuBar.add(info);

        open.add(mainImageMenuItem);
        open.add(hiddenImageMenuItem);
        open.add(cryptedImageMenuItem);

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

        send.add(inputHostText);
        send.add(inputPortText);
        send.add(radioButtonServerItem);
        send.add(radioButtonClientItem);
        send.add(connection);

        info.add(psnrMenuItem);

        return menuBar;
    }

    private void onCreateBottomMenu() {
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusPanel.setPreferredSize(new Dimension(getWidth(), 16));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));

        showMainImage = new JButton("Main Image Show");
        showMainImage.addActionListener(this);
        showMainImage.setHorizontalAlignment(SwingConstants.LEFT);

        showHiddenImage = new JButton("Hidden Image Show");
        showHiddenImage.addActionListener(this);
        showHiddenImage.setHorizontalAlignment(SwingConstants.LEFT);

        showEncImage = new JButton("Encrypt Image Show");
        showEncImage.addActionListener(this);
        showEncImage.setHorizontalAlignment(SwingConstants.LEFT);

        statusPanel.add(showMainImage);
        statusPanel.add(showHiddenImage);
        statusPanel.add(showEncImage);

        add(statusPanel, BorderLayout.SOUTH);
    }

    private void openFile(int control) {
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                if (control == 0) {
                    mainImageBox.setIcon(new ImageIcon(scaleImage(ImageIO.read(file))));
                    mainImage = ImageIO.read(file);
                } else if(control == 1) {
                    hiddenImageBox.setIcon(new ImageIcon(ImageIO.read(file)));
                    hiddenImage = ImageIO.read(file);
                } else if(control == 2) {
                    mainImageBox.setIcon(new ImageIcon(scaleImage(ImageIO.read(file))));
                    encryptedImage = ImageIO.read(file);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Image scaleImage(Image image) {
        int width = image.getWidth(this);
        int height = image.getHeight(this);

        width *= 0.7;
        height *= 0.7;

        return image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(mainImageMenuItem)) {
            openFile(0);
        }
        else if (e.getSource().equals(hiddenImageMenuItem)) {
            openFile(1);
        }
        else if (e.getSource().equals(cryptedImageMenuItem)) {
            openFile(2);
        }
        else if (e.getSource().equals(showMainImage)) {
            if(mainImage != null) new ShowImage(mainImage, this);
        }
        else if (e.getSource().equals(showHiddenImage)) {
            if(hiddenImage != null) new ShowImage(hiddenImage, this);
        }
        else if (e.getSource().equals(showEncImage)) {
            if(encryptedImage != null) new ShowImage(encryptedImage, this);
        }
        else if(e.getSource().equals(encryptMenuItem)) {
            if(hiddenImage != null) {
                Encryption encryption = new Encryption(mainImage, hiddenImage);
                if (currentMod == Mod.MOD5) {
                    String message = encryption.mod5SizeControl();
                    if (message.equals("TRUE")) {
                        encryptedImage = encryption.getEncImageMod5();
                        encryptedImageBox.setIcon(new ImageIcon(scaleImage(encryptedImage)));
                    } else JOptionPane.showMessageDialog(null, message);
                } else if (currentMod == Mod.MOD7) {
                    String message = encryption.mod7SizeControl();
                    if (message.equals("TRUE")) {
                        encryptedImage = encryption.getEncImageMod7();
                        encryptedImageBox.setIcon(new ImageIcon(scaleImage(encryptedImage)));
                    } else JOptionPane.showMessageDialog(null, message);

                } else if(currentMod == Mod.AUTO) {
                    if(!encryption.mod7SizeControl().equals("TRUE")) {
                        if(!encryption.mod5SizeControl().equals("TRUE")) {
                            JOptionPane.showMessageDialog(null, "Gizli resim ana resime saklanamiyor!");
                        } else {
                            currentMod = Mod.MOD5;
                            encryptedImage = encryption.getEncImageMod5();
                            encryptedImageBox.setIcon(new ImageIcon(scaleImage(encryptedImage)));
                            JOptionPane.showMessageDialog(null, "Mod5 ile sifrenlendi!");
                        }
                    } else {
                        currentMod = Mod.MOD7;
                        encryptedImage = encryption.getEncImageMod7();
                        encryptedImageBox.setIcon(new ImageIcon(scaleImage(encryptedImage)));
                        JOptionPane.showMessageDialog(null, "Mod7 ile sifrenlendi!");
                    }
                }
            } else JOptionPane.showMessageDialog(null, "Resim yok!");
        }
        else if(e.getSource().equals(decryptMenuItem)) {
            if(encryptedImage != null) {
                Decryption decryption = new Decryption(encryptedImage);
                if(decryption.getModType() == 0 ) {
                    new ShowImage(decryption.getDecImageMod5(), this);
                    JOptionPane.showMessageDialog(null, "Mod5 ile desifrelendi");
                }
                else if(decryption.getModType() == 1) {
                    new ShowImage(decryption.getDecImageMod7(), this);
                    JOptionPane.showMessageDialog(null, "Mod7 ile desifrelendi");
                }
            } else JOptionPane.showMessageDialog(null, "Sifreli resim yok!");

        }
        else if(e.getSource().equals(radioButtonMode5Item)) currentMod = Mod.MOD5;
        else if(e.getSource().equals(radioButtonMode7Item)) currentMod = Mod.MOD7;
        else if(e.getSource().equals(radioButtonAutoItem)) currentMod = Mod.AUTO;
        else if(e.getSource().equals(psnrMenuItem)) {
            if(encryptedImage != null && mainImage != null) {
                double[] array = Info.getPSNRValue(mainImage, encryptedImage);
                DecimalFormat decimalFormat = new DecimalFormat(".0000");
                JOptionPane.showConfirmDialog(null,
                        "RED PSNR : " + String.valueOf(decimalFormat.format(array[0])) + "\n" +
                                "GREEN PSNR : " + String.valueOf(decimalFormat.format(array[1])) + "\n" +
                                "BLUE PSNR : " + String.valueOf(decimalFormat.format(array[2])), "PSNR", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            } else JOptionPane.showConfirmDialog(null, "Oncelikle Sifrelemeyi Calistirin!", "PSNR", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        }
        else if(e.getSource().equals(radioButtonClientItem)) currentRole = Role.CLIENT;
        else if(e.getSource().equals(radioButtonServerItem)) currentRole = Role.SERVER;
        else if(e.getSource().equals(connection)) {
            String host = inputHostText.getText();
            int port = Integer.parseInt(inputPortText.getText());
            if(host != null && currentRole == Role.CLIENT) {
                    Client client = new Client(host, port);
                    client.onCreateConnection();
            } else {
                if (inputPortText.getText() != null && encryptedImage != null) {
                    Server server = new Server(port, encryptedImage);
                    server.start();
                }
            }
        }
    }
}
