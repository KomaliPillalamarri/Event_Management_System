package com.event_management.management.repository;

import com.event_management.management.model.Attendance;
import com.event_management.management.model.Event;
import com.event_management.management.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AttendanceRepository extends MongoRepository<Attendance,String> {

    List<Attendance> findByUserId(User user);

    List<Attendance> findByEvent(Event event);
}
