package com.tzxx.common.tencentservice.bill.concurrent;

import java.util.List;

/**
 * @author tzxx
 */
public class RunnableHolder {
    private List<Runnable> tasks;

    public RunnableHolder(List<Runnable> tasks) {
        this.tasks = tasks;
    }

    public List<Runnable> getTasks() {
        return tasks;
    }

    public void setTasks(List<Runnable> tasks) {
        this.tasks = tasks;
    }
}