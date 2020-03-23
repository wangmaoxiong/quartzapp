package com.wmx.quartzapp.controller;

import com.wmx.quartzapp.enums.ResultCode;
import com.wmx.quartzapp.pojo.ResultData;
import com.wmx.quartzapp.scheduler.WeatherRequestJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangmaoxiong
 */
@RestController
public class WeatherController {

    private static Logger logger = LoggerFactory.getLogger(WeatherController.class);
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
        //返回给页面的数据
        ResultData resultData = null;
        try {
            if (isStartRequestJob) {
                return new ResultData(202, "任务已经在运行", null);
            }
            //1、读取 classpath 下的 quartz.properties（不存在就都使用默认值）配置来实例化 Scheduler
            //可以在类路径下使用同名文件覆盖 quartz-x.x.x.jar 包下的 org\quartz\quartz.properties 属性文件
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            //2、定义作业的详细信息，并设置要执行的作业的类名。设置作业的名称及其组名.
            JobDetail jobDetail = JobBuilder.newJob(WeatherRequestJob.class)
                    .withIdentity("job_weather_1", "job_group_1").build();
            //向作业设置数据，这样在 job 中便可以获取
            jobDetail.getJobDataMap().put("cityCode", cityCode);

            //3、创建触发器，设置触发器名称与组名称，设置触发器的调度规则为每30秒触发一次，永远重复(repeatForever)
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger_weather_1", "trigger_group_1")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(30)
                            .repeatForever()).build();

            //4、将 jobDetail 与 trigger 注册到调度器 scheduler 并启动。
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();

            isStartRequestJob = true;
            resultData = new ResultData(ResultCode.SUCCESS, null);
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
            resultData = new ResultData(202, e.getMessage(), null);
        }
        return resultData;
    }
}

