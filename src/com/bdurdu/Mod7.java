package com.bdurdu;

import java.awt.image.BufferedImage;
import java.util.Arrays;

class Mod7 {

//  private int kR4; // en son en anlamli

    private int KR[];
    private int KG[];
    private int KB[];

    private int mainArray[];
    private int hiddenArray[];

    private int currentCount;
    private int hiddenImageWidth;
    private int hiddenImageHeight;

    private BufferedImage mainImage;

    private MyColor color;
    private MyColor colorR1;
    private MyColor colorR2;
    private MyColor colorR3;

    Mod7(BufferedImage mainImage) {
        this.mainImage = mainImage;
        currentCount = 0;

        KR = new int[3];
        KG = new int[3];
        KB = new int[3];

        color = new MyColor();
        colorR1 = new MyColor();
        colorR2 = new MyColor();
        colorR3 = new MyColor();

        hiddenImageWidth = 0;
        hiddenImageHeight = 0;

        setMainArray();
    }

    private void setMainArray() {
        int count = 0;
        mainArray = new int[mainImage.getWidth() * mainImage.getHeight()];
        for (int y = 0; y < mainImage.getHeight(); y++)
            for (int x = 0; x < mainImage.getWidth(); x++)
                mainArray[count++] = mainImage.getRGB(x, y);
    }

    /*
    *
    *
    * @ param1 => max width size mod7 pixel count
    * @ param2 => max height size mod7 pixel count
    *
    *
    * */
    void setLength(int widthPixCou, int heightPixCou, int[] widKey, int[] heiKey) {
        int newValue[];
        for (int i = 0; i < widthPixCou; i++) {
            colorR1.setPixel(mainArray[currentCount]);
            colorR2.setPixel(mainArray[currentCount + 1]);
            colorR3.setPixel(mainArray[currentCount + 2]);
            newValue = setImage(colorR1.getGreen(), colorR2.getGreen(), colorR3.getGreen(), widKey[i]);

            colorR1.setGreen(newValue[0]);
            colorR2.setGreen(newValue[1]);
            colorR3.setGreen(newValue[2]);

            mainArray[currentCount] = colorR1.intPixel();
            mainArray[currentCount + 1] = colorR2.intPixel();
            mainArray[currentCount + 2] = colorR3.intPixel();
            currentCount += 3;
        }

        for (int j = 0; j < heightPixCou; j++) {
            colorR1.setPixel(mainArray[currentCount]);
            colorR2.setPixel(mainArray[currentCount + 1]);
            colorR3.setPixel(mainArray[currentCount + 2]);

            newValue = setImage(colorR1.getGreen(), colorR2.getGreen(), colorR3.getGreen(), heiKey[j]);
            colorR1.setGreen(newValue[0]);
            colorR2.setGreen(newValue[1]);
            colorR3.setGreen(newValue[2]);

            mainArray[currentCount] = colorR1.intPixel();
            mainArray[currentCount + 1] = colorR2.intPixel();
            mainArray[currentCount + 2] = colorR3.intPixel();
            currentCount += 3;
        }
    }

    void getLength(int widthPixCou, int heightPixCou) {
        currentCount++;
        for (int i = 0; i < widthPixCou; i++) {
            colorR1.setPixel(mainArray[currentCount]);
            colorR2.setPixel(mainArray[currentCount + 1]);
            colorR3.setPixel(mainArray[currentCount + 2]);

            hiddenImageWidth += ((colorR1.getGreen() + colorR2.getGreen() * 2 + colorR3.getGreen() * 3)
                    % 7)
                    * (int) Math.pow(7, i);
            currentCount += 3;
        }

        for (int i = 0; i < heightPixCou; i++) {
            colorR1.setPixel(mainArray[currentCount]);
            colorR2.setPixel(mainArray[currentCount + 1]);
            colorR3.setPixel(mainArray[currentCount + 2]);

            hiddenImageHeight += ((colorR1.getGreen() + colorR2.getGreen() * 2 + colorR3.getGreen() * 3)
                    % 7)
                    * (int) Math.pow(7, i);
            currentCount += 3;
        }

        hiddenArray = new int[hiddenImageWidth * hiddenImageHeight];
    }

    void setModType() {
        color.setPixel(mainArray[currentCount]);
        int val = (color.getBlue() & 0xFE) | 0x01;  //11111110 AND OR 00000001
        color.setBlue(val);
        mainArray[currentCount] = color.intPixel();
        currentCount++;
    }

