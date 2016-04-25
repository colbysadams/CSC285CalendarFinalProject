/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author colbysadams
 */
public class EventFactoryPanel extends JPanel implements ActionListener
{

    private static int offsetX = 5;
    private JButton saveEventButton, cancelButton, deleteButton;
    private JTextField eventNameField, eventLocationField;
    private JTextArea eventDescriptionArea;
    private JRadioButton[] eventTypeRadio;
    private ButtonGroup eventTypes;
    private JPanel subPanel;
    private Box mainPanelButton;
    private JComboBox repeatComboBox;
    private JPanel timePanel;
    private JPanel reminderPanel;
    private Box timeBox;
    private Box reminderBox;
    private JCheckBox hasTimeBox;
    private JCheckBox setReminder;
    private JComboBox hourCombo, minuteCombo;
    private JComboBox hourReminderCombo, minuteReminderCombo;
    private ArrayList<String> clockHourStrings, clockMinuteStrings, reminderHourStrings;
    private int textFieldSize = 10;
    private CalendarEvent event;
    private MyDate eventDate;
    private boolean newEvent;
    private JCheckBox amCheck;

    public EventFactoryPanel(Box panelButton)
    {
        super();

        this.mainPanelButton = panelButton;

        clockHourStrings = new ArrayList(12);
        clockMinuteStrings = new ArrayList(60);
        reminderHourStrings = new ArrayList(12);
        String s = "0";
        for (int i = 0; i < 12; ++i)
        {
            clockHourStrings.add(String.valueOf(i + 1));
            reminderHourStrings.add(String.valueOf(i));
        }
        newEvent = false;

        for (int i = 0; i < 60; ++i)
        {
            clockMinuteStrings.add(s + String.valueOf(i));
            if (i == 9)
                s = "";
        }

        hourCombo = new JComboBox(clockHourStrings.toArray());
        hourReminderCombo = new JComboBox(reminderHourStrings.toArray());
        minuteCombo = new JComboBox(clockMinuteStrings.toArray());
        minuteReminderCombo = new JComboBox(clockMinuteStrings.toArray());

        hasTimeBox = new JCheckBox("All Day Event");
        setReminder = new JCheckBox("Set Reminder");
        hasTimeBox.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (hasTimeBox.isSelected())
                    timePanel.setVisible(false);
                else
                    timePanel.setVisible(true);

            }
        });

        setReminder.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (setReminder.isSelected())
                    reminderPanel.setVisible(true);
                else
                    reminderPanel.setVisible(false);

            }
        });

        Box allDayBox = Box.createHorizontalBox();
        Box reminderSpaceBox = Box.createHorizontalBox();
        allDayBox.add(hasTimeBox);
        reminderSpaceBox.add(setReminder);
        allDayBox.add(Box.createHorizontalGlue());
        reminderSpaceBox.add(Box.createHorizontalGlue());
        amCheck = new JCheckBox("AM");

        timePanel = new JPanel(new GridLayout(0, 1));
        reminderPanel = new JPanel(new GridLayout(0, 1));

        timeBox = Box.createHorizontalBox();
        reminderBox = Box.createHorizontalBox();
        timeBox.add(Box.createHorizontalGlue());
        reminderBox.add(Box.createHorizontalGlue());
        timeBox.add(hourCombo);
        reminderBox.add(hourReminderCombo);
        timeBox.add(new JLabel(":"));
        reminderBox.add(new JLabel(":"));
        timeBox.add(minuteCombo);
        reminderBox.add(minuteReminderCombo);
        timeBox.add(Box.createHorizontalGlue());
        reminderBox.add(Box.createHorizontalGlue());

        timePanel.add(timeBox);
        reminderPanel.add(reminderBox);
        timePanel.add(amCheck);
        subPanel = new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
        subPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        saveEventButton = new JButton("Save");
        saveEventButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {

                String eventName = eventNameField.getText();
                if (eventName.equals(""))
                {
                    eventNameField.requestFocusInWindow();
                    return;
                }
                EventType eventType = null;
                for (int i = 0; i < eventTypeRadio.length; ++i)
                    if (eventTypeRadio[i].isSelected())
                    {
                        eventType = EventType.get(i);
                        break;
                    }
                event.setName(eventName);
                event.setEventType(eventType);
                if (event.getRepeating() != repeatComboBox.getSelectedIndex() && !newEvent)
                {
                    MasterSchedule.getInstance().removeFromSchedule(event);
                    newEvent = true;
                }
                event.setRepeating(repeatComboBox.getSelectedIndex());

                if (!eventLocationField.getText().equals(""))
                    event.setLocation(eventLocationField.getText());
                if (!eventDescriptionArea.getText().equals(""))
                    event.setDescription(eventDescriptionArea.getText());

                if (!hasTimeBox.isSelected())
                    if (amCheck.isSelected())
                        event.setTime(new MyTime(LocalTime.of((hourCombo.getSelectedIndex() + 1) % 12,
                                                              minuteCombo.getSelectedIndex())));
                    else if (hourCombo.getSelectedIndex() == 11)
                        event.setTime(new MyTime(LocalTime.of(hourCombo.getSelectedIndex() + 1,
                                                              minuteCombo.getSelectedIndex())));
                    else
                        event.setTime(new MyTime(LocalTime.of(hourCombo.getSelectedIndex() + 13,
                                                              minuteCombo.getSelectedIndex())));

                if (setReminder.isSelected())
                    event.getTimeObject().setReminder(hourReminderCombo.getSelectedIndex(),
                                                      minuteReminderCombo.getSelectedIndex());
                else
                    event.getTimeObject().setReminder(-1, -1);

                if (newEvent)
                {
                    MasterSchedule.getInstance().addEventToSchedule(SelectedDate.getInstance(), event);
                    newEvent = false;
                }
                cancelButton.doClick();
                SelectedDate.getInstance().notifyObservers();
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);

        deleteButton = new JButton("DELETE EVENT");
        deleteButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                MasterSchedule.getInstance().removeFromSchedule(event);
                SelectedDate.getInstance().notifyObservers();
                cancelButton.doClick();
            }
        });

        eventNameField = new JTextField(textFieldSize);
        eventLocationField = new JTextField(textFieldSize);

        repeatComboBox = new JComboBox(MasterSchedule.repeatStrings);

        eventTypes = new ButtonGroup();
        eventTypeRadio = new JRadioButton[]
        {
            new JRadioButton(EventType.work.TYPE),
            new JRadioButton(EventType.family.TYPE),
            new JRadioButton(EventType.school.TYPE),
            new JRadioButton(EventType.social.TYPE),
            new JRadioButton(EventType.holiday.TYPE),
            new JRadioButton(EventType.other.TYPE)
        };

        eventTypeRadio[0].setSelected(true);

        eventDescriptionArea = new JTextArea(5, 5);
        eventDescriptionArea.setWrapStyleWord(true);
        eventDescriptionArea.setLineWrap(true);

        JScrollPane descriptionPane = new JScrollPane();
        descriptionPane.setViewportView(eventDescriptionArea);

        Box buttonBox = Box.createHorizontalBox();

        subPanel.add(createLabel("Event Name:"));
        subPanel.add(eventNameField);
        subPanel.add(allDayBox);
        subPanel.add(timePanel);
        subPanel.add(reminderSpaceBox);
        subPanel.add(reminderPanel);
        subPanel.add(createLabel("Location:"));
        subPanel.add(eventLocationField);
        subPanel.add(createLabel("Description:"));
        subPanel.add(descriptionPane);
        subPanel.add(createLabel("Repeats:"));
        subPanel.add(repeatComboBox);
        subPanel.add(createLabel("Type of Event:"));
        JPanel radioPanel = new JPanel(new GridLayout(3, 2));
        for (int i = 0; i < eventTypeRadio.length; ++i)
        {
            eventTypeRadio[i].setActionCommand(String.valueOf(i));
            eventTypes.add(eventTypeRadio[i]);
            radioPanel.add(eventTypeRadio[i]);
        }
        hasTimeBox.doClick();
        reminderPanel.setVisible(false);
        subPanel.add(radioPanel);
        buttonBox.add(saveEventButton);
        buttonBox.add(cancelButton);
        subPanel.add(buttonBox);
        Box deleteBox = Box.createHorizontalBox();
        deleteBox.add(deleteButton);
        deleteBox.add(Box.createHorizontalGlue());
        subPanel.add(Box.createVerticalStrut(50));
        subPanel.add(deleteBox);
        this.add(subPanel);

    }

    /**
     * 
     * @param label
     * @return the labelBox
     */
    public Component createLabel(String label)
    {
        Box labelBox = Box.createHorizontalBox();
        labelBox.add(new JLabel(label));
        labelBox.add(Box.createHorizontalGlue());
        return labelBox;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        this.setVisible(false);
        mainPanelButton.setVisible(true);
        eventNameField.setText("");
        eventLocationField.setText("");
        eventDescriptionArea.setText("");
        repeatComboBox.setSelectedIndex(0);
        eventTypeRadio[0].setSelected(true);
        if (!hasTimeBox.isSelected())
            hasTimeBox.doClick();
        else
        {
            hourCombo.setSelectedIndex(0);
            minuteCombo.setSelectedIndex(0);
            amCheck.setSelected(false);
        }
        event = null;
        newEvent = false;
        EventDetailPanel.makeVisible(true);

    }

    
    public void inputEvent()
    {
        newEvent = true;

        inputEvent(new CalendarEvent());

    }

    public void inputEvent(CalendarEvent event)
    {
        this.eventDate = SelectedDate.getInstance();
        this.event = event;
        eventNameField.setText(this.event.getName());
        eventLocationField.setText(this.event.getLocation());
        eventDescriptionArea.setText(this.event.getDescription());
        repeatComboBox.setSelectedIndex(event.getRepeating());
        eventTypeRadio[event.getEventType().index].setSelected(true);
        if (newEvent)
            deleteButton.setVisible(false);

        else
            deleteButton.setVisible(true);
        if (this.event.getTimeObject().getTime() == null)
        {

            if (!hasTimeBox.isSelected())
                hasTimeBox.doClick();
        } else
        {
            if (hasTimeBox.isSelected())
                hasTimeBox.doClick();
            hourCombo.setSelectedIndex((event.getTimeObject().getTime().getHour() - 1) % 12);
            minuteCombo.setSelectedIndex(event.getTimeObject().getTime().getMinute());

            if (event.getTimeObject().getTime().getHour() < 12)
                amCheck.setSelected(true);
        }
        if (this.event.getTimeObject().hasReminder())
        {
            if (!setReminder.isSelected())
                setReminder.doClick();
            hourReminderCombo.setSelectedIndex(event.getTimeObject().getHoursBefore());
            minuteReminderCombo.setSelectedIndex(event.getTimeObject().getMinutesBefore());

        } else if (setReminder.isSelected())
        {
            setReminder.doClick();
            hourReminderCombo.setSelectedIndex(0);
            minuteReminderCombo.setSelectedIndex(0);
        }

    }
}