/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

/**
 * 
 * Enum the represents each month of the year
 *
 * @author colbysadams
 */


public enum Month 
{
    jan("January",1,31),
    feb("February",2,28),
    mar("March",3,31),
    apr("April",4,30),
    may("May",5,31),
    jun("June",6,30),
    jul("July",7,31),
    aug("August",8,31),
    sep("September",9,30),
    oct("October",10,31),
    nov("November",11,30),
    dec("December",12,31);
    
    final String name;
    final int monthNum;
    private final int daysInMonth;
    
    Month(String s, int num, int days) 
    {
        name = s;
        monthNum = num;
        daysInMonth = days;
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
    
    /**
     * 
     * gets the month according to its position
     * January = 1
     * February = 2...
     *
     * @param i integer representing month to be returned
     * @return the month object at position i in the calendar
     * @throws IllegalDateException i was not in between 1 and 12 inclusive
     */
    public static Month getMonth(int i) throws IllegalDateException
    {
        switch(i)
        {
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
                
        }
        throw new IllegalDateException("Month Index is out of bounds");
    }
    
}
