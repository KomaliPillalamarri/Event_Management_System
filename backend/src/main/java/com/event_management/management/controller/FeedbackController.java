package com.event_management.management.controller;

import com.event_management.management.helpers.ResponseHelper;
import com.event_management.management.model.Feedback;
import com.event_management.management.model.Notification;
import com.event_management.management.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/submit")
    public ResponseEntity<Object> submitFeedback(@RequestBody Feedback feedback) {
        try{
            Feedback data = feedbackService.submitFeedback(feedback);
            return ResponseHelper.createResponse(HttpStatus.OK,"Feedback submitted successfully",data,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }

    @PostMapping("/event/{eventId}")
    public ResponseEntity<Object> getFeedBackEvent(@RequestBody String eventId) {
        try{
            List<Feedback> data = feedbackService.getFeedbackForEvent(eventId);
            return ResponseHelper.createResponse(HttpStatus.OK,"Feedback submitted successfully",data,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }
}
