package com.sdx.platform.quartz.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.sdx.platform.quartz.serviceNew.SchedulerService;


@Slf4j
@Component
public class SchedulerStartUpHandler implements ApplicationRunner {

    @Autowired
    private SchedulerService schedulerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Schedule all new scheduler jobs at app startup - starting");
        try {
            schedulerService.startAllSchedulers();
            System.out.println("Schedule all new scheduler jobs at app startup - complete");
        } catch (Exception ex) {
        	ex.printStackTrace();
        	System.err.println("Schedule all new scheduler jobs at app startup - error "+ex.getLocalizedMessage());
        }
    }
}
