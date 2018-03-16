package com.bdurdu;

import java.awt.image.BufferedImage;

public class Encryption extends Security {

    private BufferedImage mainImage;
    private BufferedImage hiddenImage;

    private Mod5 mod5;

    private MyColor color;


    public Encryption(BufferedImage mainImage, BufferedImage hiddenImage) {
        super(mainImage, hiddenImage);

        this.mainImage = mainImage;
        this.hiddenImage = hiddenImage;


        mod5 = new Mod5(mainImage);
        color = new MyColor();
    }

    public void mainImageControl() {
        for(int y = 0; y < mainImage.getHeight(); y++)
            for(int x = 0; x < mainImage.getWidth(); x++) {
                color.setPixel(mainImage.getRGB(x, y));
                if(color.getRed() == 0) {
                    color.setRed(1);
                } else if(color.getRed() == 255) {
                    color.setRed(254);
                }

                if(color.getGreen() == 0) {
                    color.setGreen(1);
                } else if(color.getGreen() == 255) {
                    color.setGreen(254);
                }

                if(color.getBlue() == 0) {
                    color.setBlue(1);
                } else if(color.getBlue() == 255) {
                    color.setBlue(254);
                }
                mainImage.setRGB(x, y, color.intPixel());
            }
    }

    public BufferedImage getEncImageMod5() {

        if(controlImageMaxLen() && isMod5Compression()) {

            mod5LenControl();

            mod5.setLength(getWidthPixel(), getHeightPixel(), mod5LenWid(), mod5LenHei());

            for (int y = 0; y < hiddenImage.getHeight(); y++)
                for (int x = 0; x < hiddenImage.getWidth(); x++) {
                    mod5.setPixel(hiddenImage.getRGB(x, y));
                    mod5.setEncryption();
                }

            return mod5.getEncryptionImage();
        }
        return null;
    }
}
