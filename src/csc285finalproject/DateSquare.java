/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author colbysadams
 */
public class DateSquare extends JLabel implements MouseListener, MouseMotionListener{
    
    private int month,day,year;
    private boolean bordered;
    
    
    
    public DateSquare(int month, int day, int year, Color color){
        super();
        this.month = month;
        this.day = day;
        this.year = year;
        
        this.setOpaque(true);
        this.setBackground(color);
        this.setText(String.valueOf(day));
        this.setVerticalAlignment(SwingConstants.TOP);
        this.addMouseListener(this);
        
        // if the square is the selected date, then put a border on the square
        if (SelectedDate.getInstance().fieldsEqual(month, day, year) && (Color.white == color)){
            this.setBorder(BorderFactory.createLineBorder(Color.black));
            bordered = true;
        }
        
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        //set the selected date to equal this date
        try {
            SelectedDate.getInstance().changeSelectedDate(new MyDate(month,day,year));
        } catch (IllegalDateException ex) {
            Logger.getLogger(DateSquare.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Selecting Date: " + SelectedDate.getInstance());

    }



    @Override
    public void mouseEntered(MouseEvent e) {
        //yellow border on mouse over
        this.setBorder(BorderFactory.createLineBorder(Color.yellow));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //if square was bordered, replace the border
        if (bordered)
            this.setBorder(BorderFactory.createLineBorder(Color.black));
        else
            this.setBorder(BorderFactory.createEmptyBorder());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
    
        @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}
