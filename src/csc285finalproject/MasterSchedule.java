/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author colbysadams
 */
public class MasterSchedule implements Serializable
{

    public static final int ONETIME = 0;
    public static final int YEARLY = 1;
    public static final int MONTHLY = 2;
    public static final int WEEKLY = 3;
    public static final int DAILY = 4;

    public static String[] repeatStrings =
    {
        "Never", "Yearly", "Monthly", "Weekly", "Everyday"
    };

    private static int REPEATTYPES = 5;

    private static MasterSchedule schedule;

    private EventMap eventMap;

    private MasterSchedule()
    {

        eventMap = new EventMap();

        generateSampleEvents();

    }

    public static MasterSchedule getInstance()
    {
        if (schedule == null)
            retrieveSavedSchedule();
        if (schedule == null)
            schedule = new MasterSchedule();

        return schedule;
    }

    private static void retrieveSavedSchedule()
    {
        ObjectInputStream obj_in = null;
        try
        {
            // Read from disk using FileInputStream
            FileInputStream f_in = new FileInputStream("calendar.ser");
            // Read object using ObjectInputStream
            obj_in = new ObjectInputStream(f_in);
            // Read an object
            Object obj = obj_in.readObject();
            if (obj instanceof MasterSchedule)
                schedule = (MasterSchedule) obj;
        }
        catch (IOException ex)
        {
            System.out.println("Loading Failed");
        }
        catch (ClassNotFoundException ex)
        {
            System.out.println("Loading Failed");
        }
        finally
        {
            try
            {
                obj_in.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(MasterSchedule.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void addEventToSchedule(MyDate date, CalendarEvent event)
    {
        eventMap.put(getMapString(date, event.getRepeating()), event);
    }

    public void removeFromSchedule(CalendarEvent event)
    {
        eventMap.remove(event);
    }

    public ArrayList<CalendarEvent> getDaysEvents(MyDate date)
    {
        ArrayList<CalendarEvent> daysEvents = new ArrayList();

        ArrayList<CalendarEvent> temp;

        for (int repeating = 0; repeating < REPEATTYPES; ++repeating)
            if (eventMap.containsKey(getMapString(date, repeating)))
            {
                temp = eventMap.get(getMapString(date, repeating));
                for (int j = 0; j < temp.size(); ++j)
                    daysEvents.add(temp.get(j));
            }

        //tests for end of month events
        if (date.getDay() == date.getDaysInMonth() && date.getDay() < 31)
        {

            MyDate sample = null;
            try
            {
                sample = new MyDate(1, date.getDay() + 1, date.getYear());
            }
            catch (IllegalDateException ex)
            {
                System.out.println("we messing up");
            }
            while (sample.getMonthInt() == 1)
            {
                if (eventMap.containsKey(getMapString(sample, MONTHLY)))
                {
                    temp = eventMap.get(getMapString(sample, MONTHLY));
                    //System.out.println("monthly: e" + e + "Date" +date);
                    for (int i = 0; i < temp.size(); ++i)
                        daysEvents.add(temp.get(i));
                }
                sample.nextDay();
            }
        }

        daysEvents.sort(null);
        return daysEvents;

    }

    public boolean hasEventsOn(MyDate date)
    {
        if (date.getDay() == date.getDaysInMonth())
        {
            //test for end of month events (ie this month only has 28 days, but a monthly
            //event occurs on the 31 of every month.
            MyDate sample = null;
            try
            {
                sample = new MyDate(1, date.getDay(), date.getYear());
            }
            catch (IllegalDateException ex)
            {
                Logger.getLogger(MasterSchedule.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (sample.getMonthInt() == 1)
            {
                if (eventMap.containsKey(getMapString(sample, MONTHLY)))
                    return true;
                sample.nextDay();
            }
        }
        for (int i = 0; i < REPEATTYPES; ++i)
            if (eventMap.containsKey(getMapString(date, i)))
                return true;
        return false;
    }

    private void generateSampleEvents()
    {
        try
        {

            addEventToSchedule(new MyDate(3, 15, 2016), new CalendarEvent("Daily Event", EventType.family, DAILY));
            addEventToSchedule(new MyDate(3, 31, 2016), new CalendarEvent("Last Day of Month", EventType.other, MONTHLY));
            addEventToSchedule(new MyDate(1, 1, 2016), new CalendarEvent("First Day of Month", EventType.work, MONTHLY));
            addEventToSchedule(new MyDate(9, 7, 2016), new CalendarEvent("Emily's Birthday", EventType.family, YEARLY));
            addEventToSchedule(new MyDate(4, 1, 2015), new CalendarEvent("April Fools Day", EventType.holiday, YEARLY));
            addEventToSchedule(new MyDate(5, 1, 2016), new CalendarEvent("Leroy's Birthday", EventType.family, YEARLY));
            addEventToSchedule(new MyDate(3, 16, 2016), new CalendarEvent("Weekly Event", EventType.school, WEEKLY));
            addEventToSchedule(new MyDate(12, 25, 2999), new CalendarEvent("Jesus's Birthday", EventType.holiday, YEARLY));
            addEventToSchedule(new MyDate(1, 1, 2999), new CalendarEvent("New Year's Day", EventType.holiday, YEARLY));
            addEventToSchedule(new MyDate(12, 31, 2999), new CalendarEvent("New Year's Eve", EventType.holiday, YEARLY));
            addEventToSchedule(new MyDate(2, 29, 2999), new CalendarEvent("Works On Leap Year", EventType.social, YEARLY));
            addEventToSchedule(new MyDate(1, 18, 2999), new CalendarEvent("MLK Day", EventType.holiday, YEARLY));
        }
        catch (IllegalDateException ex)
        {

        }
    }

    private static String getMapString(MyDate date, int repeating)
    {
        switch (repeating)
        {
            case ONETIME:
                return date.getMonthString() + "_" + date.getDay() + "_" + date.getYear();
            case YEARLY:
                return date.getMonthString() + "_" + date.getDay() + "_" + "YYYY";
            case MONTHLY:
                return "MM_" + date.getDay() + "_YYYY";
            case WEEKLY:
                return date.getDayOfWeek().name;
            case DAILY:
                return "MM_DD_YYYY";
        }
        return "";

    }

    //public static final int ONETIME = 0,YEARLY = 1,MONTHLY = 2,WEEKLY = 3;
    private class EventMap implements Serializable
    {

        private HashMap<String, ArrayList<CalendarEvent>> eventMap;

        private boolean month, day, year, week;

        private EventMap(boolean month, boolean day, boolean year, boolean week)
        {
            this.month = month;
            this.day = day;
            this.year = year;
            this.week = week;
            eventMap = new HashMap();
        }

        private EventMap()
        {
            eventMap = new HashMap();
        }

        public int size()
        {
            return eventMap.size();
        }

        public boolean isEmpty()
        {
            return eventMap.isEmpty();
        }

        public boolean containsKey(String key)
        {
            return eventMap.containsKey(key);
        }

        public ArrayList<CalendarEvent> get(String key)
        {
            return eventMap.get(key);
        }

        public void put(String key, CalendarEvent value)
        {
            value.setDateString(key);
            ArrayList<CalendarEvent> daysEvents;
            if (!containsKey(key))
                daysEvents = new ArrayList();
            else
                daysEvents = eventMap.get(key);
            daysEvents.add(value);
            eventMap.put(key, daysEvents);
        }

        public void remove(CalendarEvent event)
        {
            ArrayList<CalendarEvent> daysEvents;
            try
            {
                daysEvents = eventMap.get(event.getDateString());
            }
            catch (NullPointerException e)
            {
                System.out.println("remove(MyDate key, CalendarEvent): event doesnt exist");
                return;
            }
            daysEvents.remove(event);
        }

        public void clear()
        {
            eventMap.clear();
        }

        public Set keySet()
        {
            return eventMap.keySet();
        }

        public Collection values()
        {
            return eventMap.values();
        }

    }

}
