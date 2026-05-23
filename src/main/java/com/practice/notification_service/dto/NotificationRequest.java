package com.practice.notification_service.dto;

import com.practice.notification_service.model.NotificationType;

import java.util.Map;

public class NotificationRequest {
    private NotificationType type;
    private  String recipientId;
    private String massagePayload;
    private Map<String, String> metadata;
}
