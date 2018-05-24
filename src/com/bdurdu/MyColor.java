package com.bdurdu;

class MyColor {

    private int red;
    private int green;
    private int blue;

    private int pixel;

    private void setRgbPixel() {
        red = (pixel >> 16) & 0xff;
        green = (pixel >> 8) & 0xff;
        blue = (pixel) & 0xff;
    }
    int intPixel() {
        return (red<<16) | (green<<8) | blue;
    }

    void setPixel(int pixel) {
        this.pixel = pixel;
        setRgbPixel();
    }

     int getRed() {
        return red;
    }

     void setRed(int red) {
        this.red = red;
    }

     int getGreen() {
        return green;
    }

     void setGreen(int green) {
        this.green = green;
    }

     int getBlue() {
        return blue;
    }

     void setBlue(int blue) {
        this.blue = blue;
    }
}
