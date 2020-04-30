package com.zl.job.enumerations;

import lombok.Getter;

/**
 * @author zl
 * @date 2020/2/19.
 */
@Getter
public enum TriggerTypeEnum {
    /**
     * 简单
     */
    SIMPLE(0,"simple"),
    /**
     * Cron表达式
     */
    CRON(1,"cron");

    private Integer value;
    private String group;

    TriggerTypeEnum(int value,String group) {
        this.value = value;
        this.group = group;
    }
}
