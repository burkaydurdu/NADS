package com.bdurdu;

import java.awt.image.BufferedImage;

public class Decryption extends Security{


    private Mod5 mod5;

    public Decryption(BufferedImage mainImage) {

        mod5LenControl();
        mod5 = new Mod5(mainImage);
        mod5.getLength(getWidthPixel(), getHeightPixel());

    }

    public BufferedImage getDecImageMod5() {
        mod5.setDecryption();
        return mod5.setDecryptionImage();
    }
}
