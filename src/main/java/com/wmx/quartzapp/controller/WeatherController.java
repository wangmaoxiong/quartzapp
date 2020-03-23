package com.wmx.quartzapp.controller;

import com.wmx.quartzapp.enums.ResultCode;
import com.wmx.quartzapp.pojo.ResultData;
import com.wmx.quartzapp.scheduler.WeatherRequestJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangmaoxiong
 */
@RestController
public class WeatherController {

    /**
     * 天气信息获取任务当前是否已经在执行
     */
    private static boolean isStartRequestJob = false;

    /**
     * 天气任务调度接口。用于启动天气信息定时查询任务
     * http://localhost:8080/weather/startRequestJob?cityCode=101280601
     * <p>
     * 免费天气预报接口返回15天的天气JSON格式：https://www.cnblogs.com/java888/p/11121987.html
     *
     * @param cityCode 天气查询接口约定的城市编码，如长沙：101250101、香港：101320101、深圳：101280601
     * @return
     */
    @GetMapping("weather/startRequestJob")
    public ResultData startWeatherRequestJob(@RequestParam String cityCode) {
        try {
            if (isStartRequestJob) {
                return new ResultData(3, "任务已经在运行", null);
            }
            //创建一个scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            //创建一个Trigger
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(30)
                            .repeatForever()).build();

            //创建一个job
            JobDetail job = JobBuilder.newJob(WeatherRequestJob.class)
                    .withIdentity("myjob", "mygroup").build();
            job.getJobDataMap().put("cityCode", cityCode);


            //注册trigger并启动scheduler
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
            isStartRequestJob = true;
            ResultData resultData = new ResultData(ResultCode.SUCCESS, null);
            System.out.println("resultData=" + resultData);
            return new ResultData(ResultCode.SUCCESS, null);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return new ResultData(3, "调度失败", null);
    }


    @GetMapping("test")
    public ResultData test() {
        return new ResultData(3, "调度失败", null);
    }
}

