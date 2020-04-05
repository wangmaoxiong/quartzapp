package com.wmx.quartzapp.helloworld;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HaiJob2Test {
    public static void main(String[] args) {
        try {
            /**1）创建调度器*/
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            /**2）创建任务详情*/
            JobDetail jobDetail = JobBuilder.newJob(HaiJob.class)
                    .withIdentity("haiJob2", "haiJobGroup")
                    .build();
            /**3）创建假日日历 HolidayCalendar，精确到天，表示触发器遇到这一天时，不触发执行任务(放假).
             * HolidayCalendar extends BaseCalendar implements org.quartz.Calendar：假日日历，全天被排除在日程之外，
             *      即调度器遇到这些日期就放假，不执行任务.
             * addExcludedDate(Date excludedDate)：将给定日期(年月日)添加到排除日期列表中，即使设置了时分秒，也会被强制置为 0，存放在 TreeSet<Date> 中.
             * removeExcludedDate(Date dateToRemove)：移除指定假期日历，会从 TreeSet<Date> 中进行移除.
             */
            Date excludedDate1 = new SimpleDateFormat("yyyy-MM-dd").parse("2020-04-04");
            Date excludedDate2 = new SimpleDateFormat("yyyy-MM-dd").parse("2020-04-05");
            HolidayCalendar holidayCalendar = new HolidayCalendar();
            holidayCalendar.removeExcludedDate(null);
            holidayCalendar.addExcludedDate(excludedDate1);
            holidayCalendar.addExcludedDate(excludedDate2);
            /**向调度程序添加（注册）给定的 "日历"
             * scheduler.addCalendar(String calName, Calendar calendar, boolean replace, boolean updateTriggers)
             * calName：假期日历的名称，触发器会根据名称进行引用、calendar：假期日历
             * replace：表示调度器 scheduler 中如果已经存在同名的日历是否替换
             * updateTriggers：是否更新引用了现有日历的现有触发器，以使其基于新触发器是"正确的"
             * scheduler.getCalendar(String calName)：根据名称获取调度器中注册好的日历
             * scheduler.getCalendarNames()：获取调度器中注册的所有日历的名称.
             */
            scheduler.addCalendar("hc1", holidayCalendar, true, true);
            /**
             * 4）创建触发器
             * withIdentity(TriggerKey triggerKey)：设置触发器的名称以及所属组名称，同一组内的触发器名称必须唯一
             * dailyAtHourAndMinute(int hour, int minute)：cron 触发器，在每天的 hour 时 minute 分 0 秒触发.
             * modifiedByCalendar(String calName)：按日历修改触发器，对假期日历 hc1 不触发执行任务。
             */
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(TriggerKey.triggerKey("myTrigger", "myTriggerGroup"))
                    .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(17, 18))
                    .modifiedByCalendar("hc1")
                    .build();

            //注册任务详情与触发器，然后启动调度器
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
