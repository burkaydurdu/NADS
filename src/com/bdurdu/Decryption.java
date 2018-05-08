package com.bdurdu;

import java.awt.image.BufferedImage;

public class Decryption extends Security{


    private Mod5 mod5;
    private Mod7 mod7;
    private MyColor color;
    private BufferedImage mainImage;

    public Decryption(BufferedImage mainImage) {

        this.mainImage = mainImage;
        color = new MyColor();

        mod5LenControl();
        mod5 = new Mod5(mainImage);
        mod5.getLength(getWidthPixel(), getHeightPixel());

        mod7LenControl();
        mod7 = new Mod7(mainImage);
        mod7.getLength(getWidthPixel(), getHeightPixel());
    }

    public int getModType() {
        color.setPixel(mainImage.getRGB(0,0));
        return color.getBlue() & 0x01;
    }

    public BufferedImage getDecImageMod5() {
        mod5.setDecryption();
        return mod5.getDecryptionImage();
    }

    public BufferedImage getDecImageMod7() {
        mod7.setDecryption();
        return mod7.getDecryptionImage();
    }
}
