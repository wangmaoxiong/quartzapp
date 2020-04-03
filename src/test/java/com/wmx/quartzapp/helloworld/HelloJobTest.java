package com.wmx.quartzapp.helloworld;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

public class HelloJobTest {
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        //1）读取 classpath 下的 quartz.properties（不存在就都使用默认值）配置来实例化 Scheduler
        //可以在类路径下使用同名文件覆盖 quartz-x.x.x.jar 包下的 org\quartz\quartz.properties 属性文件
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        //2、定义作业的详细信息，并设置要执行的作业的类名。设置作业的名称及其组名.
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("helloJob", "jobGroup").build();
        //3、创建触发器，设置触发器名称与组名称，设置 CronTrigger 触发器的调度规则为每 10 秒触发一次.
        //startNow()：表示立即触发任务，否则需要等待下一个触发点
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("helloTrigger", "triggerGroup")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?")).build();
        //4、将 jobDetail 与 trigger 注册到调度器 scheduler 并启动。
        scheduler.scheduleJob(jobDetail, cronTrigger);
        scheduler.start();

        TimeUnit.MINUTES.sleep(1);//1分钟以后停掉调度器
        scheduler.shutdown();


    }
}
