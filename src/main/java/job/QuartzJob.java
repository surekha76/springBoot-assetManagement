package job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzJob implements Job{

	@Override
	 public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String message = jobDataMap.get("message").toString();
        System.out.println("Scheduler is printing message:: {} "+message);
        System.out.println(new Date());
    }

}
