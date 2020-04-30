package com.zl.job.model;

import lombok.Data;

import java.util.Date;

/**
 * @author zl
 * @date 2020/2/19.
 */
@Data
public class JobDTO {

    private Long id;

    private String beanName;

    private String methodName;

    private String params;

    private String cronExpression;

    private String status;

    private String remark;

    /**
     * trigger类型
     */
    private Integer triggerType;

    /**
     * 重复间隔
     */
    private Long repeatInterval;

    /**
     * 重复次数
     */
    private Long repeatCount;

    /**
     * 重复次数
     */
    private Date startTime;
}
