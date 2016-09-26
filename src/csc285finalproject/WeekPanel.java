/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

/**
 *
 * Panel representing one calendar week
 *
 * @author colbysadams
 */
public class WeekPanel extends WeekMonthParent
{

    public WeekPanel()
    {
        super();
    }

    /**
     *
     * @return the daysDisplayed
     */
    @Override
    public int getDaysDisplayed()
    {
        return 7;
    }

    /**
     *
     * @return the buffer
     */
    @Override
    public int getBuffer()
    {
        if (SelectedDate.getInstance().getFirstWeekdayOfMonth()
                + SelectedDate.getInstance().getDay() > 7)
        {
            return 0;
        }
        return SelectedDate.getInstance().getFirstWeekdayOfMonth();
    }

    /**
     *
     * @return the dateOffset
     */
    @Override
    public int getDateOffset()
    {
        int i;
        if (getBuffer() == 0)
        {
            i = SelectedDate.getInstance().getDay()
                    - SelectedDate.getInstance().getDayOfWeek().index - 1;
        } else
        {
            i = 0;
        }

        return i;
    }
}
