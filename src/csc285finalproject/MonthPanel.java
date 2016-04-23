/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

/**
 *
 * @author colbysadams
 */
public class MonthPanel extends WeekMonthParent
{

    public MonthPanel()
    {
        super();
    }

    /**
     * 
     * @param date
     * @param shortLabels 
     */
    public MonthPanel(MyDate date, boolean shortLabels)
    {
        super(date, shortLabels);
    }

    /**
     * 
     * @return the daysInMonth
     */
    @Override
    public int getDaysDisplayed()
    {
        return getSelectedDate().getDaysInMonth();
    }

    /**
     * 
     * @return firstWeekdayOfMonth
     */
    @Override
    public int getBuffer()
    {
        return getSelectedDate().getFirstWeekdayOfMonth();
    }

    @Override
    public int getDateOffset()
    {
        return 0;
    }
}