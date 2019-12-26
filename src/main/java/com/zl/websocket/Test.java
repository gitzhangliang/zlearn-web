package com.zl.websocket;

import com.zl.domain.Coder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhangliang
 * @date 2019/10/15.
 */
public class Test {
    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        Coder coder1 = new Coder();
        coder1.setId(1);
        coder1.setName("1");
        Coder coder2 = new Coder();
        coder2.setId(2);
        coder2.setName("2");
        Coder coder3 = new Coder();
        coder3.setId(3);
        coder3.setName("3");
        List<Coder> coders = new ArrayList<>();
        coders.add(coder1);
        coders.add(coder2);
        coders.add(coder3);
        Stream<Coder> stream = coders.stream();
        System.out.println(stream.getClass());

    }

    public static void cons(HashMap<Long, String> a,HashMap<Long, String> b){

    }
    public static void cons1(HashMap<Long, String> a,Coder b){
        a.put(b.getId(), b.getName());
    }


}


