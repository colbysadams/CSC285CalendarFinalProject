/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.util.ArrayList;

/**
 * 
 * Singleton instance that represents the currently selected date
 * because only one date can be selected at a time
 * keeps all the views in sync and calls them to update whenever a change
 * is made to the singleton instance
 *
 * @author colbysadams
 */
public class SelectedDate extends MyDate implements Subject
{

    private ArrayList<Observer> observers;
    
    private static SelectedDate date;
    
    private static int count = 0;
    
    private SelectedDate()
    {
        super();
        observers = new ArrayList();
    }
    
    /**
     *
     * @return the singleton object representing the currently selected date
     */
    public static SelectedDate getInstance()
    {
        if (date == null)
            date = new SelectedDate();
        return date;
    }
    
    public void nextDay()
    {
        super.nextDay();
        notifyObservers();
    }
    
    /**
     *
     * @param days number of days to advance forward
     */
    public void nextDay(int days) 
    {
        for (int i = 0; i < days; ++i)
            super.nextDay();
        notifyObservers();
    }
    
    /**
     * advances calendar forward exactly one year
     * corrects for leap year when necessary
     *
     */
    public void nextYear(){
        for (int i = 0; i < 365; i++)
        {
            super.nextDay();
            if (date.getMonth() == Month.feb && date.getDay() == 29)
                super.nextDay();
        }
        notifyObservers();
    }
    
    public void prevDay() 
    {
        
        super.prevDay();
        notifyObservers();
    }
    
    /**
     *
     * @param days number of days to go back
     */
    public void prevDay(int days) 
    {
        for (int i = 0; i < days; ++i)
            super.prevDay();
        notifyObservers();
    }
    
    /**
     * pushes calendar back exactly one year
     * corrects for leap year when necessary
     *
     */
    public void prevYear()
    {
        for (int i = 0; i < 365; i++)
        {
            super.prevDay();
            if (date.getMonth() == Month.feb && date.getDay() == 29)
                super.prevDay();
        }
        notifyObservers();
    }
    
    @Override
    public void setToToday()
    {
        super.setToToday();
        notifyObservers();
    }
    
    
    @Override
    public void addObserver(Observer o) 
    {
        observers.add(o);
    }

    
    @Override
    public void deleteObserver(Observer o) 
    {
        observers.remove(o);
    }

    
    @Override
    public void notifyObservers() 
    {
        
        for (Observer o : observers)
            o.update();
    }
    
    /**
     * 
     * 
     *
     * @param date the new value of selected date
     */
    public void changeSelectedDate(MyDate date)
    {
       //scrolls either forward or backward until reaching desired date
       while (((MyDate)SelectedDate.date).compareTo(date) < 0){
           super.nextDay();
       }
       
       while (((MyDate)SelectedDate.date).compareTo(date) > 0){
           super.prevDay();
       }
       notifyObservers();
       
   }
    
//    @Override
//    public int hashCode(){
//        return date.super.hashCode();
//    }
//    
//    public boolean equals(Object other){
//        return super.equals(other);
//    }
//    
}
