/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * Template model for displaying different views of the calendar
 * <p>
 * @author colbysadams
 */
public abstract class AbstractCalendarViewPanel extends ObserverPanel
{

    protected boolean shortLabels;
    //the currently selected date to display a view for
    private MyDate selectedDate;
    private JPanel squaresPanel, subPanel;
    private MasterSchedule schedule;
    private int pWidth, pHeight;

    /**
     *
     * Constructor used for making the panel for each month of the year
     * when viewing the year panel
     *
     * @param date
     * @param shortLabels
     */
    public AbstractCalendarViewPanel(MyDate date, boolean shortLabels)
    {

        super();

        pWidth = 1100;
        pHeight = 600;

        this.shortLabels = shortLabels;

        this.setLayout(new BorderLayout());

        subPanel = new JPanel();
        subPanel.setLayout(new BorderLayout());

        this.setPreferredSize(new Dimension(pWidth, pHeight));

        selectedDate = date;
        this.schedule = MasterSchedule.getInstance();

        squaresPanel = new JPanel(new GridLayout(0, getRowSize(), 5, 5));
        subPanel.add(squaresPanel);

        this.add(subPanel, BorderLayout.CENTER);
        if (!shortLabels)
        {
            EventDetailPanel detailPanel = new EventDetailPanel();

            this.add(detailPanel, BorderLayout.EAST);
        }
    }

    /**
     * Constructor for creating all other panels
     * <p>
     * <p>
     */
    public AbstractCalendarViewPanel()
    {
        this(SelectedDate.getInstance(), false);

        addLabels();
        this.addDateSquares();

    }

    /**
     *
     * Used to build the subpanel holding all the dateSquares representing
     * different dates in the range of the view
     * <p>
     */
    public void addDateSquares()
    {

        squaresPanel.removeAll();

        MyDate prevMonth = null;

        try
        {
            prevMonth = new MyDate(selectedDate.getMonthInt(), 1, selectedDate.getYear());
        }
        catch (IllegalDateException ex)
        {

        }
        //
        for (int i = 0; i < getBuffer(); ++i)
        {
            prevMonth.prevDay();
        }

        AbstractDateSquare dateSquare;
        ArrayList<CalendarEvent> events = new ArrayList();
        //displays any days from the previous month that are in the current view
        for (int i = 0; i < getBuffer(); ++i)
        {
            dateSquare = new DateSquare(prevMonth.getMonthInt(),
                                        prevMonth.getDay(),
                                        prevMonth.getYear(),
                                        Color.lightGray);

            events = schedule.getDaysEvents(prevMonth);

            for (CalendarEvent event : events)
            {
                dateSquare = decorateDateSquare(event, dateSquare);
            }

            squaresPanel.add(dateSquare);
            prevMonth.nextDay();
        }

        int lastDay = getBuffer();

        // displays all the days belonging to the current month in the view
        for (int i = 1; i <= getDaysDisplayed(); ++i)
        {
            //when creating week panels, make sure you only create 7 days
            if (i + getDateOffset() > selectedDate.getDaysInMonth()
                    || ((i + getBuffer() > getDaysDisplayed() && getDaysDisplayed() == 7)))
            {
                break;
            }
            dateSquare = new DateSquare(selectedDate.getMonthInt(),
                                        i + getDateOffset(),
                                        selectedDate.getYear(),
                                        Color.white);

            events = schedule.getDaysEvents(dateSquare.getDate());

            for (CalendarEvent event : events)
            {
                dateSquare = decorateDateSquare(event, dateSquare);
            }

            squaresPanel.add(dateSquare);
            lastDay++;
        }

        MyDate nextMonth = null;
        try
        {
            nextMonth = new MyDate(selectedDate.getMonthInt(),
                                   selectedDate.getDaysInMonth(),
                                   selectedDate.getYear());
        }
        catch (IllegalDateException ex)
        {

        }
        nextMonth.nextDay();

        //if we have reached the end of the month, but not the end of the week
        //fill in days from next month
        while (lastDay % getRowSize() != 0)
        {
            dateSquare = new DateSquare(nextMonth.getMonthInt(),
                                        nextMonth.getDay(),
                                        nextMonth.getYear(), Color.lightGray);

            events = schedule.getDaysEvents(nextMonth);

            for (CalendarEvent event : events)
            {
                dateSquare = decorateDateSquare(event, dateSquare);
            }

            squaresPanel.add(dateSquare);
            lastDay++;
            nextMonth.nextDay();
        }
    }

    /**
     *
     * decides which decorator to use, depending on the calendar view
     * <p>
     * @param event
     * @param dateSquare
     *                   <p>
     * @return the decorator
     */
    public AbstractDateSquare decorateDateSquare(CalendarEvent event, AbstractDateSquare dateSquare)
    {
        if (shortLabels)
        {
            return new YearSquareDecorator(event, dateSquare);
        }
        if (this.getRowSize() == 1)
        {
            return new DaySquareDecorator(event, dateSquare);
        }
        return new DateSquareDecorator(event, dateSquare);
    }

    /*
     * the following methods can be used to hook into and modify the above
     * methods
     * to produce the various panel views. (i.e. day, week, month, year)
     */
    /**
     *
     * Can be overridden to hook into constructor and add labels
     */
    public void addLabels()
    {

    }

    /**
     *
     * <p>
     *
     * @return the number of days that will be displayed by the Calendar View
     */
    public abstract int getDaysDisplayed();
//

    /**
     *
     * <p>
     *
     * @return the number of days in each row of Calendar View
     */
    public abstract int getRowSize();

//    
    /**
     *
     *
     * @return number of days belonging to last month to be displayed
     */
    public abstract int getBuffer();
//

    /**
     *
     * The date of the first (valid) day to be displayed in the calendar view.
     * <p>
     * @return number to add to the date of the first displayed day in white
     */
    public abstract int getDateOffset();
//

    @Override
    public void update()
    {
        //shortlabels is only true for year view. each month of a year view holds a diff
        //selected date
        if (shortLabels)
        {
            try
            {
                setSelectedDate(new MyDate(selectedDate.getMonthInt(),
                                           1, SelectedDate.getInstance().getYear()));
            }
            catch (IllegalDateException ex)
            {
            }
        } else
        //for all other views, just use the singleton SelectedDate
        {
            setSelectedDate(SelectedDate.getInstance());
        }

        this.addDateSquares();
        this.revalidate();
        this.repaint();

    }

    /**
     *
     * @return the subPanel
     */
    protected JPanel getSubPanel()
    {
        return subPanel;
    }

    /**
     *
     * @param date
     */
    private void setSelectedDate(MyDate date)
    {
        selectedDate = date;
    }

    /**
     *
     * @return the date selected by the Calendar view
     */
    public MyDate getSelectedDate()
    {
        return selectedDate;
    }
}