    private void setKey() {
        int j = 0;
        Arrays.fill(KR, 0);
        Arrays.fill(KG, 0);
        Arrays.fill(KB, 0);
        int red = color.getRed();
        while (red > 0) {
            KR[j] = red % 7;
            red /= 7;
            j++;
        }
        j = 0;
        int green = color.getGreen();
        while (green > 0) {
            KG[j] = green % 7;
            green /= 7;
            j++;
        }

        j = 0;
        int blue = color.getBlue();
        while (blue > 0) {
            KB[j] = blue % 7;
            blue /= 7;
            j++;
        }
    }

    void setEncryption() {
        int newValue[];

        for (int i = 0; i < 3; i++) {
            colorR1.setPixel(mainArray[currentCount]);
            colorR2.setPixel(mainArray[currentCount + 1]);
            colorR3.setPixel(mainArray[currentCount + 2]);

            newValue = setImage(colorR1.getRed(), colorR2.getRed(), colorR3.getRed(), KR[i]);
            colorR1.setRed(newValue[0]);
            colorR2.setRed(newValue[1]);
            colorR3.setRed(newValue[2]);

            newValue = setImage(colorR1.getGreen(), colorR2.getGreen(), colorR3.getGreen(), KG[i]);
            colorR1.setGreen(newValue[0]);
            colorR2.setGreen(newValue[1]);
            colorR3.setGreen(newValue[2]);

            newValue = setImage(colorR1.getBlue(), colorR2.getBlue(), colorR3.getBlue(), KB[i]);
            colorR1.setBlue(newValue[0]);
            colorR2.setBlue(newValue[1]);
            colorR3.setBlue(newValue[2]);

            mainArray[currentCount] = colorR1.intPixel();
            mainArray[currentCount + 1] = colorR2.intPixel();
            mainArray[currentCount + 2] = colorR3.intPixel();

            currentCount += 3;
        }
    }

    void setDecryption() {
        int red, green, blue;
        int count;
        for (int i = 0; i < hiddenArray.length; i++) {
            red = 0;
            green = 0;
            blue = 0;
            count = 0;

            while (count < 3) {
                colorR1.setPixel(mainArray[currentCount]);
                colorR2.setPixel(mainArray[currentCount + 1]);
                colorR3.setPixel(mainArray[currentCount + 2]);

                red += ((colorR1.getRed() + colorR2.getRed() * 2 + colorR3.getRed() * 3)
                        % 7)
                        * (int) Math.pow(7, count);
                green += ((colorR1.getGreen() + colorR2.getGreen() * 2 + colorR3.getGreen() * 3)
                        % 7)
                        * (int) Math.pow(7, count);
                blue += ((colorR1.getBlue() + colorR2.getBlue() * 2 + colorR3.getBlue() * 3)
                        % 7)
                        * (int) Math.pow(7, count);
                count++;
                currentCount += 3;
            }
            color.setRed(red);
            color.setGreen(green);
            color.setBlue(blue);
            hiddenArray[i] = color.intPixel();
        }
    }

    BufferedImage getEncryptionImage() {
        int count = 0;
        BufferedImage image = new BufferedImage(mainImage.getWidth(), mainImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < mainImage.getHeight(); y++)
            for (int x = 0; x < mainImage.getWidth(); x++) {
                image.setRGB(x, y, mainArray[count++]);
            }
        return image;
    }

    BufferedImage getDecryptionImage() {
        int count = 0;
        BufferedImage hiddenImage = new BufferedImage(hiddenImageWidth, hiddenImageHeight, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < hiddenImageHeight; y++)
            for (int x = 0; x < hiddenImageWidth; x++) {
                hiddenImage.setRGB(x, y, hiddenArray[count++]);
            }
        return hiddenImage;
    }

    private int[] setImage(int i1, int i2, int i3, int i4) {
        int result[] = {i1, i2, i3};

        if ((i1 + 2 * i2 + 3 * i3) % 7 != i4) {
            if ((i1 + 1 + 2 * i2 + 3 * i3) % 7 == i4) {
                result[0] = i1 + 1;
            } else if ((i1 - 1 + 2 * i2 + 3 * i3) % 7 == i4) {
                result[0] = i1 - 1;
            } else if ((i1 + 2 * (i2 + 1) + 3 * i3) % 7 == i4) {
                result[1] = i2 + 1;
            } else if ((i1 + 2 * (i2 - 1) + 3 * i3) % 7 == i4) {
                result[1] = i2 - 1;
            } else if((i1 + 2 * i2 + 3 * (i3 - 1)) % 7 == i4) {
                result[2] = i3 - 1;
            } else if((i1 + 2 * i2 + 3 * (i3 + 1)) % 7 == i4) {
                result[2] = i3 + 1;
            }
        }
        return result;
    }

    void setPixel(int pixel) {
        color.setPixel(pixel);
        setKey();
    }
}
