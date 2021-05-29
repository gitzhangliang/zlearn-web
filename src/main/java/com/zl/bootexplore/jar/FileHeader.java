package com.zl.bootexplore.jar;

interface FileHeader {
    boolean hasName(String var1, String var2);

    long getLocalHeaderOffset();

    long getCompressedSize();

    long getSize();

    int getMethod();
}
