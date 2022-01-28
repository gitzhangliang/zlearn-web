package com.zl.job.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zl.job.entity.Job;
import com.zl.job.model.JobDTO;
import com.zl.job.model.PageListResp;
import com.zl.job.model.QueryRequest;
import com.zl.job.service.JobService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zl
 */
@RestController
@RequestMapping("job")
public class JobController {

    @Resource
    private JobService jobService;

    @PostMapping("/list")
    public PageListResp list(QueryRequest request) {
        IPage<Job> page = jobService.list(request);
        return new PageListResp<>(page);
    }

    @PostMapping("/add")
    public void addJob(@RequestBody JobDTO job) {
        jobService.create(job);
    }

    @DeleteMapping("delete/{jobId}")
    public void deleteJob(@PathVariable Long jobId) {
        jobService.delete(jobId);
    }

    @PostMapping("/update")
    public void updateJob(@RequestBody JobDTO job) {
        jobService.update(job);
    }

    @GetMapping("run/{jobId}")
    public void runJob(@PathVariable Long jobId) {
        jobService.run(jobId);
    }

    @GetMapping("pause/{jobId}")
    public void pauseJob(@PathVariable Long jobId) {
        jobService.pause(jobId);
    }

    
    @GetMapping("resume/{jobId}")
    public void resumeJob(@PathVariable Long jobId) {
        jobService.resume(jobId);
    }

    @GetMapping("/addCron/{beanName}/{methodName}")
    public void addCronJob(@PathVariable String beanName,
                           @PathVariable String methodName,
                           @RequestParam(required = false) String remark,
                           @RequestParam String cron) {
        JobDTO dto = new JobDTO();
        dto.setBeanName(beanName);
        dto.setMethodName(methodName);
        dto.setCronExpression(cron);
        dto.setStatus("1");
        dto.setTriggerType(1);
        dto.setRemark(remark);
        jobService.create(dto);
    }

    @GetMapping("/delete/{jobId}")
    public void deleteJobByGet(@PathVariable Long jobId) {
        jobService.delete(jobId);
    }


}
