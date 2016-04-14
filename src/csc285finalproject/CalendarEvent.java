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

    private String dateString;
    
    private EventType eventType;

    
    public static final int ONETIME = 0,YEARLY = 1,MONTHLY = 2,WEEKLY = 3;
    private int repeating;

    //private static int eventNameBuffer = 30;

    private MyTime time;

    public CalendarEvent(String name, EventType eventType,int repeating) {
        
        this.name = name;
        this.location = "";
        this.description = "";
        this.dateString = null;
        this.eventType = eventType;
        this.repeating = repeating;
        this.time = new MyTime();
      
    }
    
    public void setDateString(String dateString){
        this.dateString = dateString;
    }
    
    public String getDateString(){
        return dateString;
    }
    
    public CalendarEvent(){
        this("",EventType.other,ONETIME);
    }
    
    public int getRepeating(){
        return repeating;
    }
    
    public String getRepeatingString(){
        switch (repeating){
            case 0:
                return "One Time";
            case 1:
                return "Yearly";
            case 2:
                return "Monthly";
            case 3:
                return "Weekly";
        }
        return "";
    }
    
    public void setRepeating(int repeating){
        this.repeating = repeating;
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
    public String getDescriptionLabel() {
        if (description.equals("")) 
            return "";
        String s;
        s = "<html><body>";

            s+= "<p><b>Description: </b></p>";
            s+= "<p>&nbsp;&nbsp;" + description + "</p>";
        
        
        s+="</body></html>";
        return s;
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
    
    public String getLocationLabel() {
        if (location.equals("")) 
            return "";
        String s;
        s = "<html><body>";

            s+= "<p><b>Location: </b></p>";
            s+= "<p>&nbsp;&nbsp;" + location + "</p>";
        
        
        s+="</body></html>";
        return s;
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
    
    
    public String getEventHTML(){
        
        String s;
        s = "<html><body>";
        s+= "<p style=font-size:120%;>";
        if (time != null)
            s+= "&nbsp;" +time;
        s+= "&nbsp;&nbsp;" + name +"&nbsp;&nbsp;";
        //s+= "<style="
        s+= "</p>";
        
        s+= "<blockquote style=color:rgb(" + eventType.COLOR.getRed()+ ","
                                       + eventType.COLOR.getGreen() + ","
                                       + eventType.COLOR.getBlue()
                                       + ")>";
        s+= eventType.TYPE + "</blockquote>";
        if (location !=  null || location.isEmpty()){
            s+= "<p><b>Location: </b></p>";
            s+= "<blockquote>" + location + "</blockquote>";
        }
        if (description != null || description.isEmpty()){
            s+= "<p><b>Description: </b></p>";
            s+= "<blockquote>" + description + "</blockquote>";
        }
        
        s+="</body></html>";
        return s;
    }
    
}
