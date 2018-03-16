package com.bdurdu;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Security  implements Rules{

    private int widthPixel;
    private int heightPixel;
    private BufferedImage mainImage, hiddenImage;
    private int main_max;
    private int hidden_max;

    public Security (BufferedImage mainImage, BufferedImage hiddenImage) {
        this.mainImage = mainImage;
        this.hiddenImage = hiddenImage;
    }
    public Security() {}

    public boolean controlImageMaxLen() {
        int image_max = maxWidth * maxHeight;
        main_max = mainImage.getWidth() * mainImage.getHeight();
        hidden_max = hiddenImage.getWidth() * hiddenImage.getHeight();
        return main_max <= image_max && hidden_max <= image_max;
    }

    @Override
    public void mod5LenControl() {
        int count = 0;
        int tempMax = maxWidth;
        while(tempMax > 0) {
            tempMax /= 5;
            count++;
        }
        widthPixel = count;
        count = 0;
        tempMax = maxHeight;
        while(tempMax > 0) {
            tempMax /= 5;
            count++;
        }
        heightPixel = count;
    }

    @Override
    public void mod7LenControl() {
        int count = 0;
        int tempMax = maxWidth;
        while(tempMax > 0) {
            tempMax /= 7;
            count++;
        }
        widthPixel = count;
        count = 0;
        tempMax = maxHeight;
        while(tempMax > 0) {
            tempMax /= 7;
            count++;
        }
        heightPixel = count;
    }

    public int[] mod5LenWid() {
        int [] array = new int[getWidthPixel()];
        Arrays.fill(array, 0);
        int wid = hiddenImage.getWidth();
        int j = 0;
        while(wid > 0) {
            array[j] = wid % 5;
            wid /= 5;
            j++;
        }
        return array;
    }

    public int[] mod5LenHei() {
        int [] array = new int[getHeightPixel()];
        Arrays.fill(array, 0);
        int hei = hiddenImage.getHeight();
        int j = 0;
        while(hei > 0) {
            array[j] = hei % 5;
            hei /= 5;
            j++;
        }
        return array;
    }


    @Override
    public boolean isMod5Compression() {
        return main_max > (hidden_max * 8) + widthPixel * 2 + heightPixel * 2;
    }

    @Override
    public boolean isMod7Compression() {
        return main_max > (hidden_max * 12) + widthPixel * 3 + heightPixel * 3;
    }

    public int getWidthPixel() {
        return widthPixel;
    }

    public void setWidthPixel(int widthPixel) {
        this.widthPixel = widthPixel;
    }

    public int getHeightPixel() {
        return heightPixel;
    }

    public void setHeightPixel(int heightPixel) {
        this.heightPixel = heightPixel;
    }
}
