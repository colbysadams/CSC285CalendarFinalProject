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
public class EventPanel extends JPanel implements Observer, ActionListener {

    private static int offsetX = 5;

    private JButton saveEventButton, cancelButton, deleteButton;
    private JTextField eventNameField, eventLocationField;
    private JTextArea eventDescriptionArea;

    private JRadioButton[] eventTypeRadio;
    private ButtonGroup eventTypes;

    private JPanel subPanel;

    private Box mainPanelButton;

    private String[] repeatBoxStrings = new String[]{"Never", "Yearly", "Monthly", "Weekly"};
    
    private JComboBox repeatComboBox;
               
    private JPanel timePanel;
    private Box timeBox;

    //private JTextField hourTextField, minuteTextField;
    
    private JCheckBox hasTimeBox;
    
    private JComboBox hourCombo, minuteCombo;
    
    private ArrayList<String> clockHourStrings,clockMinuteStrings;
    
    private int textFieldSize = 10;
    
    private CalendarEvent event;
    private MyDate eventDate;
    
    private boolean newEvent;

    public EventPanel(Box panelButton) {
        super();

        
        this.mainPanelButton = panelButton;

        clockHourStrings = new ArrayList(12);
        clockMinuteStrings = new ArrayList(60);
        String s = "0";
        for (int i = 0; i < 12; ++i) {
            clockHourStrings.add(String.valueOf(i+1));
            
                
        }
        newEvent = false;
        
        for (int i = 0; i < 60; ++i) {
            clockMinuteStrings.add(s+String.valueOf(i));
            if (i == 9)
                s= "";
        }
        
        
        hourCombo = new JComboBox(clockHourStrings.toArray());
        //hourCombo.setPreferredSize(new Dimension(comboBoxWidth, hourCombo.getPreferredSize().height));
        minuteCombo = new JComboBox(clockMinuteStrings.toArray());
        //minuteCombo.setPreferredSize(new Dimension(comboBoxWidth, hourCombo.getPreferredSize().height));
        hasTimeBox = new JCheckBox("All Day Event");
        
        hasTimeBox.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (hasTimeBox.isSelected())
                    timePanel.setVisible(false);
                else
                    timePanel.setVisible(true);
                
            }
        });
        
        Box allDayBox = Box.createHorizontalBox();
        allDayBox.add(hasTimeBox);
        allDayBox.add(Box.createHorizontalGlue());
        JCheckBox amCheck = new JCheckBox("AM");
        
        timePanel = new JPanel(new GridLayout(0,1));
        
        timeBox = Box.createHorizontalBox();
        timeBox.add(Box.createHorizontalGlue());
        timeBox.add(hourCombo);
        timeBox.add(new JLabel(":"));
        timeBox.add(minuteCombo);
        timeBox.add(Box.createHorizontalGlue());
        
        timePanel.add(timeBox);
        timePanel.add(amCheck);
        subPanel = new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel,BoxLayout.Y_AXIS));
        subPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        saveEventButton = new JButton("Save");
        saveEventButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String eventName = eventNameField.getText();
                if (eventName.equals("")){
                    eventNameField.requestFocusInWindow();
                    return;
                }
                EventType eventType = null;
                for (int i = 0; i < eventTypeRadio.length; ++i) {
                    if (eventTypeRadio[i].isSelected()) {
                        eventType = EventType.get(i);
                        break;
                    }
                }
                event.setName(eventName);
                event.setEventType(eventType);
                if (event.getRepeating() != repeatComboBox.getSelectedIndex() && !newEvent){
                    MasterSchedule.getInstance().removeFromSchedule(event);
                    newEvent = true;
                }
                event.setRepeating(repeatComboBox.getSelectedIndex());

                if (!eventLocationField.getText().equals("")) {
                    event.setLocation(eventLocationField.getText());
                }
                if (!eventDescriptionArea.getText().equals("")) {
                    event.setDescription(eventDescriptionArea.getText());
                }
                
                if (!hasTimeBox.isSelected()){
                    if (amCheck.isSelected())
                        event.setTime(new MyTime(LocalTime.of((hourCombo.getSelectedIndex()+1)%12, 
                                                                 minuteCombo.getSelectedIndex())));
                    else
                        event.setTime(new MyTime(LocalTime.of(hourCombo.getSelectedIndex()+13, 
                                                                 minuteCombo.getSelectedIndex())));
                }
                

                if (newEvent){
                    MasterSchedule.getInstance().addEventToSchedule(SelectedDate.getInstance(),event);
                    newEvent = false;
                }
                cancelButton.doClick();
                SelectedDate.getInstance().notifyObservers();

            }

        });
        
        
        
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        
        deleteButton = new JButton("DELETE EVENT");
        deleteButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                MasterSchedule.getInstance().removeFromSchedule(event);
                SelectedDate.getInstance().notifyObservers();
                cancelButton.doClick();
            }
        });
        
        eventNameField = new JTextField(textFieldSize);
        eventLocationField = new JTextField(textFieldSize);

        repeatComboBox = new JComboBox(repeatBoxStrings);

        eventTypes = new ButtonGroup();
        eventTypeRadio = new JRadioButton[]{new JRadioButton(EventType.work.TYPE),
            new JRadioButton(EventType.family.TYPE),
            new JRadioButton(EventType.school.TYPE),
            new JRadioButton(EventType.social.TYPE),
            new JRadioButton(EventType.holiday.TYPE),
            new JRadioButton(EventType.other.TYPE)};

        eventTypeRadio[0].setSelected(true);

        eventDescriptionArea = new JTextArea(5, 5);
        eventDescriptionArea.setWrapStyleWord(true);
        eventDescriptionArea.setLineWrap(true);

        JScrollPane descriptionPane = new JScrollPane();
        descriptionPane.setViewportView(eventDescriptionArea);
