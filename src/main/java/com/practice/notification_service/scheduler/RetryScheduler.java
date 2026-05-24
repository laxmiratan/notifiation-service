package com.practice.notification_service.scheduler;

import com.practice.notification_service.model.Notification;
import com.practice.notification_service.model.NotificationStatus;
import com.practice.notification_service.repository.NotificationRepository;
import com.practice.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RetryScheduler {

    private final NotificationRepository repository;
    private final NotificationService service;



    @Scheduled(fixedRate = 600000)
    public void processRetries() {
        List<Notification> retryableNotifications = repository
                .findByStatusAndRetryCountLessThanAndNextRetryAtBefore(
                        NotificationStatus.FAILED,
                        3,
                        LocalDateTime.now()
                );

        if (!retryableNotifications.isEmpty()) {
            System.out.println("Found " + retryableNotifications.size() + " notifications to retry.");
            for (Notification notification : retryableNotifications) {
                service.attemptSend(notification);
            }
        }
    }
}
