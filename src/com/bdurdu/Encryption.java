package com.bdurdu;

import java.awt.image.BufferedImage;

class Encryption extends Security {

    private BufferedImage mainImage;
    private BufferedImage hiddenImage;

    private Mod5 mod5;
    private Mod7 mod7;

    private MyColor color;


    Encryption(BufferedImage mainImage, BufferedImage hiddenImage) {
        super(mainImage, hiddenImage);

        color = new MyColor();

        this.mainImage = mainImageControl(mainImage);
        this.hiddenImage = hiddenImage;

        mod5 = new Mod5(this.mainImage);
        mod7 = new Mod7(this.mainImage);
    }

    private BufferedImage mainImageControl(BufferedImage oldImage) {
        BufferedImage image = new BufferedImage(oldImage.getWidth(), oldImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int y = 0; y < oldImage.getHeight(); y++)
            for(int x = 0; x < oldImage.getWidth(); x++) {
                color.setPixel(oldImage.getRGB(x, y));
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
                image.setRGB(x, y, color.intPixel());
            }

        return image;
    }

    String mod5SizeControl() {
        if(!controlImageMaxLen()) return String.format("Belirtilen boyutlandirmanin sinir degerinden fazla\n" +
                "Max = { %d x %d }", maxWidth, maxHeight);
        else if(!isMod5Compression()) return String.format("Resim Boyutu { %d x %d}\n" +
                "Sifrelenicek Resim boyutu { %d x %d}", mainImage.getWidth(), mainImage.getHeight(), hiddenImage.getWidth(), hiddenImage.getHeight());
        return "TRUE";
    }

    String mod7SizeControl() {
        if(!controlImageMaxLen()) return String.format("Belirtilen boyutlandirmanin sinir degerinden fazla\n" +
                "Max = { %d x %d }", maxWidth, maxHeight);
        else if(!isMod7Compression()) return String.format("Resim Boyutu { %d x %d}\n" +
                "Sifrelenicek Resim boyutu { %d x %d}", mainImage.getWidth(), mainImage.getHeight(), hiddenImage.getWidth(), hiddenImage.getHeight());
        return "TRUE";
    }

    BufferedImage getEncImageMod5() {
        mod5LenControl();
        mod5.setModType();
        mod5.setLength(getWidthPixel(), getHeightPixel(), mod5LenWid(), mod5LenHei());

        for (int y = 0; y < hiddenImage.getHeight(); y++)
            for (int x = 0; x < hiddenImage.getWidth(); x++) {
                mod5.setPixel(hiddenImage.getRGB(x, y));
                mod5.setEncryption();
            }
        return mod5.getEncryptionImage();
    }

    BufferedImage getEncImageMod7() {

        mod7LenControl();
        mod7.setModType();
        mod7.setLength(getWidthPixel(), getHeightPixel(), mod7LenWid(), mod7LenHei());

        for(int y = 0; y < hiddenImage.getHeight(); y++)
            for(int x = 0; x < hiddenImage.getWidth(); x++) {
                mod7.setPixel(hiddenImage.getRGB(x, y));
                mod7.setEncryption();
            }
        return mod7.getEncryptionImage();
    }
}
