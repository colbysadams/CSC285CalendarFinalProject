/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.awt.Dimension;
import javax.swing.JLabel;



/**
 *
 * @author colbysadams
 */
public abstract class AbstractCalendarEvent extends JLabel
{
    
    //private MyDate date;
    
    private String name,description,location;
    
    private EventType eventType;
    
    public static final int diameter = 5;
    
    private static int eventNameBuffer = 30;
    
    
    
    public AbstractCalendarEvent(String name, EventType eventType) 
    {
        super();
        this.name = name;
        this.description = null;
        this.eventType = eventType;
        this.description = null;
        this.location = null;
        
        this.setOpaque(true);
        this.setBackground(eventType.COLOR);
        this.setText(name);
        
        
        
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    //public void setTime(int 
    
    
    public String getName() 
    {
        return name;
    }
    
    public String getDescription()
    {
        return description;
    }

    
    public String toString() 
    {
        return name + ": " + description; 
    }
    
    public abstract boolean isOnSelectedDate();
}
