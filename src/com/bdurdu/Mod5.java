package com.bdurdu;

import java.awt.image.BufferedImage;

public class Mod5 {

//  private int kR4; // en son en anlamli

    private int KR[];
    private int KG[];
    private int KB[];

    private int pixel;

    private int mainArray[];
    private int hiddenArray[];

    private int currentCount;
    private int hiddenImageWidth;
    private int hiddenImageHeight;

    private BufferedImage mainImage;

    private MyColor color;
    private MyColor colorR1;
    private MyColor colorR2;

    Mod5(BufferedImage mainImage) {
        this.mainImage = mainImage;
        currentCount = 0;

        KR = new int[4];
        KG = new int[4];
        KB = new int[4];

        color = new MyColor();
        colorR1 = new MyColor();
        colorR2 = new MyColor();

        hiddenImageWidth = 0;
        hiddenImageHeight = 0;

        setMainArray();
    }

    private void setMainArray() {
        int count = 0;
        mainArray = new int[mainImage.getWidth() * mainImage.getHeight()];
        for(int y = 0; y < mainImage.getHeight(); y++)
            for(int x = 0; x < mainImage.getWidth(); x++)
                mainArray[count++] = mainImage.getRGB(x, y);
    }

    /*
    *
    *
    * @ param1 => max width size mod5 pixel count
    * @ param2 => max height size mod5 pixel count
    *
    * */
    void setLength(int widthPixCou, int heightPixCou, int[] widKey, int[] heiKey) {
        int newValue[];
        for(int i = 0; i < widthPixCou; i++) {
            colorR1.setPixel(mainArray[currentCount]);
            colorR2.setPixel(mainArray[currentCount + 1]);
            newValue = setImage(colorR1.getRed(), colorR2.getRed() , widKey[i]);
            colorR1.setRed(newValue[0]);
            colorR2.setRed(newValue[1]);
            mainArray[currentCount] = colorR1.intPixel();
            mainArray[currentCount + 1] = colorR2.intPixel();
            currentCount += 2;
        }

        for(int j = 0; j < heightPixCou; j++) {
            colorR1.setPixel(mainArray[currentCount]);
            colorR2.setPixel(mainArray[currentCount + 1]);
            newValue = setImage(colorR1.getRed(), colorR2.getRed() , heiKey[j]);
            colorR1.setRed(newValue[0]);
            colorR2.setRed(newValue[1]);
            mainArray[currentCount] = colorR1.intPixel();
            mainArray[currentCount + 1] = colorR2.intPixel();
            currentCount += 2;
        }
    }

    void getLength(int widthPixCou, int heightPixCou) {
        for(int i = 0; i < widthPixCou; i++) {
            colorR1.setPixel(mainArray[currentCount]);
            colorR2.setPixel(mainArray[currentCount + 1]);
            hiddenImageWidth +=  ((colorR1.getRed() + colorR2.getRed() * 2 )
                    % 5 )
                    * (int) Math.pow(5, i) ;
            currentCount += 2;
        }

        for(int i = 0; i < heightPixCou; i++) {
            colorR1.setPixel(mainArray[currentCount]);
            colorR2.setPixel(mainArray[currentCount + 1]);
            hiddenImageHeight +=  ((colorR1.getRed() + colorR2.getRed() * 2 )
                    % 5 )
                    * (int) Math.pow(5, i) ;
            currentCount += 2;
        }

        hiddenArray = new int[hiddenImageWidth * hiddenImageHeight];
    }

    private void setKey() {
        int j = 0;
        color.setPixel(pixel);
        int red = color.getRed();
        while(  red > 0 ) {
            KR[j] = red % 5;
            red /= 5;
            j++;
        }
        j = 0;
        int green = color.getGreen();
        while(  green > 0 ) {
            KG[j] = green % 5;
            green /= 5;
            j++;
        }

        j = 0;
        int blue = color.getBlue();
        while(  blue > 0 ) {
            KB[j] = blue % 5;
            blue /= 5;
            j++;
        }
    }

    void setEncryption() {
        int newValue[];

        for(int i = 0; i < 4; i++) {
            colorR1.setPixel(mainArray[currentCount]);
            colorR2.setPixel(mainArray[currentCount + 1]);

            newValue = setImage(colorR1.getRed(), colorR2.getRed() , KR[i]);
            colorR1.setRed(newValue[0]);
            colorR2.setRed(newValue[1]);

            newValue = setImage(colorR1.getGreen(), colorR2.getGreen() , KG[i]);
            colorR1.setGreen(newValue[0]);
            colorR2.setGreen(newValue[1]);

            newValue = setImage(colorR1.getBlue(), colorR2.getBlue() , KB[i]);
            colorR1.setBlue(newValue[0]);
            colorR2.setBlue(newValue[1]);

            mainArray[currentCount] = colorR1.intPixel();
            mainArray[currentCount + 1] = colorR2.intPixel();

            currentCount += 2;
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
            while (count < 4) {
                colorR1.setPixel(mainArray[currentCount]);
                colorR2.setPixel(mainArray[currentCount + 1]);
                red += ((colorR1.getRed() + colorR2.getRed() * 2)
                        % 5)
                        * (int) Math.pow(5, count);
                green += ((colorR1.getGreen() + colorR2.getGreen() * 2)
                        % 5)
                        * (int) Math.pow(5, count);
                blue += ((colorR1.getBlue() + colorR2.getBlue() * 2)
                        % 5)
                        * (int) Math.pow(5, count);
                count++;
                currentCount += 2;
            }
            color.setRed(red);
            color.setGreen(green);
            color.setBlue(blue);
            hiddenArray[i] = color.intPixel();
        }
    }
    BufferedImage getEncryptionImage() {
        int count = 0;
        for(int y = 0; y < mainImage.getHeight(); y++)
            for(int x = 0; x < mainImage.getWidth(); x++) {
                mainImage.setRGB(x,y, mainArray[count++]);
            }
        return mainImage;
    }

    BufferedImage setDecryptionImage() {
        int count = 0;
        BufferedImage hiddenImage = new BufferedImage(hiddenImageWidth, hiddenImageHeight, BufferedImage.TYPE_INT_RGB);
        for(int y = 0; y < hiddenImageHeight; y++)
            for(int x = 0; x < hiddenImageWidth; x++) {
                hiddenImage.setRGB(x, y, hiddenArray[count++]);
            }
        return hiddenImage;
    }

    private int [] setImage(int i1, int i2, int i3 ) {
        int result[] = {i1 , i2};

        if ((i1 + 2 * i2) % 5 != i3) {
            if( (i1 + 1 + 2 * i2) % 5 == i3) {
                result[0] = i1 + 1;
            } else if((i1 - 1 + 2 * i2) % 5 == i3) {
                result[0] = i1 - 1;
            } else if( (i1 + 2 * (i2 + 1)) % 5 == i3) {
                result[1] = i2 + 1;
            } else if((i1 + 2 *  (i2 - 1)) % 5 == i3) {
                result[1] = i2 - 1;
            }
        }
        return result;
    }

    void setPixel(int pixel) {
        this.pixel = pixel;
        setKey();
    }

}
