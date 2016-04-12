/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author colbysadams
 */
public class MyDate implements Comparable<MyDate>
{
    
    private int day, year;
    private Month month;
    //private transient static Calendar today = Calendar.getInstance();;
    
    
    /**
     * Constructor makes MyDate object for today's date
     */
    public MyDate()
    {
        try
        {
        month = Month.getMonth((Calendar.getInstance().get(Calendar.MONTH)+1));
        }
        catch(IllegalDateException e) 
        {
            System.out.println("In MyDate() Constructor " + e.getMessage());
        }
        day = Calendar.getInstance().get(Calendar.DATE);
        year = Calendar.getInstance().get(Calendar.YEAR);
        
    }
    
    /**
     *
     * @param month
     * @param day
     * @param year
     * @throws IllegalDateException
     */
    public MyDate(int month, int day, int year) throws IllegalDateException
    {
        this.month = Month.getMonth(month);
        this.day = day;
        this.year = year;
        
        if (!valid())
            throw new IllegalDateException("Illegal Date Entered into MyDate(Month month, int day, int year)");
    }
    
    /**
     *
     * @return true if date is in a leap year
     */
    public boolean isLeapYear()
    {
        //returns true if it is leap year
        return !((year % 4 != 0) || ((year % 100 == 0 && year % 400 != 0)));
    }
    
    /**
     *
     * @return true if the date is a valid calendar date between October 15, 1582 and December 31, 9999
     */
    private boolean valid() 
    //Returns true if date is valid
    {
        //if day is before Oct 15, 1582 return false
        //calendar is only good for the next 8000 years (formatting purposes)
        if (year < 1582 || year > 9999)
            return false;
        if (year == 1582){
            if (month.compareTo(Month.oct) < 0)
            {
                return false;
            }
            if (month == Month.oct && day < 15)
                return false;
        }
        
        
        // if it is a february and leap year, add one day to daysPerMonth then compare
        if (month == Month.feb) 
        {
            //returns true if leap year
            if (isLeapYear()) 
            {
                if (day > 0 && day <= month.getNormalDaysInMonth()+1)
                    return true;
            }
        }
        //not a leap year, check that day is valid and return
        return (day > 0 && day <= month.getNormalDaysInMonth());
    }
    
    /**
     * 
     * Calculates the day of the week of the date
     *
     * @return the day of the week 
     */
    public Weekday getDayOfWeek()
    {
        //first day of gregorian Calendar was a friday
        int daysSince = 4 + lilian();
        //calculate number of days since then mod 7
        int weekdayNum =  daysSince%7;
        
        return Weekday.getWeekday(weekdayNum);
    }
    
    /**
     *
     * @return the number of days in the month; automatically corrects for leap year
     */
    public int getDaysInMonth()
    {
        if (isLeapYear() && month == Month.feb)
            return month.getNormalDaysInMonth()+1;
        return month.getNormalDaysInMonth();
    }
    
    /**
     * 
     * 
     *
     * @return the number of days that have passed since October 15, 1582
     */
    private int lilian()
    {
    // CITE: DALE,JOYCE,WEEMS   OBJECT-ORIENTED DATA STRUCTURES
    // Returns the Lilian Day Number of this date.
    // Precondition: This Date is a valid date after 10/14/1582.
    //
    // Computes the number of days between 1/1/0 and this date as if no calendar
    // reforms took place, then subtracts 578,100 so that October 15, 1582 is day 1. 

        final int subDays = 578100;  // number of calculated days from 1/1/0 to 10/14/1582

        int numDays;

        // Add days in years.
        numDays = year * 365;

        // Add days in the months.
        if (month.getMonthNum() <= 2) 
            numDays = numDays + (month.getMonthNum() - 1) * 31;
        else 
            numDays = numDays + ((month.getMonthNum() - 1) * 31) - ((4 * (month.getMonthNum()-1) + 27) / 10);

        // Add days in the days.
        numDays = numDays + day;

        // Take care of leap years.
        numDays = numDays + (year / 4) - (year / 100) + (year / 400);

        // Handle special case of leap year but not yet leap day.
        if (month.getMonthNum() < 3) 
        {
            if ((year % 4) == 0)   numDays = numDays - 1;
            if ((year % 100) == 0) numDays = numDays + 1;
            if ((year % 400) == 0) numDays = numDays - 1;
        }

        // Subtract extra days up to 10/14/1582.
        numDays = numDays - subDays;
        return numDays;
    }

