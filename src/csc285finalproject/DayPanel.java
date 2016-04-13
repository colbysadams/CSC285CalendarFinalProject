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
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author colbysadams
 */
public class DayPanel extends CalendarViewPanel implements ActionListener{

    private JPanel detailPanel;
    
    private CalendarEvent selectedEvent;
    
    private ArrayList<CalendarEvent> daysEvents;
    
    public DayPanel(){
        super(); 
        this.setLayout(new BorderLayout());
        
        
        
        
        
        update();
    }
    
    public void addDateSquares(){
        if (detailPanel != null) 
            remove(detailPanel);
        super.addDateSquares();
        detailPanel = new JPanel(new GridLayout(0,1));
        daysEvents = MasterSchedule.getInstance().getDaysEvents(SelectedDate.getInstance());
        JButton eventLabel;
        if (daysEvents.size() == 0){
            eventLabel = new JButton("<html><h1>No Events</html></h1>");
            eventLabel.setEnabled(false);
            detailPanel.add(eventLabel);
        }    
        else{
            
            for (int i = 0; i < daysEvents.size(); ++i){
                eventLabel = new JButton(daysEvents.get(i).getEventHTML());
                eventLabel.setActionCommand(String.valueOf(i));
                
                detailPanel.add(eventLabel);
            }
        }
        this.add(detailPanel,BorderLayout.EAST);
        
        
        
    }

    @Override
    public int getDaysDisplayed() {
        return 1;
    }

    @Override
    public int getRowSize() {
        return 1;
    }

    @Override
    public int getBuffer() {
        return 0;
    }

    @Override
    public int getDateOffset() {
        return SelectedDate.getInstance().getDay()-1;
    }


    public CalendarEvent getSelectedEvent(){
        return selectedEvent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        int n = Integer.parseInt(e.getActionCommand());
        selectedEvent = daysEvents.get(n);
    }


    
}
