package com.event_management.management.service;

import com.event_management.management.helpers.ErrorHelper;
import com.event_management.management.helpers.ResponseHelper;
import com.event_management.management.model.Event;
import com.event_management.management.model.EventCategory;
import com.event_management.management.repository.EventCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventCategoryService {

    @Autowired
    private EventCategoryRepository eventCategoryRepository;


    public ResponseEntity<Object> createEventCategory(EventCategory eventCategory){
        try{
            eventCategoryRepository.save(eventCategory);
            return ResponseHelper.createResponse(HttpStatus.CREATED,"Created",null,null);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internaml Server erorr");
        }
    }

    public List<EventCategory> getAllEvents(){
        try{
            return eventCategoryRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException(ErrorHelper.handleControllerError(e));
        }
    }

    public boolean updateEventCategory(String id,EventCategory updatedEvent){
        try{
            eventCategoryRepository.findById(id)
                    .map(eventCategory -> {
                        eventCategory.setName(updatedEvent.getName());
                        eventCategory.setDescription(updatedEvent.getDescription());

                        return eventCategoryRepository.save(eventCategory);
                    })
                    .orElseThrow(() -> new RuntimeException("Event category not found"));

            return true;
        }catch (Exception e){
            throw new RuntimeException(ErrorHelper.handleControllerError(e));
        }
    }

    public boolean deleteEventCategory(String id){
        try{
            if (!eventCategoryRepository.existsById(id)) {
                throw new RuntimeException("Event id is not found");
            }
            eventCategoryRepository.deleteById(id);
            return true;
        }catch (Exception e){
            throw new RuntimeException(ErrorHelper.handleControllerError(e));
        }
    }

}