    /**
     * @return the day
     */
    public int getDay() 
    {
        return day;
    }

    /**
     * @return the year
     */
    public int getYear() 
    {
        return year;
    }

    /**
     * @return the month
     */
    public Month getMonth() 
    {
        return month;
    }
    
    /**
     * 
     * Changes the date to the next calendar day
     *
     */
    public void nextDay()
    {
        this.day +=1;
        if (!valid())
        {
            //if day is not valid, go to next month
            try {
                month = Month.getMonth(month.getMonthNum()+1);
            } catch (IllegalDateException ex) {
                //next month was not valid, go to next year
                year +=1;
                month = Month.jan;
            }
            day = 1;
        }
    }
    
    /**
     * 
     * Changes the date to the previous calendar day
     *
     */
    public void prevDay()
    {
        this.day -=1;
        if (day == 0)
        {
            // if day was not valid, go to previous month
            try 
            {
                month = Month.getMonth(month.getMonthNum()-1);
            } catch (IllegalDateException ex) 
            {
                //month was not valid, go to previous year
                year -=1;
                month = Month.dec;
            }
            day = this.getDaysInMonth();
        }
    }
    
    /**
     *
     * @return the total number of weeks spanned by a given month
     */
    public int getWeeksInMonth()
    {
        //returns the total number of weeks spanned by a given month
        int weeks = getFirstWeekdayOfMonth();
        weeks += getDaysInMonth()-1;
        weeks /= 7;
        weeks += 1;
        
        return weeks;
    }
    
    /**
     * 
     * Sunday = 0, Monday = 1 etc...
     *
     * @return the integer value of the first weekday included in the given month
     */
    public int getFirstWeekdayOfMonth()
    {
        //returns the integer value of the first weekday included in the given month
        MyDate firstOfMonth = null;
        try {
            firstOfMonth = new MyDate(month.getMonthNum(),1,year);
        } catch (IllegalDateException ex) {
            Logger.getLogger(MyDate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return firstOfMonth.getDayOfWeek().index;
    }
    
    @Override
    public String toString()
    {
        return month.getName()+ " " + day + ", " + year;
    }
    

    /**
     * Set the date equal to the current date
     *
     */
    public void setToToday()
    {
        day = (Calendar.getInstance().get(Calendar.DATE));
        try 
        {
            month = (Month.getMonth(Calendar.getInstance().get(Calendar.MONTH)+1));
        } catch (IllegalDateException ex) 
        {
            Logger.getLogger(SelectedDate.class.getName()).log(Level.SEVERE, null, ex);
        }
        year = (Calendar.getInstance().get(Calendar.YEAR));
    }

    @Override
    public int compareTo(MyDate o) 
    {
        if (this.year != o.year)
            return year - o.year;
        if (this.month != o.month)
            return month.compareTo(o.month);
        return this.day - o.day;
    }
    
    /**
     * 
     * lightweight, easy way to manually check equality of a date without having to handle all the exceptions
     *
     * @param month
     * @param day
     * @param year
     * @return
     */
    public boolean fieldsEqual(int month, int day, int year) 
    {
        if (month != this.month.getMonthNum())
            return false;
        if (day != this.day)
            return false;
        
        return year == this.year;
    }
    
    
    public boolean equals(MyDate other) {
        return this.fieldsEqual(other.month.monthNum, other.day, other.year);
    }
}
