package com.turkcell.bipai.saacweather.job;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;



public class WeatherConditionScheduler  {
		
	public static void Schedule() throws Exception {

		
    	JobDetail job = JobBuilder.newJob(WeatherJob.class)
		.withIdentity("sendWeatherCondition", "group1").build();


    	Trigger trigger = TriggerBuilder
		.newTrigger()
		.withIdentity("triggerCondition", "group1")
		.withSchedule(
			CronScheduleBuilder.cronSchedule("0 0 8 * * ?"))
		.build();

    	//schedule it
    	Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    	scheduler.start();
    	scheduler.scheduleJob(job, trigger);
//		

	}
	
}