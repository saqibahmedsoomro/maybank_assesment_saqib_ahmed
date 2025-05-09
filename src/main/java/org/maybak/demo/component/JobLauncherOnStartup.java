package org.maybak.demo.component;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobLauncherOnStartup implements CommandLineRunner {

    @Autowired
    Job importCustomerJob;

    @Autowired
    JobLauncher jobLauncher;

    @Override
    public void run(String... args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()) // unique run
                .toJobParameters();
        jobLauncher.run(    importCustomerJob, jobParameters);
    }
}
