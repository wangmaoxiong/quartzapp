package com.wmx.quartzapp.helloworld;

import org.quartz.*;

/**
 * @author wangmaoxiong
 * quartz 任务/作业
 * @DisallowConcurrentExecution： 不要并发地执行 Job 实现的同一个实例(JobDetail)
 * @PersistJobDataAfterExecution: 表示调度器在成功执行了 job 类的 execute 方法后（没有发生任何异常），
 * 更新 JobDetail 中 JobDataMap 的数据，使得该 JobDetail 在下一次执行的时候JobDataMap 中是更新后的数据，而不是更新前的旧数据
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
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
        try {
            //getJobDetail() 返回 JobDetail， getTrigger() 返回 Trigger，然后都可以获取自己的 JobDataMap 进行取值或者设值
            //context.getMergedJobDataMap() 返回的 JobDataMap 包含了 JobDetail 与 getTrigger 设置的所有属性
//        JobDataMap jobDetailDataMap = context.getJobDetail().getJobDataMap();
//        JobDataMap triggerDataMap = context.getTrigger().getJobDataMap();

//        String url = jobDetailDataMap.getString("url");
//        int totalCount = triggerDataMap.getInt("totalCount");
            for (int i = 0; i < 15; i++) {
                System.out.println("[" + Thread.currentThread().getName() + "]" + i);
                Thread.sleep(500);
            }
            System.out.println(totalCount + "," + url);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
