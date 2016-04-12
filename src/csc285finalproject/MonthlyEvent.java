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
public class MonthlyEvent extends AbstractCalendarEvent{
    
    
    private int day;

    public MonthlyEvent(String name,  EventType eventType, MyDate date) {
        super(name,  eventType);
        
        this.day = date.getDay();
    }
    
    
    
    
    @Override
    public boolean isOnSelectedDate() {
        if (SelectedDate.getInstance().getDaysInMonth() == SelectedDate.getInstance().getDay()
               && SelectedDate.getInstance().getDay() < day)
            return true;
        return (SelectedDate.getInstance().getDay() == day); 
                
    }
    
    
    
}
