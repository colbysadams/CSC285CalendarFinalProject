/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author colbysadams
 */
public class EventDetailPanel extends JPanel implements Observer,ActionListener{
    
    private ArrayList<CalendarEvent> daysEvents;
    
    private static CalendarEvent selectedEvent;
    
    private JPanel buttonPanel, detailPanel;
    
    private JLabel nameLabel,timeLabel,typeLabel,locationLabel,descriptionLabel,repeatingLabel;
    
    private static JButton editEventButton;
    
    public EventDetailPanel(){
        
        super();
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(175,this.getHeight()));
        SelectedDate.getInstance().addObserver(this);
        selectedEvent = null;
        buttonPanel = new JPanel(new GridLayout(0,1));
        detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        //detailPanel.setPreferredSize(new Dimension(getWidth(),getHeight()));
        
        this.add(buttonPanel,BorderLayout.NORTH);
        this.add(detailPanel,BorderLayout.CENTER);
        
        //this.editEventButton = editEventButton;
        
        nameLabel = new JLabel();
        nameLabel.setFont(new Font(nameLabel.getFont().getName(),Font.PLAIN,20));
        typeLabel = new JLabel();
        typeLabel.setFont(new Font(nameLabel.getFont().getName(),Font.BOLD,16));
        timeLabel = new JLabel();
        timeLabel.setFont(new Font(nameLabel.getFont().getName(),Font.BOLD,14));
        repeatingLabel = new JLabel();
        locationLabel = new JLabel();
        descriptionLabel = new JLabel();
        
        detailPanel.add(nameLabel);
        detailPanel.add(Box.createVerticalStrut(10));
        detailPanel.add(typeLabel);
        detailPanel.add(Box.createVerticalStrut(5));
        detailPanel.add(timeLabel);
        detailPanel.add(Box.createVerticalStrut(3));
        detailPanel.add(repeatingLabel);
        detailPanel.add(Box.createVerticalStrut(3));
        detailPanel.add(locationLabel);
        detailPanel.add(Box.createVerticalStrut(3));
        detailPanel.add(descriptionLabel);
        
        addButtonsToPanel();
    }
    
    private void addButtonsToPanel(){
        buttonPanel.removeAll();
        selectedEvent = null;
        editEventButton.setEnabled(false);
        nameLabel.setText("");
        timeLabel.setText("");
        typeLabel.setText("");
        repeatingLabel.setText("");
        locationLabel.setText("");
        descriptionLabel.setText("");
        detailPanel.repaint();
        
        daysEvents = MasterSchedule.getInstance().getDaysEvents(SelectedDate.getInstance());
        JButton eventButton;
        
        //Box buttonBox;
        if (daysEvents.size() == 0){
            eventButton = new JButton("<html><h2>No Events</html></h2>");
            //buttonPanel = new JPanel(new BorderLayout());
            eventButton.setEnabled(false);
//            buttonBox = Box.createHorizontalBox();
//            buttonBox.add(eventButton);
//            buttonBox.add(Box.createHorizontalStrut(10));
            buttonPanel.add(eventButton);
        }    
        else{

            for (int i = 0; i < daysEvents.size(); ++i){
                eventButton = new JButton(daysEvents.get(i).getName());
                //buttonPanel = new JPanel(new BorderLayout(0,0));
                eventButton.setActionCommand(String.valueOf(i));
                eventButton.addActionListener(this);
//                buttonBox = Box.createHorizontalBox();
//                buttonBox.add(eventButton);
//                buttonBox.add(Box.createHorizontalStrut(10));
                buttonPanel.add(eventButton);
                //this.add(buttonPanel);
                
            }
            //this.add(detailPanel);
        }
        
    }
    
    public static void addEditEventButton(JButton editEventButton){
        EventDetailPanel.editEventButton = editEventButton;
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        int n = Integer.parseInt(e.getActionCommand());
        selectedEvent = daysEvents.get(n);
        
        editEventButton.setEnabled(true);
        
        nameLabel.setText(selectedEvent.getName());
        timeLabel.setText(selectedEvent.getTime().getClock());
        typeLabel.setForeground(selectedEvent.getEventType().COLOR);
        typeLabel.setText(selectedEvent.getEventType().TYPE);
        repeatingLabel.setText(selectedEvent.getRepeatingString());
        locationLabel.setText(selectedEvent.getLocation());
        descriptionLabel.setText(selectedEvent.getDescriptionLabel());
        
        detailPanel.revalidate();
        detailPanel.repaint();
        System.out.println("EventSelected "  + selectedEvent);
        
    }
    


    @Override
    public void update() {
        addButtonsToPanel();
    }
    
    public static CalendarEvent getSelectedEvent(){
        return selectedEvent;
    }
    
}
