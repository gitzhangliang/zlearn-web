package com.zl.spi;

/**
 * @author zl
 * @date 2020/3/16.
 */
public class SpiServiceImpl1 implements SpiService {
    @Override
    public void spi() {
        System.out.println(SpiServiceImpl1.class);
    }
}
