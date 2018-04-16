package com.sys.util;

import java.util.Properties;


import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
/**
 * 任务调度服务类
 * @author FENG
 */
public class QuartzUtil {
	public static Scheduler scheduler=null;  
	/**
	* @param jobName,随意指定，多个调度器名称最好不要重复 否则会报一个名称已经存在的错误
	* @param jobGroup随意指定
	* @param triggerName随意指定
	* @param triggerGroup随意指定
	* @param expression 时间表达式
	* @throws SchedulerException
	*/
	public static void add(String jobName,String jobGroup,String triggerName,String triggerGroup,String expression) throws SchedulerException{
		if(scheduler==null){
			StdSchedulerFactory sf = new StdSchedulerFactory();
			Properties props = new Properties();
			props.put("org.quartz.scheduler.instanceName", jobName);
			props.put("org.quartz.threadPool.threadCount", "10");//#必填
			sf.initialize(props);
			scheduler = sf.getScheduler();
		}
		JobDetail job=JobBuilder.newJob(MyJob.class).withIdentity(jobName, jobGroup).build();  
		Trigger trigger=TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroup)  
		.withSchedule(CronScheduleBuilder.cronSchedule(expression))  
		.startNow().build();
		scheduler.scheduleJob(job, trigger);//添加到任务中
		scheduler.start();
	}
}

