package com.event_management.management.controller;

import com.event_management.management.dto.RegistrationRequestDto;
import com.event_management.management.helpers.ResponseHelper;
import com.event_management.management.model.Event;
import com.event_management.management.model.EventRegistration;
import com.event_management.management.service.CsvExportService;
import com.event_management.management.service.EventRegistrationService;
import com.event_management.management.service.EventService;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/app/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRegistrationService eventRegistrationService;

    @Autowired
    private CsvExportService csvExportService;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllEvents(){
        try{
            List<Event> events = eventService.getAllEvents();
            return ResponseHelper.createResponse(HttpStatus.OK,"Event retrived successfully",events,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }



    @GetMapping
    public ResponseEntity<Object> searchEvents(@RequestParam(value = "search",required = false) String search){
        try{
            List<Event> events = eventService.searchEvents(search);
            return ResponseHelper.createResponse(HttpStatus.OK,"Event retrived successfully",events,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> eventRegistration(@RequestBody RegistrationRequestDto body){
        try{
            eventRegistrationService.registerEvent(body);
            return ResponseHelper.createResponse(HttpStatus.OK,"Event registered successfully",true,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Object> ExportEventRegistrations(@PathVariable String id,HttpServletResponse res) throws IOException{
        try{
            csvExportService.exportEventsToCsv(res);
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,null,false,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }

    @GetMapping("/registrations/{userId}")
    public ResponseEntity<Object> getUserRegistrations(@PathVariable String userId){
        try{
            List<EventRegistration> registrations = eventRegistrationService.getUserRegistrations(userId);
            return ResponseHelper.createResponse(HttpStatus.OK,"Event registered successfully",registrations,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }

    @GetMapping("/{eventId}/attendees/download")
    public ResponseEntity<?> downloadAttendeeList(@PathVariable String eventId, HttpServletResponse response) throws IOException {
        try{
            Event event = eventService.getEventById(eventId);
            if(event == null){
                return ResponseHelper.createErrorResponse(HttpStatus.NOT_FOUND,"Event not found",false,null);
            }

            List<EventRegistration> attendees = eventService.getAttendees(eventId);
            response.setContentType("text/csv");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"attendees.csv\"");

            try(CSVWriter writer = new CSVWriter(response.getWriter())){
                writer.writeNext(new String[]{"username","Email","Event Name"});

                for(EventRegistration attendee : attendees){
                    writer.writeNext(new String[]{
                        attendee.getUserId().getUsername(),
                    attendee.getUserId().getEmail(),
                    attendee.getEventId().getName(),
                    });
                }
            }

            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }

    @GetMapping("/{eventId}/attendees")
    public ResponseEntity<?> getAttendees(@PathVariable String eventId){
        try{
            Event event = eventService.getEventById(eventId);
            if(event == null){
                return ResponseHelper.createErrorResponse(HttpStatus.NOT_FOUND,"Event not found",false,null);
            }

            if(event.getVisibility().equals("private")){
                return ResponseHelper.createErrorResponse(HttpStatus.FORBIDDEN,"Attendees list is private for this event",false,null);
            }
            return ResponseHelper.createResponse(HttpStatus.OK,"Event registered successfully",eventService.getAttendees(eventId),null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<Object> filterEvents(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String date
    ) {
        try{
            List<Event> data = eventService.filterEvents(category,location,date);
            return ResponseHelper.createResponse(HttpStatus.OK,"Event registered successfully",data,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }

}
