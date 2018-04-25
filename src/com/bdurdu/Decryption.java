package com.bdurdu;

import java.awt.image.BufferedImage;

public class Decryption extends Security{


    private Mod5 mod5;
    private Mod7 mod7;

    public Decryption(BufferedImage mainImage) {

        mod5LenControl();
        mod5 = new Mod5(mainImage);
        mod5.getLength(getWidthPixel(), getHeightPixel());

        mod7LenControl();
        mod7 = new Mod7(mainImage);
        mod7.getLength(getWidthPixel(), getHeightPixel());
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
