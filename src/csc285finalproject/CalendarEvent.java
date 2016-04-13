/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.io.Serializable;

/**
 *
 * @author colbysadams
 */
public class CalendarEvent implements Serializable{

    

    private String name, description, location;

    private EventType eventType;

    //public static final int diameter = 5;

    //private static int eventNameBuffer = 30;

    private MyTime time;

    public CalendarEvent(String name, EventType eventType) {
        super();
        this.name = name;
        this.eventType = eventType;

//        this.description = null;
//        this.description = null;
//        this.location = null;
//
//        this.time = new MyTime();
      
    }
    
    
    
    


    @Override
    public String toString() {
        return name;//+ ": " + location + "\n" + description; 
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the eventType
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    /**
     * @return the time
     */
    public MyTime getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(MyTime time) {
        this.time = time;
    }
    
}