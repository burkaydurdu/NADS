package com.bdurdu;

public class MyColor {

    private int alpha;
    private int red;
    private int green;
    private int blue;

    private int pixel;

    public MyColor() {
        alpha = 0;
        red = 0;
        green = 0;
        blue = 0;
        pixel = 0;
    }

    private void setRgbPixel() {
        alpha = (pixel >> 24) & 0xff;
        red = (pixel >> 16) & 0xff;
        green = (pixel >> 8) & 0xff;
        blue = (pixel) & 0xff;
    }
    public int intPixel() {
        return (alpha<<24) | (red<<16) | (green<<8) | blue;
    }

    public void setPixel(int pixel) {
        this.pixel = pixel;
        setRgbPixel();
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
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
