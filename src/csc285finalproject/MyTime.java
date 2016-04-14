/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.io.Serializable;
import java.time.LocalTime;

/**
 *
 * @author colbysadams
 */
public class MyTime implements Serializable{
    
    private LocalTime start;
    private int hours,minutes;
    
    public MyTime(LocalTime start)
    {
        this.start = start;
        this.hours = 1;
        this.minutes = 0;
    }
    
    public MyTime(){
        start = null;
        
    }

    /**
     * @return the start
     */
    public LocalTime getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(LocalTime start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public LocalTime getEnd() {
        LocalTime end = start.plusHours(hours);
        end.plusHours(minutes);
        return end;
    }

    
    
    public void setDuration(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }
    
    public String toString(){
        if (start == null)
            
            return "";
//        String s;
//        hour int = S
        return start.toString();
    }
    
    public String getClock(){
        if (start == null)
            
            return "";
    
        
        String s = "";
         
        s += start.getHour()%12;
        
        if (s.equals("0"))
            s = "12";
        s+= ":";
        if (start.getMinute()<10)
            s+=0;
        s+= start.getMinute();
        
        if ( start.getHour()/12 == 1)
            s+= "PM";
        else{
            s+= "AM";
        }
            
        return s;
        
    }
    
}
