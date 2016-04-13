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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author colbysadams
 */
public class MasterSchedule implements Serializable{
    
    private static MasterSchedule schedule;
    
    private EventMap oneTimeEventMap;
    
    private EventMap monthlyEventMap;
    
    private EventMap yearlyEventMap;
    
    private EventMap weeklyEventMap;
    
    public static final int ONETIME = 0,YEARLY = 1,MONTHLY = 2,WEEKLY = 3;
    
    private MasterSchedule(){
        
        
        oneTimeEventMap = new EventMap(true,true,true,false);
        monthlyEventMap = new EventMap(false,true,false,false);
        yearlyEventMap  = new EventMap(true,true,false,false);
        weeklyEventMap  = new EventMap(false,false,false,true);
        
        generateSampleEvents();
        
    }
    
    private static void retrieveSavedSchedule(){
        ObjectInputStream obj_in = null;
        try {
            // Read from disk using FileInputStream
            FileInputStream f_in = new FileInputStream("calendar.ser");
            // Read object using ObjectInputStream
            obj_in = new ObjectInputStream (f_in);
            // Read an object
            Object obj = obj_in.readObject();
            if (obj instanceof MasterSchedule)
                schedule = (MasterSchedule)obj;
        } 
        catch (IOException ex) {
            Logger.getLogger(MasterSchedule.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(MasterSchedule.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally {
            try {
                obj_in.close();
            } catch (IOException ex) {
                Logger.getLogger(MasterSchedule.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    
    public static MasterSchedule getInstance() {
        
        if (schedule == null)
            retrieveSavedSchedule();
        if (schedule == null)
            schedule = new MasterSchedule();
    
        
        return schedule;
        
    }
    
    private void generateSampleEvents(){
        try {
            
            yearlyEventMap.put(new MyDate(3,15,2016), new CalendarEvent("Colby's Birthday",EventType.family));
            monthlyEventMap.put(new MyDate(3,31,2016),new CalendarEvent("MONTHLY BUGG",EventType.social));
            monthlyEventMap.put(new MyDate(1,1,2016), new CalendarEvent("Pay Bills",EventType.other));
            oneTimeEventMap.put(new MyDate(9,7,2016), new CalendarEvent("Emily's Birthday",EventType.family));
            yearlyEventMap.put(new MyDate(4,1,2015),  new CalendarEvent("April Fools Day", EventType.holiday));
            yearlyEventMap.put(new MyDate(3,16,2016), new CalendarEvent("Leroy's Birthday",EventType.family)); 
            weeklyEventMap.put(new MyDate(3,16,2016), new CalendarEvent("HUMP DAYYYYY", EventType.social));
            yearlyEventMap.put(new MyDate(12,25,2999), new CalendarEvent("Jesus's Birthday", EventType.holiday));
        } catch (IllegalDateException ex) {
            
        }
        
    }
    
    public void addEventToSchedule(MyDate date, CalendarEvent event, int repeating) {
        switch (repeating) {
            case 0:
                oneTimeEventMap.put(date, event);
                break;
            case 1:
                yearlyEventMap.put(date, event);
                break;
            case 2:
                monthlyEventMap.put(date, event);
                break;
            case 3:
                weeklyEventMap.put(date, event);
                break;
            case 4:
                
        }
    }
    
    public ArrayList<CalendarEvent> getDaysEvents(MyDate date) {
        ArrayList<CalendarEvent> daysEvents = new ArrayList();
        ArrayList<CalendarEvent> e;
        if (oneTimeEventMap.containsKey(date)){
            e = oneTimeEventMap.get(date);
            //System.out.println("onetime: e" + e + "Date" +date);
            for (int i = 0; i < e.size(); ++i) {
                daysEvents.add(e.get(i));
            }
            
        }
        
        if (yearlyEventMap.containsKey(date)){
            e = yearlyEventMap.get(date);
            //System.out.println("yearly: e" + e + "Date" +date);
            for (int i = 0; i < e.size(); ++i) {
                daysEvents.add(e.get(i));
            }
            
        }
        if (monthlyEventMap.containsKey(date)){
            e = monthlyEventMap.get(date);
            //System.out.println("monthly: e" + e + "Date" +date);
            for (int i = 0; i < e.size(); ++i) {
                daysEvents.add(e.get(i));
            }
            
            
        }
        if (weeklyEventMap.containsKey(date)){
            e = weeklyEventMap.get(date);
            //System.out.println("monthly: e" + e + "Date" +date);
            for (int i = 0; i < e.size(); ++i) {
                daysEvents.add(e.get(i));
            }
        }
        return daysEvents;
        
            
        
    }
    
    public boolean hasEventsOn(MyDate date) {
        return (oneTimeEventMap.containsKey(date) 
                || yearlyEventMap.containsKey(date) 
                || monthlyEventMap.containsKey(date)
                || weeklyEventMap.containsKey(date));
    }
    
    
    
    private class EventMap<CalendarEvent> implements Serializable
    {
        
        private HashMap<String,ArrayList<CalendarEvent>> eventMap;

        private boolean month,day,year,week;
        
        private EventMap(boolean month, boolean day, boolean year, boolean week)
        {
            this.month = month;
            this.day = day;
            this.year = year;
            this.week = week;
            eventMap = new HashMap<String,ArrayList<CalendarEvent>> ();
        }
        
        public int size() {
            return eventMap.size();
        }

        
        public boolean isEmpty() {
            return eventMap.isEmpty();
        }

        
        public boolean containsKey(MyDate key) {
            return eventMap.containsKey(key.getMapString(month,day,year,week));
        }
        
        public ArrayList<CalendarEvent> get(MyDate key) {
            return eventMap.get(key.getMapString(month,day,year,week));
        }

        
        public void put(MyDate key, CalendarEvent value) {
            ArrayList<CalendarEvent> daysEvents;
            if (!containsKey(key))
                daysEvents = new ArrayList<CalendarEvent>();
            else
                daysEvents = eventMap.get(key.getMapString(month,day,year,week));
            daysEvents.add(value);
            eventMap.put(key.getMapString(month,day,year,week),daysEvents);
        }

        
        public void remove(MyDate key, CalendarEvent event) {
            ArrayList<CalendarEvent> daysEvents;
            try{
                daysEvents = eventMap.get(key.getMapString(month,day,year,week));
            }
            catch (NullPointerException e) {
                System.out.println("remove(MyDate key, CalendarEvent): event doesnt exist");
                return;
            }
            daysEvents.remove(event);
        }
        
        public void clear() {
            eventMap.clear();
        }

        
        public Set keySet() {
            return eventMap.keySet();
        }

        
        public Collection values() {
            return eventMap.values();
        }

     
    }
    
    
}
