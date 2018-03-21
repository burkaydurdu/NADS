package com.bdurdu;

public class MyColor {

    private int red;
    private int green;
    private int blue;

    private int pixel;

    private void setRgbPixel() {
        red = (pixel >> 16) & 0xff;
        green = (pixel >> 8) & 0xff;
        blue = (pixel) & 0xff;
    }
    public int intPixel() {
        return (red<<16) | (green<<8) | blue;
    }

    public void setPixel(int pixel) {
        this.pixel = pixel;
        setRgbPixel();
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
}
