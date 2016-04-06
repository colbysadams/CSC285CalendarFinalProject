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
public class CalendarEvent {
    
    private MyDate startDate, endDate;
    
    private String name;
    
    
    
    public CalendarEvent(MyDate date, String name) {
        this(date,null,name);
    }
    
    public CalendarEvent(MyDate startDate, MyDate endDate, String name){
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
    }
    
    
}
