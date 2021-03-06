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
import javax.swing.JOptionPane;
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
    private MyDate today;
    private LocalTime currentTime;
    private ObserverPanel weekPanel;
    private ObserverPanel monthPanel;
    private ObserverPanel dayPanel;
    private ObserverPanel yearPanel;
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
    private ArrayList<CalendarEvent> alreadyRemindedToday, alreadyRemindedTomorrow;
    //private JOptionPane reminderPanel;

    /**
     *
     * Builds the main frame which holds a tabbed pane for each calendar view
     * and event editing panels
     *
     * @param name
     */
    public MainFrame(String name)
    {
        super(name);

        this.selectedDate = SelectedDate.getInstance();
        this.selectedDate.setToToday();

        //reminderPanel = new JOptionPane();
        today = new MyDate();

        alreadyRemindedToday = new ArrayList();
        alreadyRemindedTomorrow = new ArrayList();
        timer = new Timer(10000, new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {

                if (!today.equals(new MyDate()))
                {
                    today.setToToday();
                    alreadyRemindedToday.clear();
                    alreadyRemindedTomorrow.clear();
                }

                currentTime = LocalTime.now();

                //get all of today's events
                ArrayList<CalendarEvent> todaysEvents = MasterSchedule.getInstance().getTodaysEventReminders(today);

                for (int i = 0; i < todaysEvents.size(); ++i)
                {
                    if ((alreadyRemindedToday.contains(todaysEvents.get(i)))
                            || (!todaysEvents.get(i).hasReminder()))
                    {
                        continue;
                    }

                    MyTime eventTime = todaysEvents.get(i).getTimeObject();

                    if (currentTime.isAfter(eventTime.getReminder()))
                    {
                        JOptionPane.showMessageDialog(calendarView,
                                                      todaysEvents.get(i).getShortLabel(),
                                                      "EVENT REMINDER",
                                                      JOptionPane.PLAIN_MESSAGE);

                        alreadyRemindedToday.add(todaysEvents.get(i));
                    }

                }
                // at 5:30 pm, remind user about all day events occuring tomorrow
                if ((currentTime.getHour() == 17) && (currentTime.getMinute() == 30))
                {
                    todaysEvents = MasterSchedule.getInstance().getTomorrowsEventReminders(today);
                    for (int i = 0; i < todaysEvents.size(); ++i)
                    {
                        if ((alreadyRemindedTomorrow.contains(todaysEvents.get(i)))
                                || (!todaysEvents.get(i).hasReminder()))
                        {
                            continue;
                        }

                        MyTime eventTime = todaysEvents.get(i).getTimeObject();

                        if (currentTime.isAfter(eventTime.getReminder()))
                        {

                            JOptionPane.showMessageDialog(calendarView,
                                                          todaysEvents.get(i).getShortLabel(),
                                                          "REMINDER FOR TOMORROW",
                                                          JOptionPane.PLAIN_MESSAGE);
                            alreadyRemindedTomorrow.add(todaysEvents.get(i));
                        }
                    }
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

            /**
             *
             * jump forward the appropriate number of days when the next
             * button is clicked
             * <p>
             * @param e
             */
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

            /**
             *
             * Jump backward the appropriate number of days when the previous
             * button
             * is clicked
             *
             * @param e
             */
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
            }
        });

        /*
         * set the selected date to today
         */
        todayButton.addActionListener(new ActionListener()
        {

            /**
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e)
            {
                selectedDate.setToToday();
                //System.out.println("action today:" + selectedDate);
            }

        });

        /*
         * bring up eventFactoryPanel
         */
        createEventButton.addActionListener(new ActionListener()
        {

            /**
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e)
            {
                eventPanel.inputEvent();
                eventPanel.setVisible(true);
                EventDetailPanel.makeVisible(false);
                eventBox.setVisible(false);
            }

        });

        /*
         * bring up event factory panel and input selected event
         */
        editEventButton.addActionListener(new ActionListener()
        {

            /**
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (EventDetailPanel.getSelectedEvent() == null)
                {
                    return;
                }
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

        this.getContentPane().add(changeBox, BorderLayout.NORTH);
        this.getContentPane().add(calendarView);
        this.getContentPane().add(eventPanel, BorderLayout.EAST);
        this.getContentPane().add(eventBox, BorderLayout.SOUTH);
        eventPanel.setVisible(false);
        calendarView.setSelectedComponent(monthPanel);
        timer.start();
    }

    /**
     * updates text at top of screen when selected date is changed
     */
    @Override
    public void update()
    {
        //
        dayOfWeek.setText(SelectedDate.getInstance().getDayOfWeek().name);
        date.setText("  " + SelectedDate.getInstance().toString() + "   ");

        repaint();
    }
}
