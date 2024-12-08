package com.event_management.management.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "events")
public class Event {
    @Id
    private String eventId;
    private String name;
    private String description;
    private LocalDate date;
    private String location;
    private int maxRegistrations;
    private LocalDate deadline;
    private String status;
    private String createdBy;
    private String visibility;

    private List<String> registeredUserIds = new ArrayList<>();

    @DBRef
    private EventCategory category;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMaxRegistrations() {
        return maxRegistrations;
    }

    public void setMaxRegistrations(int maxRegistrations) {
        this.maxRegistrations = maxRegistrations;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public EventCategory getCategory(){
        return category;
    }

    public void setCategory(EventCategory category){
        this.category = category;
    }

    public List<String> getRegisteredUserIds() {
        return registeredUserIds;
    }

    public void setRegisteredUserIds(String registeredUserIds) {
        this.registeredUserIds.add(registeredUserIds);
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", location='" + location + '\'' +
                ", maxRegistrations=" + maxRegistrations +
                ", deadline=" + deadline +
                ", status='" + status + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", registeredUserIds=" + registeredUserIds +
                ", category=" + category +
                '}';
    }
}