//        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
//        subPanel.setAlignmentX(SwingConstants.LEFT);
        Box buttonBox = Box.createHorizontalBox();

        
        subPanel.add(createLabel("Event Name:"));
        subPanel.add(eventNameField);
        subPanel.add(allDayBox);
        subPanel.add(timePanel);
        subPanel.add(createLabel("Location:"));
        subPanel.add(eventLocationField);
        subPanel.add(createLabel("Description:"));
        subPanel.add(descriptionPane);
        subPanel.add(createLabel("Repeats:"));
        subPanel.add(repeatComboBox);
        subPanel.add(createLabel("Type of Event:"));
        JPanel radioPanel = new JPanel(new GridLayout(3, 2));
        for (int i = 0; i < eventTypeRadio.length; ++i) {
            eventTypeRadio[i].setActionCommand(String.valueOf(i));
            eventTypes.add(eventTypeRadio[i]);
            radioPanel.add(eventTypeRadio[i]);
        }
        hasTimeBox.doClick();
        subPanel.add(radioPanel);
        buttonBox.add(saveEventButton);
        buttonBox.add(cancelButton);
        subPanel.add(buttonBox);
        Box deleteBox = Box.createHorizontalBox();
        deleteBox.add(deleteButton);
        deleteBox.add(Box.createHorizontalGlue());
        subPanel.add(deleteBox);
        this.add(subPanel);

    }
    
    public Component createLabel(String label){
        Box labelBox = Box.createHorizontalBox();
        labelBox.add(new JLabel(label));
        labelBox.add(Box.createHorizontalGlue());
        return labelBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.setVisible(false);
        mainPanelButton.setVisible(true);
        eventNameField.setText("");
        eventLocationField.setText("");
        eventDescriptionArea.setText("");
        repeatComboBox.setSelectedIndex(0);
        eventTypeRadio[0].setSelected(true);
        if (!hasTimeBox.isSelected())
            hasTimeBox.doClick();
        event = null;
        
    }
    
    public void inputEvent(){
        newEvent = true;
        
        inputEvent(new CalendarEvent());
        
    }
    
    public void inputEvent(CalendarEvent event) {
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
        if (event.getTime().getStart() == null){
            if (!hasTimeBox.isSelected())
                hasTimeBox.doClick();
        }
        else{
            hourCombo.setSelectedIndex((event.getTime().getStart().getHour()-1)%12);
            minuteCombo.setSelectedIndex(event.getTime().getStart().getMinute());
        }
        
    }

    @Override
    public void update() {

        this.revalidate();
        this.repaint();
    }

}
