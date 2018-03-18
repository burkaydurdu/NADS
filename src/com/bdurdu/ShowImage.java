package com.bdurdu;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class ShowImage extends JFrame{

    ShowImage(BufferedImage image, JFrame main) {

        Rectangle r = main.getBounds();

        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(image));

        JPanel imagePanel = new JPanel();
        imagePanel.add(label);

        JScrollPane scrollPane = new JScrollPane(imagePanel);
        scrollPane.setSize(r.width / 2, r.height / 2);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane, BorderLayout.CENTER);
        setSize(new Dimension(r.width / 2, r.height / 2));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
