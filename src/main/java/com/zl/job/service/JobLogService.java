package com.zl.job.service;

import com.zl.job.entity.JobLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.job.model.QueryRequest;


public interface JobLogService extends IService<JobLog> {

    IPage<JobLog> list(QueryRequest request);
}
