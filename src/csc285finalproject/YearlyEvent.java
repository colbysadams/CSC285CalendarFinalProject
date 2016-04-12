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
public class YearlyEvent extends AbstractCalendarEvent{

    private Month month;
    private int day;

    public YearlyEvent(String name,  EventType eventType, MyDate date) {
        super(name,  eventType);
        this.month = date.getMonth();
        this.day = date.getDay();
    }
    
    
    
    
    @Override
    public boolean isOnSelectedDate() {
        
        return (SelectedDate.getInstance().getDay() == day 
                && SelectedDate.getInstance().getMonth() == month);
    }
    
    
    
}
