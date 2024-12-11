package com.event_management.management.controller;

import com.event_management.management.helpers.ResponseHelper;
import com.event_management.management.model.Notification;
import com.event_management.management.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Object> sendNotification(@RequestBody Notification notification) {
        try{
            Notification data = notificationService.createNotification(notification);
            return ResponseHelper.createResponse(HttpStatus.OK,"Notification created successfully",data,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getNotification(@PathVariable String userId) {
        try{
            List<Notification> data = notificationService.getNotification(userId);
            return ResponseHelper.createResponse(HttpStatus.OK,"Notification retrieved successfully",data,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }

    @GetMapping("/{notificationId}/read")
    public ResponseEntity<Object> markNotificationAsRead(@PathVariable String notificationId) {
        try{
            notificationService.markAsRead(notificationId);
            return ResponseHelper.createResponse(HttpStatus.OK,"Notification read successfully",true,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }

    @GetMapping("/{empId}/emp-read")
    public ResponseEntity<Object> markAllNotificationAsRead(@PathVariable String empId) {
        try{
            notificationService.markAllAsRead(empId);
            return ResponseHelper.createResponse(HttpStatus.OK,"Notification read successfully",true,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }
}
