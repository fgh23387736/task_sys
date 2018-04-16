package com.sys.util;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
/**
 * 任务类
 * @author FENG
 */
public class MyJob implements Job{
	//要执行的任务写里面
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			System.out.println("SchedulerName:"+context.getScheduler().getSchedulerName());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		System.out.println("任务1");
	}
}