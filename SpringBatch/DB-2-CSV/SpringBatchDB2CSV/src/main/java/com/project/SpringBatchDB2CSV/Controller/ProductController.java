package com.project.SpringBatchDB2CSV.Controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class ProductController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @GetMapping("/export")
    public String exportData(){

        final JobParameters jobParameters = new JobParametersBuilder()
                .addLong("StartedAt", System.currentTimeMillis()).toJobParameters();

        try {
            final JobExecution launcher = jobLauncher.run(job, jobParameters);
            return launcher.getStatus().toString();
        } catch (Exception e){
            return "Export job failed "+ e.getMessage();
        }
    }
}
