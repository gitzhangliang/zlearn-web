package com.zl.job.util;

import com.zl.job.entity.Job;
import com.zl.job.enumerations.ScheduleStatus;
import com.zl.job.exception.JobException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

/**
 * 定时任务工具类
 * @author zl
 */
@Slf4j
public class ScheduleUtils {

    private ScheduleUtils() {}

    private static final String JOB_NAME_PREFIX = "TASK_";


    /**
     * 任务调度参数 key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    /**
     * 获取触发器key
     */
    private static TriggerKey getTriggerKey(Job job) {
        return TriggerKey.triggerKey(JOB_NAME_PREFIX + job.getId(),job.getJobGroup());
    }

    /**
     * 获取jobKey
     */
    private static JobKey getJobKey(Job job) {
        return JobKey.jobKey(JOB_NAME_PREFIX + job.getId(),job.getJobGroup());
    }

    /**
     * 获取触发器
     */
    private static Trigger getTrigger(Scheduler scheduler, Job job) {
        try {
            return scheduler.getTrigger(getTriggerKey(job));
        } catch (SchedulerException e) {
            log.error("获取触发器Trigger失败:{0}", e);
            throw new JobException("获取触发器Trigger失败");
        }
    }

    /**
     * 创建Cron定时任务
     */
    public static void createScheduleCronJob(Scheduler scheduler, Job job) {
        // 表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression())
                .withMisfireHandlingInstructionDoNothing();

        // 按新的cronExpression表达式构建一个新的trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(getTriggerKey(job))
                .withSchedule(scheduleBuilder)
                .build();
        createScheduleJob(scheduler,trigger,job);
    }

    /**
     * 创建Simple定时任务
     */
    public static void createScheduleSimpleJob(Scheduler scheduler, Job job) {
        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule()
                .withRepeatCount(job.getRepeatCount().intValue())
                .withIntervalInMilliseconds(job.getRepeatInterval());
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(getTriggerKey(job))
                .startAt(job.getStartTime())
                .withSchedule(builder)
                .build();
        createScheduleJob(scheduler,trigger,job);
    }

    /**
     * 创建定时任务
     */
    private static void createScheduleJob(Scheduler scheduler,Trigger trigger,Job job) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(job)).build();
            job.setJobId(job.getId());
            // 放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(JOB_PARAM_KEY, job);
            scheduler.scheduleJob(jobDetail, trigger);
            // 暂停任务
            if (job.getStatus().equals(ScheduleStatus.PAUSE.getValue())) {
                pauseJob(scheduler, job);
            }
        } catch (SchedulerException e) {
            log.error("创建定时任务失败:{0}", e);
            throw new JobException("创建定时任务失败");
        }
    }

    /**
     * 更新Cron定时任务
     */
    public static void updateScheduleCronJob(Scheduler scheduler, Job job) {
        try {
            TriggerKey triggerKey = getTriggerKey(job);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();
            CronTrigger trigger = ( CronTrigger ) getTrigger(scheduler, job);
            if (trigger == null) {
                throw new JobException("获取触发器失败");
            } else {
                // 按新的cronExpression表达式重新构建trigger
                trigger = trigger.getTriggerBuilder()
                        .withIdentity(triggerKey)
                        .withSchedule(scheduleBuilder)
                        .build();
                // 参数
                trigger.getJobDataMap().put(JOB_PARAM_KEY, job);
            }
            scheduler.rescheduleJob(triggerKey, trigger);
            // 暂停任务
            if (job.getStatus().equals(ScheduleStatus.PAUSE.getValue())) {
                pauseJob(scheduler, job);
            }
        }catch (SchedulerException e){
            log.error("更新Cron定时任务失败:{0}",e);
            throw new JobException("更新Cron定时任务失败");
        }

    }

    /**
     * 更新Simple定时任务
     */
    public static void updateScheduleSimpleJob(Scheduler scheduler, Job job) {
        try {
            TriggerKey triggerKey = getTriggerKey(job);
            // 表达式调度构建器
            SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule()
                    .withRepeatCount(job.getRepeatCount().intValue())
                    .withIntervalInMilliseconds(job.getRepeatInterval());
            SimpleTrigger trigger = ( SimpleTrigger ) getTrigger(scheduler, job);
            if (trigger == null) {
                throw new JobException("获取触发器失败");
            } else {
                trigger = trigger.getTriggerBuilder()
                        .withIdentity(getTriggerKey(job))
                        .startAt(job.getStartTime())
                        .withSchedule(builder)
                        .build();
                // 参数
                trigger.getJobDataMap().put(JOB_PARAM_KEY, job);
            }
            scheduler.rescheduleJob(triggerKey, trigger);
            // 暂停任务
            if (job.getStatus().equals(ScheduleStatus.PAUSE.getValue())) {
                pauseJob(scheduler, job);
            }
        }catch (SchedulerException e){
            log.error("更新Simple定时任务失败:{0}",e);
            throw new JobException("更新Simple定时任务失败");
        }
    }



    /**
     * 立即执行任务
     */
    public static void run(Scheduler scheduler, Job job) {
        try {
            // 参数
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(JOB_PARAM_KEY, job);
            scheduler.triggerJob(getJobKey(job), dataMap);
        } catch (SchedulerException e) {
            log.error("执行定时任务失败:{0}", e);
            throw new JobException("执行定时任务失败");
        }
    }

    /**
     * 暂停任务
     */
    public static void pauseJob(Scheduler scheduler, Job job) {
        try {
            scheduler.pauseJob(getJobKey(job));
        } catch (SchedulerException e) {
            log.error("暂停定时任务失败:{0}", e);
            throw new JobException("暂停定时任务失败");
        }
    }

    /**
     * 恢复任务
     */
    public static void resumeJob(Scheduler scheduler, Job job) {
        try {
            scheduler.resumeJob(getJobKey(job));
        } catch (SchedulerException e) {
            log.error("恢复定时任务失败:{0}", e);
            throw new JobException("恢复定时任务失败");
        }
    }

    /**
     * 删除定时任务
     */
    public static void deleteScheduleJob(Scheduler scheduler, Job job) {
        try {
            scheduler.deleteJob(getJobKey(job));
        } catch (SchedulerException e) {
            log.error("删除定时任务失败:{0}", e);
            throw new JobException("删除定时任务失败");
        }
    }
}
