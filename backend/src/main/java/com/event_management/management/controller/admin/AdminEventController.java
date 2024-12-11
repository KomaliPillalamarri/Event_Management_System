package com.event_management.management.controller.admin;

import com.event_management.management.helpers.ResponseHelper;
import com.event_management.management.model.Event;
import com.event_management.management.model.EventRegistration;
import com.event_management.management.service.EventRegistrationService;
import com.event_management.management.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/admin/events")
//@CrossOrigin("*")
public class AdminEventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private EventRegistrationService eventRegistrationService;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllEvents(){
        try{
            List<Event> events = eventService.getAllEvents();
            return ResponseHelper.createResponse(HttpStatus.OK,"Event retrived successfully",events,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }

    @GetMapping("/all/registrations")
    public ResponseEntity<Object> getAllRegistrations(){
        try{
            List<EventRegistration> registrations = eventRegistrationService.getAllRegistrations();
            return ResponseHelper.createResponse(HttpStatus.OK,"Registered events retrived successfully",registrations,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }

    @GetMapping("/{eventId}/get")
    public ResponseEntity<Object> getEventById(@PathVariable("eventId") String eventId){
        try{
            Event events = eventService.getEventById(eventId);
            return ResponseHelper.createResponse(HttpStatus.OK,"Event retrived successfully",events,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }

    @PostMapping("/{categoryId}/create")
    public ResponseEntity<Object> createEvent(@Valid @RequestBody Event event, @PathVariable String categoryId){
        try{
            System.out.println(event.toString());
            Event res = eventService.createEvent(event,categoryId);
            System.out.println(res.toString());
            return ResponseHelper.createResponse(HttpStatus.CREATED,"Event created successfully",res,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }

    @PatchMapping("/{id}/{categoryId}/update")
    public ResponseEntity<Object> updateEvent(@PathVariable String id,@PathVariable String categoryId,@RequestBody Event event){
        try{
            Event res = eventService.updateEvent(id,categoryId,event);
            return ResponseHelper.createResponse(HttpStatus.OK,"Event updated successfully",res,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable String id){
        try{
            boolean res = eventService.deleteEvent(id);
            return ResponseHelper.createResponse(HttpStatus.OK,"Event deleted successfully",res,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }
}
