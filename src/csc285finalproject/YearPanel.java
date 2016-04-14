/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * 
 * Creates a collection of 12 month panels to represent the whole year
 * year panel is both a subject and observer,
 * year panel observes the SelectedDate singleton,
 * but is also observed by its 12 month panels within the view
 * 
 * update is just relayed to each month panel
 *
 * @author colbysadams
 */
public class YearPanel extends CalendarViewPanel implements Subject{

    ArrayList<MonthPanel> monthPanels;
    
    public YearPanel(){
        super();
        
        
        getSubPanel().setLayout(new GridLayout(0,4,10,10));
        monthPanels = new ArrayList();
        
        
        JPanel monthSubPanel;
//        
        MonthPanel monthPanel = null;
        JLabel monthLabel = null;
//        //create a month panel for each month, override the SelectedDate with the first day of the month
        for (int i = 1; i <= 12; i++){
            monthSubPanel = new JPanel(new BorderLayout());
            
            try {
                monthPanel = (new MonthPanel(new MyDate(i,
                                                 1,
                                                 SelectedDate.getInstance().getYear()),true));
                monthLabel = new JLabel(Month.getMonth(i).getName());
            } catch (IllegalDateException ex) {
                Logger.getLogger(YearPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            monthLabel.setHorizontalAlignment(SwingConstants.CENTER);
            //panel.makeLabelsShort();
            monthSubPanel.add(monthLabel,BorderLayout.NORTH);
            monthSubPanel.add(monthPanel, BorderLayout.CENTER);
            this.addObserver(monthPanel);
            getSubPanel().add(monthSubPanel);
        }
        update();
            
    }
    
    public void addLabels(){}
    
    @Override
    public void addDateSquares(){
        
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
        return 0;
    }

    @Override
    public void addObserver(Observer o) {
        monthPanels.add((MonthPanel)o);
    }

    @Override
    public void deleteObserver(Observer o) {
    }

    @Override
    public void notifyObservers() {
        for (Observer o : monthPanels) {
            o.update();
        }
    }
    @Override
    public void update(){
        super.update();
        addDateSquares();
        notifyObservers();
    }


    

    
    

    
}
