package com.event_management.management.repository;

import com.event_management.management.model.EventCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventCategoryRepository extends MongoRepository<EventCategory,String> {
}
