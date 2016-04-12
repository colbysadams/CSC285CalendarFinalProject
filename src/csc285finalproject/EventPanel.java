/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author colbysadams
 */
public class EventPanel extends JPanel implements Observer
{

    private JPanel subPanel;
    
    private ArrayList<DateSquareDecorator> events;
    
    private static int offsetX = 5;
    
    private JButton saveEventButton,cancelButton;
    private JTextField eventNameField,eventLocationField;
    private JTextArea eventDescriptionArea;
     
    private JRadioButton[] eventTypeRadio;
    private ButtonGroup eventTypes;
    
    private int textFieldSize = 10;
    public EventPanel()
    {
        super();
        this.setLayout(new BorderLayout());
        
        events = new ArrayList();
        
        JPanel newEventPanel = new JPanel();
        
        saveEventButton = new JButton("Save");
        saveEventButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        
        });
        cancelButton = new JButton("Cancel");
        eventNameField = new JTextField(textFieldSize);
        eventLocationField = new JTextField(textFieldSize);
        
        eventTypes = new ButtonGroup();
        eventTypeRadio = new JRadioButton[]{new JRadioButton(EventType.work.TYPE),
                                            new JRadioButton(EventType.family.TYPE),
                                            new JRadioButton(EventType.school.TYPE),
                                            new JRadioButton(EventType.social.TYPE),
                                            new JRadioButton(EventType.other.TYPE)};
        
        
        
        eventDescriptionArea = new JTextArea(5,5);
        eventDescriptionArea.setWrapStyleWord(true);
        eventDescriptionArea.setLineWrap(true);
        
        JScrollPane descriptionPane = new JScrollPane();
        descriptionPane.setViewportView(eventDescriptionArea);
        newEventPanel.setLayout(new BoxLayout(newEventPanel, BoxLayout.Y_AXIS));
        newEventPanel.setAlignmentX(SwingConstants.LEFT);
        Box buttonBox = Box.createHorizontalBox();
        
        
        newEventPanel.add(new JLabel("Event Name:"));
        newEventPanel.add(eventNameField);
        newEventPanel.add(new JLabel("Location:"));
        newEventPanel.add(eventLocationField);

        newEventPanel.add(new JLabel("Description:"));
        newEventPanel.add(descriptionPane);
        
        JPanel radioPanel = new JPanel(new GridLayout(3,2));
        for (int i = 0; i < eventTypeRadio.length; ++i) {
            eventTypeRadio[i].setActionCommand(String.valueOf(i));
            eventTypes.add(eventTypeRadio[i]);
            radioPanel.add(eventTypeRadio[i]);
        }
        newEventPanel.add(radioPanel);
        buttonBox.add(saveEventButton);
        buttonBox.add(cancelButton);
        newEventPanel.add(buttonBox);
        
        
        
        this.add(newEventPanel,BorderLayout.NORTH);
        
        
        addEventLabels();
        generateSampleEvents();
        
    }
    
    public void addEventLabels()
    {
//        if (subPanel != null)
//            this.remove(subPanel);
//        
//        subPanel = new JPanel();
//        subPanel.setLayout( new GridLayout(10,1,5,5));
//        
//        for (int i = 0; i < events.size(); ++i) {
//            if (events.get(i).isOnSelectedDate()){
//                subPanel.add(events.get(i));
//            }
//        }
//        
//        
//        this.add(subPanel);
    }
    
    private void generateSampleEvents(){
//        try {
//            
//            events.add(new YearlyEvent("Colby's Birthday",EventType.family,(new MyDate(3,15,2016))));
//            events.add(new MonthlyEvent("Flea Medicine",EventType.other,(new MyDate(3,31,2016))));
//            events.add(new WeeklyEvent("go to class",EventType.school,(new MyDate(3,15,2016))));
//            events.add(new OneTimeEvent("Emily's Birthday",EventType.family,(new MyDate(9,7,2016))));
//            events.add(new YearlyEvent("Leroy's Birthday",EventType.family,(new MyDate(3,16,2016))));            
//        } catch (IllegalDateException ex) {
//            Logger.getLogger(EventPanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    

    @Override
    public void update() {
        this.addEventLabels();
        this.revalidate();
        this.repaint();
    }
    
    
    
   
    
    
}
