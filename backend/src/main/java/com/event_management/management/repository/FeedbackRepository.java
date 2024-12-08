package com.event_management.management.repository;

import com.event_management.management.model.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FeedbackRepository extends MongoRepository<Feedback,String> {

    List<Feedback> findByEventId(String eventId);
}
