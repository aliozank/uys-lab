package com.alikaracor.learning.springsecuritybasicslab.service;

import com.alikaracor.learning.springsecuritybasicslab.model.ActivityType;
import com.alikaracor.learning.springsecuritybasicslab.model.UserActivityLog;
import com.alikaracor.learning.springsecuritybasicslab.repository.UserActivityLogRepository;
import org.springframework.stereotype.Service;

@Service
public class ActivityLogService {

    private final UserActivityLogRepository activityLogRepository;

    public ActivityLogService(
            UserActivityLogRepository activityLogRepository
    ) {
        this.activityLogRepository = activityLogRepository;
    }

    public void log(
            String username,
            ActivityType activityType
    ) {
        UserActivityLog activityLog =
                new UserActivityLog(
                        username,
                        activityType
                );

        activityLogRepository.save(activityLog);
    }
}