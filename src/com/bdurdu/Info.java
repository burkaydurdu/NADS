package com.bdurdu;

import java.awt.image.BufferedImage;

public class Info {
    public static double [] getPSNRValue(BufferedImage mainImage, BufferedImage cryImage) {
        MyColor color1 = new MyColor();
        MyColor color2 = new MyColor();

        double countRed = 0;
        double countGreen = 0;
        double countBlue = 0;

        for(int i = 0; i < mainImage.getWidth(); i++) {
            for(int j = 0; j < mainImage.getHeight(); j++) {
                color1.setPixel(mainImage.getRGB(j, i));
                color2.setPixel(cryImage.getRGB(j, i));
                countRed += Math.pow(color1.getRed() - color2.getRed(), 2);
                countGreen += Math.pow(color1.getGreen() - color2.getGreen(), 2);
                countBlue += Math.pow(color1.getBlue() - color2.getBlue(), 2);
            }
        }

        countRed /= (mainImage.getHeight() * mainImage.getWidth());
        countGreen /= (mainImage.getHeight() * mainImage.getWidth());
        countBlue /= (mainImage.getHeight() * mainImage.getWidth());

        countRed = 10 * Math.log10( (255 * 255) / countRed);
        countGreen = 10 * Math.log10( (255 * 255) / countGreen);
        countBlue = 10 * Math.log10( (255 * 255) / countBlue);


        return new double[] {countRed, countGreen, countBlue};
    }
}
