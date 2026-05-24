package com.practice.notification_service.repository;

import com.practice.notification_service.model.Notification;
import com.practice.notification_service.model.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    List<Notification> findByStatusAndRetryCountLessThanAndNextRetryAtBefore(
            NotificationStatus status, int retryCount, LocalDateTime now
    );
}
