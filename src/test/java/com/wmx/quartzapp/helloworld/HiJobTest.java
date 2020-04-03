package com.wmx.quartzapp.helloworld;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author wangmaoxiong
 */
public class HiJobTest {
    public static void main(String[] args) {
        try {
            //1）读取 classpath 下的 quartz.properties（不存在就都使用默认值）配置来实例化 Scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            //2）创建任务详情。设置任务名称与所属组名，同组内的任务名称必须唯一。
            // 为任务添加数据，可以直接 usingJobData，也可以先 jobDetail.getJobDataMap(),然后 put
            JobDetail jobDetail = JobBuilder.newJob(HiJob.class)
                    .withIdentity("hiJob", "hiJobGroup")
                    .usingJobData("url", "https://wangmaoxiong.blog.csdn.net/article/details/105057405")
                    .build();

            //3）设置触发器，设置触发器名称与所属组名，同组内的触发器名称必须唯一。
            // 为触发器添加数据，可以直接 usingJobData，也可以先 jobDetail.getJobDataMap(),然后 put
            // startNow() 表示启动后立即执行
            // withSchedule(ScheduleBuilder<SBT> scheduleBuilder)：设置触发器调度计划，withIntervalInSeconds：间隔多少秒执行
            // repeatForever：表示用于重复。
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("hiTrigger", "hiTriGroup")
                    .startNow()
                    .usingJobData("totalCount", 3)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
                    .build();

            //4）注册任务详情与触发器,然后启动
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
