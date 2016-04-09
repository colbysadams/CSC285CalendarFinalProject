/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;



/**
 *
 * @author colbysadams
 */
public abstract class CalendarEvent implements Icon
{
    
    private MyDate startDate, endDate;
    
    private String name,description;
    private EventType eventType;
    
    public static final int diameter = 5;
    
    
    public CalendarEvent(MyDate startDate, MyDate endDate, String name, String description, EventType eventType) 
    {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.description = description;
        this.eventType = eventType;
        
    }
    
    public void paintIcon(Component c, Graphics g, int x, int y) 
    {
        g.setColor(eventType.COLOR);
        g.drawOval(x, y, getIconWidth(), getIconHeight());
    }
    
    public int getIconWidth()
    {
        return diameter;    
    }
    
    public int getIconHeight()
    {
        return diameter;
    }
    
}
