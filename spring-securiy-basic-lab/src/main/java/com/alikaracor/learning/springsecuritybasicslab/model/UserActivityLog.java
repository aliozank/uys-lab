package com.alikaracor.learning.springsecuritybasicslab.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_activity_logs")
public class UserActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            length = 50
    )
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "activity_type",
            nullable = false,
            length = 50
    )
    private ActivityType activityType;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    protected UserActivityLog() {
    }

    public UserActivityLog(
            String username,
            ActivityType activityType
    ) {
        this.username = username;
        this.activityType = activityType;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}