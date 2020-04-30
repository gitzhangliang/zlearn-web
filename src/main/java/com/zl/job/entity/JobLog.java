package com.zl.job.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zl
 */
@Data
@TableName("t_job_log")
public class JobLog  implements Serializable {


    private static final long serialVersionUID = -7812693495635903200L;

    private Long jobId;
    /**
     * 状态 0=成功,1=失败
     */
    private Boolean status;
    /**
     * 异常信息
     */
    private String error;

    /**
     * 耗时（毫秒）
     */
    private Long times;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
}
