package com.practice.notification_service.service;

import com.practice.notification_service.model.Notification;
import com.practice.notification_service.model.NotificationType;
import org.springframework.stereotype.Service;

@Service
public class PushSender implements NotificationSender{

    @Override
    public boolean send(Notification notification) {
        System.out.println("sending the notification to :" + notification.getRecipientId());
        return true;
    }

    @Override
    public boolean supports(NotificationType type) {
        return type == NotificationType.PUSH;
    }
}
