package com.event_management.management.repository;
import java.util.*;
import com.event_management.management.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification,String> {

    List<Notification> findByUserId(String userId);
}
