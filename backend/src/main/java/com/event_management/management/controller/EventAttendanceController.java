package com.event_management.management.controller;


import com.event_management.management.helpers.ResponseHelper;
import com.event_management.management.model.Attendance;
import com.event_management.management.model.User;
import com.event_management.management.repository.UserRepository;
import com.event_management.management.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class EventAttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/mark/{id}")
    public ResponseEntity<Object> markAttendance(@PathVariable String id, @RequestBody Attendance attendance){
        try{
            Attendance data =  attendanceService.markAttendance(id,attendance);
            return ResponseHelper.createResponse(HttpStatus.OK,"Event retrived successfully",data,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<Object> getAttendanceByEvent(@PathVariable String eventId) {
        try{
            List<Attendance> data =  attendanceService.getAttendanceByEvent(eventId);
            return ResponseHelper.createResponse(HttpStatus.OK,"Attendance retrieved successfully",data,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> getAttendanceByUserId(@PathVariable String userId) {
        try{
            List<Attendance> data =  attendanceService.getAttendanceByUser(userId);
            return ResponseHelper.createResponse(HttpStatus.OK,"Attendance retrieved successfully",data,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }


    @GetMapping("/all")
    public ResponseEntity<Object> getAllAttendance() {
        try{
            List<Attendance> data =  attendanceService.getAllAttendance();
            return ResponseHelper.createResponse(HttpStatus.OK,"Attendance retrieved successfully",data,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }
}
