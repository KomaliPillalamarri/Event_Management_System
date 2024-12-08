package com.event_management.management.repository;

import com.event_management.management.model.EventRegistration;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventRegistrationRepository extends MongoRepository<EventRegistration,String> {

    List<EventRegistration> findByUserId(String userId);

    List<EventRegistration> findByEventId(String eventId);
}
