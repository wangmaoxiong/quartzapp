package com.wmx.quartzapp.helloworld;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * CronTrigger 学习
 *
 * @author wangmaoxiong
 */
public class CronTriggerTest {
    public static void main(String[] args) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();//创建调度器
            JobDetail jobDetail = JobBuilder.newJob(HaiJob.class)
                    .withIdentity("haiJob3", "haiJobGroup")
                    .build();//创建任务详情

            Trigger trigger = getTrigger3();

            scheduler.scheduleJob(jobDetail, trigger);//注册任务详情与触发器
            scheduler.start();//启动调度器
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 每天上午 8 点至下午 5 点之间，每隔一分钟触发一次
     *
     * @return
     */
    public static Trigger getTrigger1() {
        //每天上午 8 点至下午 5 点之间，每隔一分钟触发一次，默认启动时不会执行，启动1分钟后开始执行第一次
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(TriggerKey.triggerKey("myTrigger", "myTriggerGroup"))
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 8-17 * * ?"))
                .build();
        return trigger;
    }

    /**
     * 每周一、三上午9:30执行一次
     *
     * @return
     */
    public static Trigger getTrigger2() {
        //每周一、三上午9:30执行一次
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(TriggerKey.triggerKey("myTrigger", "myTriggerGroup"))
                .withSchedule(CronScheduleBuilder.cronSchedule("0 30 9 ? * MON,WED"))
                .build();
        return trigger;
    }

    /**
     * 每月1号晚上 23:30 执行一次
     *
     * @return
     */
    public static Trigger getTrigger3() {
        //每月1号晚上 23:30 执行一次。
        //monthlyOnDayAndHourAndMinute(int dayOfMonth, int hour, int minute) :表示每月 dayOfMonth 号 hour 点 minute 分 0 秒执行一次.
        //等价于 cron 表达式：String cronExpression = String.format("0 %d %d %d * ?", minute, hour,dayOfMonth);
        //使用过期策略：CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(TriggerKey.triggerKey("myTrigger", "myTriggerGroup"))
                .withSchedule(CronScheduleBuilder.monthlyOnDayAndHourAndMinute(6, 21, 44)
                        .withMisfireHandlingInstructionFireAndProceed())
                .build();
        return trigger;
    }


}
