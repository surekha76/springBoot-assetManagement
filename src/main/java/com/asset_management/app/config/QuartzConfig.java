package com.asset_management.app.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import job.QuartzJob;

@Configuration
public class QuartzConfig {
	@Autowired
	 private Scheduler scheduler;
	
	@Bean
	 public void scheduleJob1() throws SchedulerException {
		String message = "Hello";
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("message", message);
        JobDetail jobDetail = JobBuilder.newJob()
                .withIdentity("Mesage_job")
                .setJobData(jobDataMap)
                .withDescription("Simple message printing Job.")
                .ofType(QuartzJob.class)
                .storeDurably()
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myCronTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 * ? * *"))
                .build();
        Trigger rescheduleTrigger = TriggerBuilder.newTrigger()
                .withIdentity("myCronTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/5 * ? * *"))
                .build();
        try {
        	scheduler.scheduleJob(jobDetail, trigger);
//        	scheduler.rescheduleJob(trigger.getKey(), rescheduleTrigger);
        }catch(Exception e) {
        	scheduler.rescheduleJob(trigger.getKey(), rescheduleTrigger);
        }
    }
}
