/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author colbysadams
 */
public class EventDetailPanel extends JPanel implements Observer,ActionListener{
    
    private ArrayList<CalendarEvent> daysEvents;
    
    private static CalendarEvent selectedEvent;
    
    public EventDetailPanel(){
        
        super(new GridLayout(0,1));
        this.setPreferredSize(new Dimension(200,this.getHeight()));
        SelectedDate.getInstance().addObserver(this);
        addButtonsToPanel();
            
    }
    
    private void addButtonsToPanel(){
        this.removeAll();
        daysEvents = MasterSchedule.getInstance().getDaysEvents(SelectedDate.getInstance());
        JButton eventButton;
        if (daysEvents.size() == 0){
            eventButton = new JButton("<html><h1>No Events</html></h1>");
            eventButton.setEnabled(false);
            this.add(eventButton);
        }    
        else{

            for (int i = 0; i < daysEvents.size(); ++i){
                eventButton = new JButton(daysEvents.get(i).getEventHTML());
                eventButton.setActionCommand(String.valueOf(i));
                eventButton.addActionListener(this);
                this.add(eventButton);
            }
        }
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        int n = Integer.parseInt(e.getActionCommand());
        selectedEvent = daysEvents.get(n);
        
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
