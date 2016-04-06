/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;



/**
 *
 * @author colbysadams
 */
public abstract class CalendarEvent 
{
    
    private MyDate startDate, endDate;
    
    private String name,description;
    private EventType eventType;
    
    
    
    public CalendarEvent(MyDate startDate, MyDate endDate, String name, String description, EventType eventType) 
    {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.description = description;
        this.eventType = eventType;
        
    }
    
    
    
    
}
