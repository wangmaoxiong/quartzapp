package com.wmx.quartzapp.scheduler;

import com.wmx.quartzapp.factory.BeanFactoryHelper;
import org.apache.commons.io.FileUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;

/**
 * @author wangmaoxiong
 * 自定义请求城市天气信息任务。实现 org.quartz.Job 接口，execute 方法中写任务逻辑.
 */
public class WeatherRequestJob implements Job {
    private static Logger logger = LoggerFactory.getLogger(WeatherRequestJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            //获取 JobDetail 通过 JobDataMap 设置的数据.
            Object cityCode = context.getJobDetail().getJobDataMap().get("cityCode");
            //免费天气预报接口返回15天的天气JSON格式：https://www.cnblogs.com/java888/p/11121987.html
            URL url = new URL("http://t.weather.sojson.com/api/weather/city/" + cityCode);
            //通过 BeanFactoryAware 获取 spring 容器中的 bean
            RestTemplate restTemplate = BeanFactoryHelper.getBean(RestTemplate.class);
            //使用 RestTemplate 发送 http 请求
            String forObject = restTemplate.getForObject(url.toURI(), String.class);
            //将请求结果保存到桌面，仅仅只是演示，实际中应该存入库中.
            File homeDirectory = FileSystemView.getFileSystemView().getHomeDirectory();
            homeDirectory = new File(homeDirectory, "weather");
            File dataFile = new File(homeDirectory, LocalDateTime.now().toString().replace(":", "") + ".json");
            FileUtils.write(dataFile, forObject, "utf-8");
            logger.info("天气数据保存在：" + dataFile.getAbsolutePath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
