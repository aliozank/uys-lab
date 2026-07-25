package com.alikaracor.learning.springsecuritybasicslab.repository;

import com.alikaracor.learning.springsecuritybasicslab.model.UserActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivityLogRepository
        extends JpaRepository<UserActivityLog, Long> {
}