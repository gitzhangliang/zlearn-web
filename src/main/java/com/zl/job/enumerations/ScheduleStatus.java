package com.zl.job.enumerations;

public enum ScheduleStatus {

    /**
     * 暂停
     */
    PAUSE("0"),
    /**
     * 正常
     */
    NORMAL("1");

    private String value;

    ScheduleStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}