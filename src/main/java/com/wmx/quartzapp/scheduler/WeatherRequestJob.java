package com.wmx.quartzapp.scheduler;

import com.wmx.quartzapp.factory.BeanFactoryHelper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author wangmaoxiong
 * 请求城市天气信息任务
 */
public class WeatherRequestJob implements Job {
    private static Logger logger = LoggerFactory.getLogger(WeatherRequestJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            Object cityCode = context.getJobDetail().getJobDataMap().get("cityCode");
            //免费天气预报接口返回15天的天气JSON格式：https://www.cnblogs.com/java888/p/11121987.html
            URL url = new URL("http://t.weather.sojson.com/api/weather/city/" + cityCode);
            //通过 BeanFactoryAware 获取 spring 容器中的 bean
            RestTemplate restTemplate = BeanFactoryHelper.getBean(RestTemplate.class);
            //使用 RestTemplate 发送 http 请求
            String forObject = restTemplate.getForObject(url.toURI(), String.class);
            logger.info(forObject);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
