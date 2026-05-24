package com.practice.notification_service.service;


import com.practice.notification_service.dto.NotificationRequest;
import com.practice.notification_service.model.Notification;
import com.practice.notification_service.model.NotificationStatus;
import com.practice.notification_service.model.NotificationType;
import com.practice.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;
    private final NotificationFactory factory;

    public UUID processNotification(NotificationRequest request){
        Notification notification = new Notification();
        notification.setRecipientId(request.getRecipientId());
        notification.setMessagePayload(request.getMessagePayload());
        notification.setType(request.getType());
        notification.setStatus(NotificationStatus.QUEUED);
        notification = repository.save(notification);

        attemptSend(notification);

        return notification.getId();
    }

    public void attemptSend(Notification notification){

        NotificationSender sender = factory.getSender(notification.getType());
        //NotificationStatus oldStatus = notification.getStatus();

        try{
            boolean success = sender.send(notification);

            if(success){
                notification.setStatus(NotificationStatus.SENT);
            }else{
                handleFailure(notification);
            }
        } catch (Exception e) {
            handleFailure(notification);
        }

        repository.save(notification);

    }

    private void handleFailure(Notification notification) {

        notification.setRetryCount(notification.getRetryCount()+1);

        if(notification.getRetryCount() >= notification.getMaxRetry()){
            notification.setStatus(NotificationStatus.DEAD_LETTER);
        }else {
            notification.setStatus(NotificationStatus.FAILED);
            long backOffMin= (long) Math.pow(2, notification.getRetryCount());
            notification.setNextRetryAt(LocalDateTime.now().plusMinutes(backOffMin));
        }

    }

    public Notification getStatus(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
    }
}
