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
public class CalendarEvent implements Serializable, Comparable<CalendarEvent>
{
    public static final int ONETIME = 0;
    public static final int YEARLY = 1;
    public static final int MONTHLY = 2;
    public static final int WEEKLY = 3;

    private String name, description, location;
    private String dateString;

    private EventType eventType;

    private int repeating;
    
    private MyTime time;

    public CalendarEvent(String name, EventType eventType, int repeating)
    {

        this.name = name;
        this.location = "";
        this.description = "";
        this.dateString = null;
        this.eventType = eventType;
        this.repeating = repeating;
        this.time = new MyTime();

    }

    public CalendarEvent()
    {
        this("", EventType.other, ONETIME);
    }

    public void setDateString(String dateString)
    {
        this.dateString = dateString;
    }

    public String getDateString()
    {
        return dateString;
    }

    public int getRepeating()
    {
        return repeating;
    }

    public void setRepeating(int repeating)
    {
        this.repeating = repeating;
    }

    public String getRepeatingString()
    {
        switch (repeating)
        {
            case 0:
                return "<html><p>&nbsp;&nbsp;One Time</html></p>";
            case 1:
                return "<html><p>&nbsp;&nbsp;Yearly</html></p>";
            case 2:
                return "<html><p>&nbsp;&nbsp;Monthly</html></p>";
            case 3:
                return "<html><p>&nbsp;&nbsp;Weekly</html></p>";
        }
        return "";
    }

    @Override
    public String toString()
    {
        return name;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    public String getShortLabel()
    {
        String s = "";
        if (time.getStart() != null)
            s += time.getClock() + "  ";
        s += name;
        return s;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescriptionLabel()
    {
        if (description.equals(""))
            return "";
        String s;
        s = "<html><body>";

        s += "<p><b>Description: </b></p>";
        s += "<p>&nbsp;&nbsp;" + description + "</p>";

        s += "</body></html>";
        return s;
    }

    /**
     * @return the location
     */
    public String getLocation()
    {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getLocationLabel()
    {
        if (location.equals(""))
            return "";
        String s;
        s = "<html><body>";

        s += "<p><b>Location: </b></p>";
        s += "<p>&nbsp;&nbsp;" + location + "</p>";

        s += "</body></html>";
        return s;
    }

    /**
     * @return the eventType
     */
    public EventType getEventType()
    {
        return eventType;
    }

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(EventType eventType)
    {
        this.eventType = eventType;
    }

    /**
     * @return the time
     */
    public MyTime getTime()
    {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(MyTime time)
    {
        this.time = time;
    }

    @Override
    public int compareTo(CalendarEvent o)
    {
        if (time.compareTo(o.time) == 0)
        {
            if (repeating == o.repeating)
                return name.compareTo(o.name);
            return repeating - o.repeating;
        }
        return time.compareTo(o.time);
    }

}
