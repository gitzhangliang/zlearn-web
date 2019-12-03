package com.zl.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EatListener implements ApplicationListener<EatEvent> {
    @Override
    public void onApplicationEvent(EatEvent event) {
        System.out.println(event.getTime()+"点了，吃饭时间到");
    }
    @EventListener
    public void eat(EatEvent eatEvent){
        System.out.println(eatEvent.getTime()+"点了，吃饭时间到EventListener");
    }
}