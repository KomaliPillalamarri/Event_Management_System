package com.event_management.management.service;

import com.event_management.management.model.Attendance;
import com.event_management.management.model.Event;
import com.event_management.management.model.User;
import com.event_management.management.repository.AttendanceRepository;
import com.event_management.management.repository.EventRepository;
import com.event_management.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    public Attendance markAttendance(String id, Attendance attendance) {
        try{
            attendance.setTimestamp(LocalDateTime.now());
            User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            attendance.setUserId(user);
            return attendanceRepository.save(attendance);
        }catch (Exception e){
            throw e;
        }
    }

    public List<Attendance> getAttendanceByUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        return attendanceRepository.findByUserId(user);
    }

    public List<Attendance> getAttendanceByEvent(String eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("event not found"));
        return attendanceRepository.findByEvent(event);
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }
}
