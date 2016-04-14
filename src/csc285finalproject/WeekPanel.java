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
public class WeekPanel extends WeekMonthParent
{

    public WeekPanel()
    {
        super();

    }

    @Override
    public int getDaysDisplayed()
    {
        return 7;
    }

    @Override
    public int getBuffer()
    {
        if (SelectedDate.getInstance().getFirstWeekdayOfMonth()
                + SelectedDate.getInstance().getDay() > 7)
            return 0;
        return SelectedDate.getInstance().getFirstWeekdayOfMonth();
    }

    @Override
    public int getDateOffset()
    {
        int i;
        if (getBuffer() == 0)
            i = SelectedDate.getInstance().getDay()
                    - SelectedDate.getInstance().getDayOfWeek().index - 1;
        else
            i = 0;

        return i;
    }

}
