package com.event_management.management.service;

import com.event_management.management.helpers.ErrorHelper;
import com.event_management.management.helpers.ResponseHelper;
import com.event_management.management.model.Event;
import com.event_management.management.model.EventCategory;
import com.event_management.management.model.EventRegistration;
import com.event_management.management.repository.EventCategoryRepository;
import com.event_management.management.repository.EventRegistrationRepository;
import com.event_management.management.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventCategoryRepository eventCategoryRepository;
    @Autowired
    private EventRegistrationService eventRegistrationService;
    @Autowired
    private EventRegistrationRepository eventRegistrationRepository;

    public List<Event> getAllEvents(){
        try{
            return eventRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException(ErrorHelper.handleControllerError(e));
        }
    }

    public List<Event> searchEvents(String search){
        try{
            if (search == null || search.isEmpty()) {
                return eventRepository.findAll();
            }
            return eventRepository.findByNameContainingIgnoreCase(search);
        }catch (Exception e){
            throw new RuntimeException(ErrorHelper.handleControllerError(e));
        }
    }

    public Event getEventById(String eventId){
        try{
            return eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("invalid event id"));
        }catch (Exception e){
            throw new RuntimeException(ErrorHelper.handleControllerError(e));
        }
    }

    public List<EventRegistration> getAttendees(String eventId){
        try{
            return eventRegistrationRepository.findByEventId(eventId);
        }catch (Exception e){
            throw new RuntimeException(ErrorHelper.handleControllerError(e));
        }
    }

    public Event createEvent(Event event,String categoryId){
        try{
            EventCategory category = eventCategoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("category id is invalid"));
            System.out.println(event.toString());
            event.setCategory(category);
            eventRepository.save(event);
            return event;
        }catch (Exception e){
            throw e;
        }
    }

    public Event updateEvent(String id,String categoryId,Event updatedEvent){
       try{
           System.out.println("print");
           return eventRepository.findById(id)
                   .map(event -> {
                       if(categoryId != null){
                           EventCategory category = eventCategoryRepository.findById(categoryId)
                                   .orElseThrow(() -> new RuntimeException("invalid event category id"));
                           event.setCategory(category);
                       }
                       event.setName(updatedEvent.getName());
                       event.setDescription(updatedEvent.getDescription());
                       event.setDate(updatedEvent.getDate());
                       event.setDeadline(updatedEvent.getDeadline());
                       event.setLocation(updatedEvent.getLocation());
                       event.setStatus(updatedEvent.getStatus());
                       event.setMaxRegistrations(updatedEvent.getMaxRegistrations());
                       event.setVisibility(updatedEvent.getVisibility());
                       return eventRepository.save(event);
                   })
                   .orElseThrow(() -> new RuntimeException("Event not found!"));
       }catch (Exception e){
           throw e;
       }
    }

    public String generateCSV(List<EventRegistration> attendees){
        StringBuilder csv = new StringBuilder("Name,email,event");
        for (EventRegistration attendee : attendees){
            csv.append(attendee.getUserId().getUserId()).append(", ")
                    .append(attendee.getUserId().getEmail()).append(", ")
                    .append(attendee.getEventId().getName()).append("\n");
        }

        return csv.toString();
    }

    public boolean deleteEvent(String id){
        try{
            eventRepository.deleteById(id);
            return true;
        }catch (Exception e){
            throw e;
        }
    }

    public List<Event> filterEvents(String category,String location,String date){
        try{
            if (category != null) {
                return eventRepository.findByCategory(category);
            } else if (location != null) {
                return eventRepository.findByLocation(location);
            } else if (date != null) {
                LocalDate eventDate = LocalDate.parse(date);
                return eventRepository.findByDate(eventDate);
            }
            return eventRepository.findAll();
        }catch (Exception e){
            throw e;
        }
    }
}
