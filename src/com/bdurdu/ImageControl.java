package com.bdurdu;

import java.awt.image.BufferedImage;

public class ImageControl {

    private BufferedImage image;
    private MyColor color;

    public ImageControl(BufferedImage image) {
        this.image = image;
        color = new MyColor();
    }
    public ImageControl() {
        color = new MyColor();
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    private int getGrayValue(int pixel) {
        color.setPixel(pixel);
        int avg = ( (color.getRed() + color.getGreen() + color.getBlue()) / 3);
        color.setRed(avg);
        color.setGreen(avg);
        color.setBlue(avg);
        return color.intPixel();
    }

    private int getGValue(int pixel) {
        color.setPixel(pixel);
        color.setRed((int) (color.getRed() * 0.30));
        color.setGreen((int) (color.getGreen() * 0.59));
        color.setBlue((int) (color.getBlue() * 0.11));
        return color.intPixel();
    }

    public BufferedImage getGrayImage() {
        for(int i = 0; i < image.getHeight(); i++)
            for(int j = 0; j < image.getWidth(); j++) {
                image.setRGB(j, i, getGrayValue(image.getRGB(j, i)));
            }
        return image;
    }
}
