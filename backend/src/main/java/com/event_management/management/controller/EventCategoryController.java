package com.event_management.management.controller;

import com.event_management.management.helpers.ResponseHelper;
import com.event_management.management.model.Event;
import com.event_management.management.model.EventCategory;
import com.event_management.management.service.EventCategoryService;
import com.event_management.management.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/event-category")
public class EventCategoryController {

    @Autowired
    private EventCategoryService eventCategoryService;

    @PostMapping
    public ResponseEntity<Object> createEventCategory(@RequestBody EventCategory eventCategory){
        try{
            ResponseEntity<?> eventC = eventCategoryService.createEventCategory(eventCategory);
            return ResponseHelper.createResponse(HttpStatus.FOUND,"Event created successfully",true,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllEvents(){
        try{
            List<EventCategory> events = eventCategoryService.getAllEvents();
            return ResponseHelper.createResponse(HttpStatus.OK,"Event retrived successfully",events,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> updateEventCategory(@PathVariable String id,@RequestBody EventCategory event){
        try{
            boolean eventUpdated = eventCategoryService.updateEventCategory(id,event);
            return ResponseHelper.createResponse(HttpStatus.OK,"Event Updated successfully",eventUpdated,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable String id){
        try{
            boolean eventDeleted = eventCategoryService.deleteEventCategory(id);
            return ResponseHelper.createResponse(HttpStatus.OK,"Event Deleted successfully",eventDeleted,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }
}
