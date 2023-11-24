package com.eztornado.tornadocorebase.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = org.quartz.impl.StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        return scheduler;
    }
}