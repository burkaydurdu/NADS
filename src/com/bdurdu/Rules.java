package com.bdurdu;

public interface Rules {
    int maxWidth = 1080;
    int maxHeight = 1080;
    void mod5LenControl();
    void mod7LenControl();
    boolean isMod5Compression();
    boolean isMod7Compression();
}
