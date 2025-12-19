package net.jdgould.spring_garden.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Component
public class TimeService {

    @Scheduled(fixedRate=50000)
    public void reportCurrentTime() {
        System.out.println("The time is: "  + System.currentTimeMillis());
    }
}
