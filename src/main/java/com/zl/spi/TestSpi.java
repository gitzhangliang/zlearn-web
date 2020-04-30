package com.zl.spi;

import sun.misc.Service;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author zl
 * @date 2020/3/16.
 */
public class TestSpi {
    public static void main(String[] args) {
        Iterator<SpiService> providers = Service.providers(SpiService.class);
        ServiceLoader<SpiService> load = ServiceLoader.load(SpiService.class);

        while(providers.hasNext()) {
            SpiService ser = providers.next();
            ser.spi();
        }
        System.out.println("--------------------------------");
        Iterator<SpiService> iterator = load.iterator();
        while(iterator.hasNext()) {
            SpiService ser = iterator.next();
            ser.spi();
        }
    }
}
