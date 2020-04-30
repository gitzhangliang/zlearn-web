package com.zl.job.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_job")
public class Job implements Serializable {

    private static final long serialVersionUID = 400066840871805700L;

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

    /**
     * 任务组名
     */
    private String jobGroup;

    @TableField(exist = false)
    private Long jobId;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
}
