package com.project.SpringBatch.Controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @PostMapping("/import")
    public String jobLauncher(){

        final JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();

        try{

            //Lauch the job
            final JobExecution jobExecution = jobLauncher.run(job, jobParameters);

            //Return job status
            return jobExecution.getStatus().toString();
        }catch (Exception e){
            e.printStackTrace();
            return "Job failed "+e.getMessage();
        }

    }
}
