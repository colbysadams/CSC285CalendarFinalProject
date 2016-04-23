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

        month = Month.getMonth((Calendar.getInstance().get(Calendar.MONTH) + 1));

        day = Calendar.getInstance().get(Calendar.DATE);
        year = Calendar.getInstance().get(Calendar.YEAR);

    }

    /**
     *
     * @param month
     * @param day
     * @param year  <p>
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
     * Calculates the day of the week of the date
     * <p>
     * @return the day of the week
     */
    public Weekday getDayOfWeek()
    //Returns true if date is valid
    {
        //first day of gregorian Calendar was a friday
        int daysSince = 4 + lilian();
        //calculate number of days since then mod 7
        int weekdayNum = daysSince % 7;

        return Weekday.getWeekday(weekdayNum);
    }

    /**
     *
     * @return the number of days in the month; automatically corrects for leap year
     */
    public int getDaysInMonth()
    {
        if (isLeapYear() && month == Month.feb)
            return month.getNormalDaysInMonth() + 1;
        return month.getNormalDaysInMonth();
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
    public String getMonthString()
    {
        return month.name;
    }

    /**
     * 
     * @return the monthNum
     */
    public int getMonthInt()
    {
        return month.monthNum;
    }

    /**
     * 
     * @param calendarNum
     * @return the Month
     */
    public static String getMonth(int calendarNum)
    {
        return Month.getMonth(calendarNum).name;
    }

    /**
     *
     * Changes the date to the next calendar day
     * <p>
     */
    public void nextDay()
    {
        this.day += 1;
        if (!valid())
        {
            //if day is not valid, go to next month
            if (month.getMonthNum() + 1 == 13)
                year++;
            month = Month.getMonth(month.getMonthNum() + 1);

            day = 1;
        }
    }

    /**
     *
     * Changes the date to the previous calendar day
     * <p>
     */
    public void prevDay()
    {
        this.day -= 1;
        if (day == 0)
        {
            // if day was not valid, go to previous month
            if (month.getMonthNum() - 1 == 0)
                year--;
            month = Month.getMonth(month.getMonthNum() - 1);
            //month was not valid, go to previous year

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
        weeks += getDaysInMonth() - 1;
        weeks /= 7;
        weeks += 1;

        return weeks;
    }

    /**
     *
     * Sunday = 0, Monday = 1 etc...
     * <p>
     * @return the integer value of the first weekday included in the given month
     */
    public int getFirstWeekdayOfMonth()
    {
        //returns the integer value of the first weekday included in the given month
        MyDate firstOfMonth = null;
        try
        {
            firstOfMonth = new MyDate(month.getMonthNum(), 1, year);
        }
        catch (IllegalDateException ex)
        {
            Logger.getLogger(MyDate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return firstOfMonth.getDayOfWeek().index;
    }

    /**
     * 
     * @return string
     */
    @Override
    public String toString()
    {
        return month.getName() + " " + day + ", " + year;
    }

    /**
     * Set the date equal to the current date
     * <p>
     */
    public void setToToday()
    {
        day = (Calendar.getInstance().get(Calendar.DATE));

        month = (Month.getMonth(Calendar.getInstance().get(Calendar.MONTH) + 1));

        year = (Calendar.getInstance().get(Calendar.YEAR));
    }

    /**
     * 
     * @param o
     * @return the date
     */
    @Override
    public int compareTo(MyDate o)
    {
        if (this.year != o.year)
            return year - o.year;
        if (this.month != o.month)
            return month.compareTo(o.month);
        return this.day - o.day;
    }

    private boolean fieldsEqual(MyDate other)
    {
        {
            if (other.month.getMonthNum() != this.month.getMonthNum())
                return false;
            if (other.day != this.day)
                return false;

            return other.year == this.year;
        }
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == null)
        {
            System.out.println("null");
            return false;
        }
        if (this.getClass() != other.getClass() && (this.getClass() != other.getClass().getSuperclass()))
        {
            System.out.println("classes");
            return false;
        }

        MyDate otherDate = (MyDate) other;
        return fieldsEqual(otherDate);

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
        if (year == 1582)
        {
            if (month.compareTo(Month.oct) < 0)
                return false;
            if (month == Month.oct && day < 15)
                return false;
        }

        // if it is a february and leap year, add one day to daysPerMonth then compare
        if (month == Month.feb)
            //returns true if leap year
            if (isLeapYear())
                if (day > 0 && day <= month.getNormalDaysInMonth() + 1)
                    return true;
        //not a leap year, check that day is valid and return
        return (day > 0 && day <= month.getNormalDaysInMonth());
    }

    /**
     *
     * <p>
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
            numDays = numDays + ((month.getMonthNum() - 1) * 31) - ((4 * (month.getMonthNum() - 1) + 27) / 10);

        // Add days in the days.
        numDays = numDays + day;

        // Take care of leap years.
        numDays = numDays + (year / 4) - (year / 100) + (year / 400);

        // Handle special case of leap year but not yet leap day.
        if (month.getMonthNum() < 3)
        {
            if ((year % 4) == 0)
                numDays = numDays - 1;
            if ((year % 100) == 0)
                numDays = numDays + 1;
            if ((year % 400) == 0)
                numDays = numDays - 1;
        }

        // Subtract extra days up to 10/14/1582.
        numDays = numDays - subDays;
        return numDays;
    }

    private enum Month
    {

        jan("January", 1, 31),
        feb("February", 2, 28),
        mar("March", 3, 31),
        apr("April", 4, 30),
        may("May", 5, 31),
        jun("June", 6, 30),
        jul("July", 7, 31),
        aug("August", 8, 31),
        sep("September", 9, 30),
        oct("October", 10, 31),
        nov("November", 11, 30),
        dec("December", 12, 31);
        final String name;
        final int monthNum;
        private final int daysInMonth;

        /**
         * 
         * @param s
         * @param num
         * @param days 
         */
        Month(String s, int num, int days)
        {
            name = s;
            monthNum = num;
            daysInMonth = days;
        }

        /**
         *
         * gets the month according to its position January = 1 February = 2...
         * <p>
         * @param i integer representing month to be returned
         * <p>
         * @return the month object at position i in the calendar
         * <p>
         * @throws IllegalDateException i was not in between 1 and 12 inclusive
         */
        public static Month getMonth(int i)
        {
            switch (i)
            {
                case 0:

                    return Month.dec;
                case 1:
                    return Month.jan;
                case 2:
                    return Month.feb;
                case 3:
                    return Month.mar;
                case 4:
                    return Month.apr;
                case 5:
                    return Month.may;
                case 6:
                    return Month.jun;
                case 7:
                    return Month.jul;
                case 8:
                    return Month.aug;
                case 9:
                    return Month.sep;
                case 10:
                    return Month.oct;
                case 11:
                    return Month.nov;
                case 12:
                    return Month.dec;
                case 13:
                    return Month.jan;

            }
            return null;

        }

        /**
         * @return the name
         */
        public String getName()
        {
            return name;
        }

        /**
         * @return the monthNum
         */
        public int getMonthNum()
        {
            return monthNum;
        }

        /**
         * @return the daysInMonth
         */
        public int getNormalDaysInMonth()
        {
            return daysInMonth;
        }
    }
}