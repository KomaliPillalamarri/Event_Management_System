package com.event_management.management.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "feedback")
public class Feedback {

    @Id
    private String feedbackId;
    private String eventId;
    private String userId;
    private int rating;
    private String comments;
    private String dateSubmitted;

    public Feedback() {}

    public Feedback(String feedbackId, String eventId, String userId, int rating, String comments, String dateSubmitted) {
        this.feedbackId = feedbackId;
        this.eventId = eventId;
        this.userId = userId;
        this.rating = rating;
        this.comments = comments;
        this.dateSubmitted = dateSubmitted;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(String dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }
}
