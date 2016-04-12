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
public class OneTimeEvent extends AbstractCalendarEvent
{
    
    private MyDate date;

    public OneTimeEvent(String name, EventType eventType, MyDate date) {
        super(name, eventType);
        this.date = date;
    }

    @Override
    public boolean isOnSelectedDate() {
        return (this.date.equals(SelectedDate.getInstance()));
            
    }
    
    
    
    
}
