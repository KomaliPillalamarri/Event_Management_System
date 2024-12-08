package com.event_management.management.repository;

import com.event_management.management.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends MongoRepository<Event,String> {
    List<Event> findByNameContainingIgnoreCase(String name);
    List<Event> findByLocation(String location);
    List<Event> findByDate(LocalDate date);
    List<Event> findByDateBetween(LocalDate startDate,LocalDate endDate);
    List<Event> findByCategory(String category);
}
