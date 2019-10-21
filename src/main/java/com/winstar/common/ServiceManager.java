package com.winstar.common;

import com.winstar.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceManager {

    public static ActivityRepository activityRepository;

    @Autowired
    public static void setActivityRepository(ActivityRepository activityRepository) {
        ServiceManager.activityRepository = activityRepository;
    }
}
