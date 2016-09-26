/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

/**
 *
 * panel that displays only one day at a time
 *
 * @author colbysadams
 */
public class DayPanel extends AbstractCalendarViewPanel
{

    public DayPanel()
    {
        super();
        update();
    }

    @Override
    public int getDaysDisplayed()
    {
        return 1;
    }

    @Override
    public int getRowSize()
    {
        return 1;
    }

    @Override
    public int getBuffer()
    {
        return 0;
    }

    @Override
    public int getDateOffset()
    {
        return SelectedDate.getInstance().getDay() - 1;
    }
}
