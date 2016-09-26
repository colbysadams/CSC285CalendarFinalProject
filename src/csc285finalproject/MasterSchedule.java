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
 * This Singleton class maintains a hashmap of all calendar events.
 * <p>
 * Details for the implementation of the hashmap are found in the private class
 * below.
 * <p>
 *
 * @author colbysadams
 */
public class MasterSchedule implements Serializable
{

    private static MasterSchedule schedule;
    private EventMap eventMap;

    private MasterSchedule()
    {

        eventMap = new EventMap();

        generateSampleEvents();

    }

    /**
     *
     * Singleton class ensures that only one instance of masterSchedule will
     * exist
     *
     * @return the schedule
     */
    public static MasterSchedule getInstance()
    {

        //if no schedule exists, check for a saved schedule
        if (schedule == null)
        {
            retrieveSavedSchedule();
        }
        //if no saved schedule exists, then create a new schedule
        if (schedule == null)
        {
            schedule = new MasterSchedule();
        }

        return schedule;
    }

    /**
     * if user has previously saved a schedule, retrieve it
     */
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
            {
                schedule = (MasterSchedule) obj;
            }
        }
        catch (IOException | ClassNotFoundException ex)
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

    /**
     *
     * Add a new event to the schedule
     *
     * @param date
     * @param event
     */
    public void addEventToSchedule(MyDate date, CalendarEvent event)
    {
        eventMap.put(event.getRepeating().getMapString(date), event);
    }

    /**
     *
     * Remove an event from the schedule
     *
     * @param event
     */
    public void removeFromSchedule(CalendarEvent event)
    {
        eventMap.remove(event);
    }

    /**
     *
     * Return arraylist representing all events for a given date
     * <p>
     * @param date
     *             <p>
     * @return the daysEvents
     */
    public ArrayList<CalendarEvent> getDaysEvents(MyDate date)
    {
        ArrayList<CalendarEvent> daysEvents = new ArrayList();

        ArrayList<CalendarEvent> temp;


        /*
         * checks each repeating separately, see getMapString method
         */
        for (RepeatingEnum repeating : RepeatingEnum.values())
        {
            if (eventMap.containsKey(repeating.getMapString(date)))
            {
                temp = eventMap.get(repeating.getMapString(date));
                for (int j = 0; j < temp.size(); ++j)
                {
                    daysEvents.add(temp.get(j));
                }
            }
        }

        /*
         * if an event occurs monthly on the 31st, and today is the last
         * day of (for example) february, check for all monthly events that
         * occur on
         * a later date so that this month isnt skipped
         */
        if (date.getDay() == date.getDaysInMonth() && date.getDay() < 31)
        {

            MyDate sample = null;
            try
            {
                sample = new MyDate(1, date.getDay() + 1, date.getYear());
            }
            catch (IllegalDateException ex)
            {
                System.out.println("The Monthly Event Checker has thrown an exception");
            }
            while (sample.getMonthInt() == 1)
            {
                if (eventMap.containsKey(RepeatingEnum.MONTHLY.getMapString(sample)))
                {
                    temp = eventMap.get(RepeatingEnum.MONTHLY.getMapString(sample));

                    for (int i = 0; i < temp.size(); ++i)
                    {
                        daysEvents.add(temp.get(i));
                    }
                }
                sample.nextDay();
            }
        }

        daysEvents.sort(null);
        return daysEvents;

    }

    /**
     * returns all events that have reminders that need to be fired.
     * <p>
     * @param date
     *             <p>
     * @return
     */
    public ArrayList<CalendarEvent> getTodaysEventReminders(MyDate date)
    {
        ArrayList<CalendarEvent> temp;
        ArrayList<CalendarEvent> reminderEvents = new ArrayList();

        //if reminder wraps around midnight, ignore it bc it was fired yesterday
        temp = this.getDaysEvents(date);
        for (CalendarEvent e : temp)
        {
            if (!e.hasReminder())
            {
                continue;
            }
            if (e.getTimeObject().getTime() == null)
            {
                reminderEvents.add(e);
            } else if (!e.getTimeObject().getReminder().isAfter(e.getTimeObject().getTime()))
            {
                reminderEvents.add(e);
            }

        }
        MyDate tomorrow = date.clone();
        tomorrow.nextDay();
        temp = this.getDaysEvents(tomorrow);
        //get events that have a reminder that wraps around midnight
        for (CalendarEvent e : temp)

        {
            if (e.hasReminder() && e.getTimeObject().getTime() != null)
            {
                if (e.getTimeObject().getReminder().isAfter(e.getTimeObject().getTime()))
                {
                    reminderEvents.add(e);
                }
            }
        }
        return reminderEvents;
    }

    /**
     * At 5:30 pm, user will be reminded of tomorrows "all day" events that
     * have a reminder set
     * <p>
     * @param date
     *             <p>
     * @return
     */
    public ArrayList<CalendarEvent> getTomorrowsEventReminders(MyDate date)
    {
        ArrayList<CalendarEvent> temp;
        ArrayList<CalendarEvent> reminderEvents = new ArrayList();

        MyDate tomorrow = date.clone();
        tomorrow.nextDay();
        temp = this.getDaysEvents(tomorrow);
        for (CalendarEvent e : temp)
        {
            if (e.hasReminder() && e.getTimeObject().getTime() == null)
            {
                reminderEvents.add(e);
            }
        }
        return reminderEvents;
    }

    /**
     * generates a handful of generic events to populate the calendar
     */
    private void generateSampleEvents()
    {
        //MAKES TYPING EASIER
        RepeatingEnum DAILY = RepeatingEnum.DAILY;
        RepeatingEnum WEEKLY = RepeatingEnum.WEEKLY;
        RepeatingEnum MONTHLY = RepeatingEnum.MONTHLY;
        RepeatingEnum YEARLY = RepeatingEnum.YEARLY;
        RepeatingEnum ONETIME = RepeatingEnum.ONETIME;
        try
        {
            System.out.println("generating events");
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
            addEventToSchedule(new MyDate(2, 29, 2000), new CalendarEvent("Leap Year", EventType.social, YEARLY));
            addEventToSchedule(new MyDate(1, 18, 2999), new CalendarEvent("MLK Day", EventType.holiday, YEARLY));
            addEventToSchedule(new MyDate(2, 8, 2016), new CalendarEvent("Chinese New Year", EventType.holiday, YEARLY));
            addEventToSchedule(new MyDate(2, 14, 2016), new CalendarEvent("Valentine's Day", EventType.holiday, YEARLY));
            addEventToSchedule(new MyDate(2, 15, 2016), new CalendarEvent("Presidents' Day", EventType.holiday, YEARLY));
            addEventToSchedule(new MyDate(3, 17, 2016), new CalendarEvent("St. Patrck's Day", EventType.holiday, YEARLY));
            addEventToSchedule(new MyDate(3, 25, 2016), new CalendarEvent("Good Friday", EventType.holiday, YEARLY));
            addEventToSchedule(new MyDate(3, 27, 2016), new CalendarEvent("Easter", EventType.holiday, YEARLY));
            addEventToSchedule(new MyDate(5, 5, 2016), new CalendarEvent("Cinco De Mayo", EventType.holiday, YEARLY));
            addEventToSchedule(new MyDate(5, 8, 2016), new CalendarEvent("Mother's Day", EventType.holiday, YEARLY));
            addEventToSchedule(new MyDate(6, 19, 2016), new CalendarEvent("Father's Day", EventType.holiday, YEARLY));
            addEventToSchedule(new MyDate(7, 4, 2016), new CalendarEvent("Independence Day", EventType.holiday, YEARLY));
            addEventToSchedule(new MyDate(9, 5, 2016), new CalendarEvent("Labor Day", EventType.holiday, YEARLY));
            addEventToSchedule(new MyDate(10, 31, 2016), new CalendarEvent("Halloween", EventType.holiday, YEARLY));
            addEventToSchedule(new MyDate(11, 24, 2016), new CalendarEvent("Thanksgiving Day", EventType.holiday, YEARLY));
            addEventToSchedule(new MyDate(12, 25, 2016), new CalendarEvent("Christmas Day", EventType.holiday, YEARLY));
        }
        catch (IllegalDateException ex)
        {
            System.out.println("illegal date");
        }
    }

    /**
     * wrapper class for HashMap that holds the events.
     * HashMap uses strings for keys, and an ArrayList of CalendarEvents as
     * value
     * HashMap is wrapped to alter the behavior of put and remove
     */
    private class EventMap implements Serializable
    {

        private HashMap<String, ArrayList<CalendarEvent>> eventMap;

        private EventMap()
        {
            eventMap = new HashMap();
        }

        /**
         *
         * @return the size
         */
        public int size()
        {
            return eventMap.size();
        }

        /**
         *
         * @return isEmpty
         */
        public boolean isEmpty()
        {
            return eventMap.isEmpty();
        }

        /**
         *
         * @param key
         *            <p>
         * @return containsKey
         */
        public boolean containsKey(String key)
        {
            return eventMap.containsKey(key);
        }

        /**
         *
         * @param key
         *            <p>
         * @return
         */
        public ArrayList<CalendarEvent> get(String key)
        {
            return eventMap.get(key);
        }

        /**
         * normally, this method would replace the arraylist currently in place
         * but method has been wrapped to modify it to access the arraylist and
         * then add the new event to the array list
         * <p>
         * @param key
         * @param value
         */
        public void put(String key, CalendarEvent value)
        {
            value.setDateString(key);
            ArrayList<CalendarEvent> daysEvents;

            if (!containsKey(key))
            {
                daysEvents = new ArrayList();
            } else
            {
                daysEvents = eventMap.get(key);
            }
            daysEvents.add(value);
            eventMap.put(key, daysEvents);

        }

        /**
         * normally, this method would remove the entire key/value pair from the
         * hashmap, but it has been altered to simply retrieve the arraylist and
         * remove the specified event.
         * <p>
         * @param event
         */
        public void remove(CalendarEvent event)
        {
            ArrayList<CalendarEvent> daysEvents;
            try
            {
                daysEvents = eventMap.get(event.getDateString());
            }
            catch (NullPointerException e)
            {
                return;
            }
            daysEvents.remove(event);
        }

        public void clear()
        {
            eventMap.clear();
        }

        /**
         *
         * @return the keySet
         */
        public Set keySet()
        {
            return eventMap.keySet();
        }

        /**
         *
         * @return the values
         */
        public Collection values()
        {
            return eventMap.values();
        }
    }
}
