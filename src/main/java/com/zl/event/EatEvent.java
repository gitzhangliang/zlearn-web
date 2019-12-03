package com.zl.event;

import lombok.Getter;

import org.springframework.context.ApplicationEvent;

/**
 * @author zhangliang
 * @date 2019/12/3.
 */
@Getter
public class EatEvent extends ApplicationEvent {

    private String time;
    public EatEvent(Object source,String time) {
        super(source);
        this.time = time;
    }

}



