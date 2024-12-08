package com.event_management.management.service;

import com.event_management.management.dto.RegistrationRequestDto;
import com.event_management.management.helpers.ErrorHelper;
import com.event_management.management.model.Event;
import com.event_management.management.model.EventCategory;
import com.event_management.management.model.EventRegistration;
import com.event_management.management.model.User;
import com.event_management.management.repository.EventRegistrationRepository;
import com.event_management.management.repository.EventRepository;
import com.event_management.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventRegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventRegistrationRepository eventRegistrationRepository;

    public List<EventRegistration> getAllRegistrations(){
        try{
            return eventRegistrationRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException(ErrorHelper.handleControllerError(e));
        }
    }

    public EventRegistration registerEvent(RegistrationRequestDto reg){
        try{
            User user = userRepository.findById(reg.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
            Event event = eventRepository.findById(reg.getEventId()).orElseThrow(() -> new RuntimeException("Event not found"));

            if(event.getRegisteredUserIds().contains(reg.getUserId())){
                throw new RuntimeException("User is already registered");
            }

            if(event.getRegisteredUserIds().size() >= event.getMaxRegistrations()){
                throw new RuntimeException("Event registration is full");
            }
            System.out.println(reg.getUserId());
            event.getRegisteredUserIds().add(reg.getUserId());
            eventRepository.save(event);
            EventRegistration registration = new EventRegistration();
            registration.setUserId(user);
            registration.setEventId(event);
            registration.setRegistrationDate(reg.getRegistrationDate());
            registration.setStatus(reg.getStatus());
            return eventRegistrationRepository.save(registration);
        }catch (Exception e){
            throw e;
        }
    }

//    public List<EventRegistration> getEventRegistrationById(String id){
//        try{
//            Optional<EventRegistration> registrations = eventRegistrationRepository.findById(id).orElseThrow();
//            return eventRegistrationRepository.findAll();
//        }catch (Exception e){
//            throw new RuntimeException(ErrorHelper.handleControllerError(e));
//        }
//    }

    public List<EventRegistration> getUserRegistrations(String userId){
        try{
            List<EventRegistration> registrations = eventRegistrationRepository.findByUserId(userId);
            return eventRegistrationRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException(ErrorHelper.handleControllerError(e));
        }
    }
}
