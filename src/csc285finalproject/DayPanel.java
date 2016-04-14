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
public class DayPanel extends CalendarViewPanel{
    
    
    
    
    public DayPanel(){
        super(); 
        
        
        
        update();
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


    
    

    
}
