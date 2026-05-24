package com.practice.notification_service.service;

import com.practice.notification_service.model.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationFactory {

    private final List<NotificationSender> sender;

    public NotificationSender getSender(NotificationType type){
        return sender.stream()
                .filter(i -> i.supports(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported notification type: " + type));
    }

}
