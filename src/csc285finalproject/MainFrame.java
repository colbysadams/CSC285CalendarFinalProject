/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.Timer;

/**
 *
 * JTabbed pane that lets you cycle through the different calendar views
 * <p>
 * today button sets the selected date to today's date
 * <p>
 * the function of the previous/next buttons depends on the current view
 * <p>
 * Ex. current view = week, next button advances one week current view = month,
 * next button advances one month
 * <p>
 * @author colbysadams
 */
public class MainFrame extends JFrame implements Observer
{

    private SelectedDate selectedDate;
    private AbstractCalendarViewPanel weekPanel;
    private AbstractCalendarViewPanel monthPanel;
    private AbstractCalendarViewPanel dayPanel;
    private AbstractCalendarViewPanel yearPanel;
    private EventFactoryPanel eventPanel;
    private Box eventBox;
    private JButton nextButton;
    private JButton prevButton;
    private JButton todayButton;
    private JLabel dayOfWeek;
    private JLabel date;
    private JButton createEventButton, editEventButton;
    private JTabbedPane calendarView;
    private Timer timer;
    private ArrayList<CalendarEvent> todaysEvents;

    public MainFrame(String name)
    {
        super(name);

        this.selectedDate = SelectedDate.getInstance();
        this.selectedDate.setToToday();

        todaysEvents = MasterSchedule.getInstance().getDaysEvents(selectedDate);
        timer = new Timer(60000, new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                LocalTime currentTime;
                currentTime = LocalTime.now();
                for (int i = 0; i < todaysEvents.size(); ++i)
                {
                    MyTime eventTime = todaysEvents.get(i).getTimeObject();

                    if ()
                }
            }

        });

        createEventButton = new JButton("Create New Event");
        editEventButton = new JButton("Edit Selected Event");

        EventDetailPanel.addEditEventButton(editEventButton);

        //detailPanel = new EventDetailPanel();
        dayPanel = new DayPanel();
        weekPanel = new WeekPanel();
        monthPanel = new MonthPanel();
        yearPanel = new YearPanel();

        eventBox = Box.createHorizontalBox();
        eventBox.add(Box.createHorizontalGlue());
        eventBox.add(editEventButton);
        eventBox.add(createEventButton);

        eventPanel = new EventFactoryPanel(eventBox);

        calendarView = new JTabbedPane();

        selectedDate.addObserver(dayPanel);
        selectedDate.addObserver(weekPanel);
        selectedDate.addObserver(monthPanel);
        selectedDate.addObserver(yearPanel);
        //selectedDate.addObserver(eventPanel);
        selectedDate.addObserver(this);

        nextButton = new JButton("-->");
        prevButton = new JButton("<--");
        todayButton = new JButton("Today");

        nextButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {

                switch (calendarView.getSelectedIndex())
                {
                    case 0:
                        selectedDate.nextDay(1);
                        break;
                    case 1:
                        selectedDate.nextDay(7);
                        break;
                    case 2:
                        selectedDate.nextDay(selectedDate.getDaysInMonth() - selectedDate.getDay() + 1);
                        break;
                    case 3:

                        selectedDate.nextYear();
                        break;
                }
                //System.out.println("action next:" + selectedDate);
            }
        });

        prevButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                switch (calendarView.getSelectedIndex())
                {
                    case 0:
                        selectedDate.prevDay(1);
                        break;
                    case 1:
                        selectedDate.prevDay(7);
                        break;
                    case 2:
                        selectedDate.prevDay(selectedDate.getDay());
                        break;
                    case 3:
                        selectedDate.prevYear();
                        break;
                }
                //System.out.println("action prev:" + selectedDate);

            }

        });

        todayButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                selectedDate.setToToday();
                //System.out.println("action today:" + selectedDate);
            }

        });

        createEventButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                eventPanel.inputEvent();
                eventPanel.setVisible(true);
                EventDetailPanel.makeVisible(false);
                eventBox.setVisible(false);
            }

        });

        editEventButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (EventDetailPanel.getSelectedEvent() == null)
                    return;
                eventPanel.inputEvent(EventDetailPanel.getSelectedEvent());

                eventPanel.setVisible(true);
                EventDetailPanel.makeVisible(false);
                eventBox.setVisible(false);
            }
        });

        //slap all them views in the tabbed pane
        calendarView.add("Day", dayPanel);
        calendarView.add("Week", weekPanel);
        calendarView.add("Month", monthPanel);
        calendarView.add("Year", yearPanel);

        Box labelBox = new Box(BoxLayout.X_AXIS);
        labelBox.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        //day of week label
        dayOfWeek = new JLabel(SelectedDate.getInstance().getDayOfWeek().name);
        dayOfWeek.setFont(dayOfWeek.getFont().deriveFont(24.0f));
        dayOfWeek.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        labelBox.add(dayOfWeek);

        //date label
        date = new JLabel("  " + SelectedDate.getInstance().toString() + "   ");

        date.setFont(date.getFont().deriveFont(18.0f));
        date.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        labelBox.add(date);

        //box holding all them buttons
        Box changeBox = Box.createHorizontalBox();
        changeBox.setAlignmentX(Component.RIGHT_ALIGNMENT);

        changeBox.add(prevButton, BorderLayout.NORTH);
        changeBox.add(todayButton, BorderLayout.NORTH);
        changeBox.add(nextButton, BorderLayout.NORTH);
        changeBox.add(Box.createHorizontalGlue());
        changeBox.add(labelBox);
        //changeBox.add(eventButton);
        //this.add(eventButton,BorderLayout.SOUTH);
        this.getContentPane().add(changeBox, BorderLayout.NORTH);
        this.getContentPane().add(calendarView);
        this.getContentPane().add(eventPanel, BorderLayout.EAST);
        this.getContentPane().add(eventBox, BorderLayout.SOUTH);
        eventPanel.setVisible(false);
        calendarView.setSelectedComponent(monthPanel);
    }

    @Override
    public void update()
    {
        //updates text at top of screen when selected date is changed
        dayOfWeek.setText(SelectedDate.getInstance().getDayOfWeek().name);
        date.setText("  " + SelectedDate.getInstance().toString() + "   ");

        repaint();
    }
}
