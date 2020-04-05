package com.wmx.quartzapp.helloworld;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HaiJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(HaiJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
