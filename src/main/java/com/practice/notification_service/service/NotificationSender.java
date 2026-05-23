package com.practice.notification_service.service;

import com.practice.notification_service.model.Notification;
import com.practice.notification_service.model.NotificationType;

public interface NotificationSender {
    boolean send(Notification notification);
    boolean supports(NotificationType type);
}
