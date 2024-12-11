package com.event_management.management.service;

import java.util.*;
import java.util.stream.Collectors;

import com.event_management.management.model.Notification;
import com.event_management.management.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public List<Notification> getNotification(String empId) {
        List<Notification> notifications = notificationRepository
                .findByUserId(empId)
                .stream().filter(notification -> !notification.isReadStatus())
                .collect(Collectors.toList());
        return notifications;
    }

    public void markAsRead(String notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new RuntimeException("notification not found"));
        notification.setReadStatus(true);
        notificationRepository.save(notification);
    }

    public void markAllAsRead(String empId) {
        List<Notification> notifications = notificationRepository.findByUserId(empId);
        notifications.forEach(notification -> {
            notification.setReadStatus(true);
        });
        notificationRepository.saveAll(notifications);
    }
}
