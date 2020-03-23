package com.wmx.quartzapp.helloworld;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义作业实现 org.quartz.Job 接口，execute 方法中写作业逻辑.
 */
public class HelloJob implements Job {
    private static Logger logger = LoggerFactory.getLogger(HelloJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //获取触发器的名称及其组名称，获取作业详细信息的名称及其组名称.
        String triggerName = context.getTrigger().getKey().getName();
        String triggerGroup = context.getTrigger().getKey().getGroup();
        String jobDetailName = context.getJobDetail().getKey().getName();
        String jobDetailGroup = context.getJobDetail().getKey().getGroup();
        logger.info("执行作业，作业名称={}，作业所属组={}，触发器名称={}，触发器所属组={}",
                jobDetailName, jobDetailGroup, triggerName, triggerGroup);
    }
}
