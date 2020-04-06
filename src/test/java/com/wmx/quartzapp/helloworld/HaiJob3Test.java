package com.wmx.quartzapp.helloworld;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class HaiJob3Test {
    public static void main(String[] args) {
        try {
            /**1）创建调度器*/
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            /**2）创建任务详情*/
            JobDetail jobDetail = JobBuilder.newJob(HaiJob.class)
                    .withIdentity("haiJob3", "haiJobGroup")
                    .build();

            Trigger trigger = getTrigger4();

            //注册任务详情与触发器，然后启动调度器
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定开始触发时间，不重复，到点就会触发一次：
     *
     * @return
     */
    public static Trigger getTrigger1() {
        /**
         * startAt：触发器开始时间，不设置时，默认 startTime = new Date()，则立即会执行.
         * dateOf(int hour, int minute, int second) 返回 Date 对象，表示今天(new Date())的 hour时minute分second秒开始.
         * withIdentity(TriggerKey triggerKey)：设置触发器的名称以及所属组名称，同一组内的触发器名称必须唯一
         */
        Trigger trigger = TriggerBuilder.newTrigger()
                .startAt(DateBuilder.dateOf(15, 30, 0))
                .withIdentity(TriggerKey.triggerKey("myTrigger", "myTriggerGroup"))
                .build();
        return trigger;
    }

    /**
     * 从指定时间开始触发，然后每隔 10 秒执行一次，重复 10 次：
     *
     * @return
     */
    public static Trigger getTrigger2() {
        /**
         * startAt：触发器开始时间，不设置时，默认 startTime = new Date()，则立即会执行.
         * dateOf(int hour, int minute, int second,int dayOfMonth, int month)
         *      表示 month 月 dayOfMonth 日 hour 点 minute 分 second 秒开始触发
         * withIdentity(TriggerKey triggerKey)：设置触发器的名称以及所属组名称，同一组内的触发器名称必须唯一
         */
        Trigger trigger = TriggerBuilder.newTrigger()
                .startAt(DateBuilder.dateOf(15, 42, 0, 6, 4))
                .withIdentity(TriggerKey.triggerKey("myTrigger", "myTriggerGroup"))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(5)
                        .withRepeatCount(10))
                .build();
        return trigger;
    }

    /**
     * 5分钟以后开始触发，每隔 5 分钟执行一次，直到今天晚上 22:00.
     *
     * @return
     */
    public static Trigger getTrigger3() {
        /**
         * startAt：触发器开始时间，不设置时，默认 startTime = new Date()，则立即会执行.
         * futureDate(int interval, IntervalUnit unit)：表示未来时间，多少时间后，如 5 分钟后，1小时后，1天后，3个月后等
         * withIdentity(TriggerKey triggerKey)：设置触发器的名称以及所属组名称，同一组内的触发器名称必须唯一
         * repeatForever()：方法内部是将循环次数 repeatCount=-1; 即无限循环.直到 endTime 失效时间
         */
        Trigger trigger = TriggerBuilder.newTrigger()
                .startAt(DateBuilder.futureDate(5, DateBuilder.IntervalUnit.MINUTE))
                .withIdentity(TriggerKey.triggerKey("myTrigger", "myTriggerGroup"))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(10)
                        .repeatForever())
                .endAt(DateBuilder.dateOf(22, 0, 0))
                .build();
        return trigger;
    }

    /**
     * 当前时间下一个小时的整点触发，然后每 2 小时重复一次
     */
    public static Trigger getTrigger4() {
        /**
         * startAt：触发器开始时间，不设置时，默认 startTime = new Date()，则立即会执行.
         *      evenHourDateAfterNow：表示当前时间后面最近的整点，如当前时间为 16:20，则返回 17:00
         *      evenSecondDate(Date date)：也可以返回时间后面的整点，date 为 null，默认为 new Date();
         * withIdentity(TriggerKey triggerKey)：设置触发器的名称以及所属组名称，同一组内的触发器名称必须唯一
         * repeatForever()：方法内部是将循环次数 repeatCount=-1; 即无限循环.直到 endTime 失效时间
         */
        Trigger trigger = TriggerBuilder.newTrigger()
                .startAt(DateBuilder.evenHourDateAfterNow())
                .withIdentity(TriggerKey.triggerKey("myTrigger", "myTriggerGroup"))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(2)
                        .repeatForever()
                .withMisfireHandlingInstructionNextWithExistingCount())
                .build();
        return trigger;
    }

}
