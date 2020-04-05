package com.wmx.quartzapp.helloworld;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HaiJobTest {
    public static void main(String[] args) {

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            JobDetail jobDetail = JobBuilder.newJob(HaiJob.class)
                    .withIdentity("job1","group1")
                    .build();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = dateFormat.parse("2020-04-04 17:28:00");
            Date endTime = dateFormat.parse("2020-04-04 18:30:30");

            //设置触发器的启动时间为 startTime，失效时间为 endTime。
            //一到启动时间 SimpleTrigger 就会第一次执行任务，然后间隔 withIntervalInSeconds 继续执行
            //直到失效时间不再执行，触发器也会被卸载.
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withPriority(1)    //设置触发器执行优先级值越小优先级越高，默认为 5
                    .startAt(startTime)
                    .endAt(endTime)
                    .withIdentity(TriggerKey.triggerKey("myTrigger", "myTriggerGroup"))
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(10)
                            .repeatForever())
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
