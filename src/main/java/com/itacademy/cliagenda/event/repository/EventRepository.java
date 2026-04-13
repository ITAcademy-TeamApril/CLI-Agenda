package com.itacademy.cliagenda.event.repository;

import com.itacademy.cliagenda.event.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventRepository {

    private List<Event> eventList = new ArrayList<>();

    //SAVE EVENT
    public void save(Event event){
        eventList.add(event);
    }

    //LIST ALL EVENTS
    public List<Event> getAllEvents(){
        return List.copyOf(eventList);
    }

    //FIND EVENT BY ID
    public Event findEventById(int id) {
        for (Event element: eventList) {
            if(element.getIdEvent()==id){
                return element;
            }
        }
        System.out.println("Event not found");
        return null;
    }

    //REMOVE EVENTS FROM ID
    public void removeEventById(int id) {
        Event eventToRemove = null;
        for(Event element : eventList) {
            if(element.getIdEvent() == id) {
                eventToRemove = element;
                break;
            }
        }
        if(eventToRemove != null) {
            eventList.remove(eventToRemove);
        } else {
            System.out.println("Event not found with ID: " + id);
        }

    }


}
