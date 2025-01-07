package com.med.batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobLauncherController {

    private final JobLauncher jobLauncher;
    private final Job job;

    public JobLauncherController(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @GetMapping("/start-job")
    public ResponseEntity<String> startJob(@RequestParam(value = "param", required = false) String param) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("param", param != null ? param : "defaultValue")
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(job, jobParameters);
            return ResponseEntity.ok("Job démarré avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors du démarrage du Job : " + e.getMessage());
        }
    }
}
