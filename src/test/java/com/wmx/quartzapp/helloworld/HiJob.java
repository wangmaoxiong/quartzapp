package com.wmx.quartzapp.helloworld;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author wangmaoxiong
 * quartz 任务/作业
 */
public class HiJob implements Job {

    // 如果在 Job 类中为 JobDataMap 中存储数据的 key 增加 setter 方法，
    // 那么 Quartz 的默认 JobFactory 实现会在 job 被实例化的时候自动调用这些 setter 方法进行注值，
    // 这样就不需要在 execute() 方法中显式地从 JobDataMap 中取数据了。
    private String url;
    private Integer totalCount;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //getJobDetail() 返回 JobDetail， getTrigger() 返回 Trigger，然后都可以获取自己的 JobDataMap 进行取值或者设值
        //context.getMergedJobDataMap() 返回的 JobDataMap 包含了 JobDetail 与 getTrigger 设置的所有属性
//        JobDataMap jobDetailDataMap = context.getJobDetail().getJobDataMap();
//        JobDataMap triggerDataMap = context.getTrigger().getJobDataMap();

//        String url = jobDetailDataMap.getString("url");
//        int totalCount = triggerDataMap.getInt("totalCount");
        System.out.println(totalCount + "," + url);
    }
}
