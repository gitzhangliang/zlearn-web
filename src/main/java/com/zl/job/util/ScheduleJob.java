package com.zl.job.util;

import com.zl.job.entity.Job;
import com.zl.job.entity.JobLog;
import com.zl.job.service.JobLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * 定时任务
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {


    @Override
    protected void executeInternal(JobExecutionContext context) {
        Job job = (Job) context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY);

        // 获取spring bean
        JobLogService jobLogService = SpringContextUtil.getBean(JobLogService.class);

        JobLog jobLog = new JobLog();
        jobLog.setJobId(job.getId() == null ? job.getJobId() : job.getId());
        jobLog.setCreateTime(new Date());

        long startTime = System.currentTimeMillis();

        try {
            log.info("任务准备执行，任务ID：{} 类名:{} 方法名：{}  参数：{}", job.getId(),job.getBeanName(),job.getMethodName(),job.getParams());
            ScheduleRunnable task = new ScheduleRunnable(job.getBeanName(), job.getMethodName(), job.getParams());
            task.run();
            jobLog.setStatus(true);
        } catch (Exception e) {
            log.error("任务执行失败，任务ID：" + job.getId(), e);
            jobLog.setStatus(false);
            jobLog.setError(StringUtils.substring(e.toString(), 0, 2000));
        } finally {
            long times = System.currentTimeMillis() - startTime;
            log.info("任务执行完毕，任务ID：{} 总共耗时：{} 毫秒", job.getId(), times);
            jobLog.setTimes(times);
            jobLogService.save(jobLog);
        }
    }
}
