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
public class WeeklyEvent extends AbstractCalendarEvent

{
    
    private Weekday weekday;

    public WeeklyEvent(String name,  EventType eventType, MyDate date) {
        super(name, eventType);
        this.weekday = date.getDayOfWeek();
    }
    
    public WeeklyEvent(String name, EventType eventType, Weekday weekday) {
        super(name, eventType);
        this.weekday = weekday;
    }
    

    @Override
    public boolean isOnSelectedDate() 
    {
        return (weekday == SelectedDate.getInstance().getDayOfWeek());
    }
    
    
    
}
