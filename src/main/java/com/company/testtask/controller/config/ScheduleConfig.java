package com.company.testtask.controller.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class ScheduleConfig {

    @Value("${schedule.variable}")
    private String scheduleVariable;

    @Scheduled(fixedRate = 600000)
    public void scheduledFixedRateTask() {
        LocalDateTime exitVariable = LocalDateTime.parse(scheduleVariable);
        if ((LocalDateTime.now().isAfter(exitVariable))) {
            System.exit(0);
        }
    }
}
