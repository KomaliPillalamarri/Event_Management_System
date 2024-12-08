package com.event_management.management.model;

public enum EventCategoryName {
    CONFERENCES("Conferences"),
    WORKSHOPS("Workshops"),
    WEBINARS("Webinars"),
    SOCIAL_GATHERINGS("Social Gatherings");

    private final String name;

    EventCategoryName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}

