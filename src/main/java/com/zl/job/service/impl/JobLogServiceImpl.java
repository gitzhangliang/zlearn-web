package com.zl.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.job.dao.JobLogMapper;
import com.zl.job.entity.JobLog;
import com.zl.job.model.PageAndSort;
import com.zl.job.model.QueryRequest;
import com.zl.job.service.JobLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zl
 */
@Slf4j
@Service("JobLogService")
public class JobLogServiceImpl extends ServiceImpl<JobLogMapper, JobLog> implements JobLogService {

    @Override
    public IPage<JobLog> list(QueryRequest request) {
        LambdaQueryWrapper<JobLog> queryWrapper = new LambdaQueryWrapper<>();
        Page<JobLog> page = PageAndSort.constructPage(request,"create_time",PageAndSort.DESC);
        return this.page(page, queryWrapper);
    }
}
