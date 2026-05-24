package com.practice.notification_service.controller;

import com.practice.notification_service.dto.NotificationRequest;
import com.practice.notification_service.model.Notification;
import com.practice.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/notifications")
public class NotificationController {

    private final NotificationService service;

    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendNotification(@RequestBody NotificationRequest request){
        UUID id = service.processNotification(request);

        return ResponseEntity.ok(Map.of("message", "Notification queued",
                "id", id));
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<Notification> getStatus(@PathVariable UUID id){
        return ResponseEntity.ok(service.getStatus(id));
    }
}
