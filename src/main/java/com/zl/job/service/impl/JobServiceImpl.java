package com.zl.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.job.dao.JobMapper;
import com.zl.job.entity.Job;
import com.zl.job.enumerations.ScheduleStatus;
import com.zl.job.enumerations.TriggerTypeEnum;
import com.zl.job.exception.JobException;
import com.zl.job.model.JobDTO;
import com.zl.job.model.PageAndSort;
import com.zl.job.model.QueryRequest;
import com.zl.job.service.JobService;
import com.zl.job.util.ScheduleUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.quartz.Scheduler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zl
 */
@Slf4j
@Service("JobService")
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {

    @Resource
    private Scheduler scheduler;


    @Override
    public Job get(Long jobId) {
        return this.getById(jobId);
    }

    @Override
    public IPage<Job> list(QueryRequest request) {
        LambdaQueryWrapper<Job> queryWrapper = new LambdaQueryWrapper<>();
        Page<Job> page = PageAndSort.constructPage(request,"create_time",PageAndSort.DESC);
        return this.page(page, queryWrapper);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(JobDTO dto) {
        Job job = new Job();
        BeanUtils.copyProperties(dto,job);
        if(TriggerTypeEnum.CRON.getValue().equals(job.getTriggerType())){
            if (!CronExpression.isValidExpression(job.getCronExpression())) {
                throw new JobException("cron表达式错误");
            }
            job.setJobGroup(TriggerTypeEnum.CRON.getGroup());
            this.save(job);
            ScheduleUtils.createScheduleCronJob(scheduler, job);
        }else if(TriggerTypeEnum.SIMPLE.getValue().equals(job.getTriggerType())){
            job.setJobGroup(TriggerTypeEnum.SIMPLE.getGroup());
            this.save(job);
            ScheduleUtils.createScheduleSimpleJob(scheduler, job);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(JobDTO dto) {
        Job job = get(dto.getId());
        BeanUtils.copyProperties(dto,job);
        if(TriggerTypeEnum.CRON.getValue().equals(job.getTriggerType())){
            if (!CronExpression.isValidExpression(job.getCronExpression())) {
                throw new JobException("cron表达式错误");
            }
            job.setJobGroup(TriggerTypeEnum.CRON.getGroup());
            this.baseMapper.updateById(job);
            ScheduleUtils.updateScheduleCronJob(scheduler, job);
        }else if(TriggerTypeEnum.SIMPLE.getValue().equals(job.getTriggerType())){
            job.setJobGroup(TriggerTypeEnum.SIMPLE.getGroup());
            this.baseMapper.updateById(job);
            ScheduleUtils.updateScheduleSimpleJob(scheduler, job);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long jobId) {
        Job job = get(jobId);
        ScheduleUtils.deleteScheduleJob(scheduler, job);
        this.baseMapper.deleteById(jobId);
    }


    @Override
    public void run(Long jobId) {
        Job job = get(jobId);
        ScheduleUtils.run(scheduler, job);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pause(Long jobId) {
        Job job = get(jobId);
        job.setStatus(ScheduleStatus.PAUSE.getValue());
        ScheduleUtils.pauseJob(scheduler, job);
        this.updateById(job);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resume(Long jobId) {
        Job job = get(jobId);
        job.setStatus(ScheduleStatus.NORMAL.getValue());
        ScheduleUtils.resumeJob(scheduler, job);
        this.updateById(job);
    }
}
