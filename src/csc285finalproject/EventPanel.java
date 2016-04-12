/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author colbysadams
 */
public class EventPanel extends JPanel implements Observer
{

    private JPanel subPanel;
    
    private ArrayList<AbstractCalendarEvent> events;
    
    private static int offsetX = 5;
    
    private JButton createEventButton;
    private JTextField eventNameField,eventLocationField;
    private JTextArea eventDescriptionArea;
     
    
    private int textFieldSize = 10;
    public EventPanel()
    {
        super();
        this.setLayout(new BorderLayout());
        
        events = new ArrayList();
        
        JPanel newEventPanel = new JPanel();
        newEventPanel.setLayout(new BoxLayout(newEventPanel,BoxLayout.Y_AXIS));
        
        createEventButton = new JButton("Create Event");
        eventNameField = new JTextField(textFieldSize);
        eventLocationField = new JTextField(textFieldSize);
        
        eventDescriptionArea = new JTextArea(textFieldSize,1);
        JScrollPane descriptionPane = new JScrollPane(eventDescriptionArea);
        
        //newEventPanel.add(Box.createGlue());
        newEventPanel.add(createEventButton);
        newEventPanel.add(eventNameField);
        newEventPanel.add(eventLocationField);
        newEventPanel.add(descriptionPane);
        //newEventPanel.add(Box.createGlue());
        this.add(newEventPanel,BorderLayout.NORTH);
        
        
        addEventLabels();
        generateSampleEvents();
        
    }
    
    public void addEventLabels()
    {
        if (subPanel != null)
            this.remove(subPanel);
        
        subPanel = new JPanel();
        subPanel.setLayout( new BoxLayout(subPanel, BoxLayout.Y_AXIS));
        
        for (int i = 0; i < events.size(); ++i) {
            if (events.get(i).isOnSelectedDate()){
                subPanel.add(events.get(i));
            }
        }
        
        
        this.add(subPanel);
    }
    
    private void generateSampleEvents(){
        try {
            
            events.add(new YearlyEvent("Colby's Birthday",EventType.family,(new MyDate(3,15,2016))));
            events.add(new MonthlyEvent("Flea Medicine",EventType.other,(new MyDate(3,31,2016))));
            events.add(new WeeklyEvent("go to class",EventType.school,(new MyDate(3,15,2016))));
            events.add(new OneTimeEvent("Emily's Birthday",EventType.family,(new MyDate(9,7,2016))));
            events.add(new YearlyEvent("Leroy's Birthday",EventType.family,(new MyDate(3,16,2016))));            
        } catch (IllegalDateException ex) {
            Logger.getLogger(EventPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @Override
    public void update() {
        this.addEventLabels();
        this.revalidate();
        this.repaint();
    }
    
   
    
    
}
