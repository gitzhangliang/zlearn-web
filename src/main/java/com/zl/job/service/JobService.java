package com.zl.job.service;

import com.zl.job.entity.Job;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.job.model.JobDTO;
import com.zl.job.model.QueryRequest;


public interface JobService extends IService<Job> {

    Job get(Long jobId);

    IPage<Job> list(QueryRequest request);

    void create(JobDTO job);

    void update(JobDTO dto);

    void delete(Long jobId);

    void run(Long jobId);

    void pause(Long jobId);

    void resume(Long jobId);
}
