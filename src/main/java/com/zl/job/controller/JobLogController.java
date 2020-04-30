package com.zl.job.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zl.job.entity.JobLog;
import com.zl.job.model.PageListResp;
import com.zl.job.model.QueryRequest;
import com.zl.job.service.JobLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zl
 */
@RestController
@RequestMapping("job/log")
public class JobLogController {

    @Resource
    private JobLogService jobLogService;

    @PostMapping("/list")
    public PageListResp list(@RequestBody QueryRequest request) {
        IPage<JobLog> page = jobLogService.list(request);
        return new PageListResp<>(page);
    }

}
