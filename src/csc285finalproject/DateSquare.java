/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;

/**
 *
 * @author colbysadams
 */
public class DateSquare extends AbstractDateSquare{
    
    private MyDate date;
    private Color color;
    private boolean bordered;
    
    
    
    private ArrayList<DateSquareDecorator> daysEvents;
    
    
    
    public DateSquare(int month, int day, int year, Color color){
        super();
        try {
            this.date = new MyDate(month,day,year);
        } catch (IllegalDateException ex) {
        }
        this.color = color;
        //this.daysEvents = MasterSchedule.getInstance().getDaysEvents(date);
        
        
        this.addMouseListener(this);
        
        // if the square is the selected date, then put a border on the square
//        if (SelectedDate.getInstance().equals(date) && (Color.white == color)){
//            this.setBorder(BorderFactory.createLineBorder(Color.black));
//            bordered = true;
        //}
        
    }
    
    @Override
    public MyDate getDate(){
        return date;
    }
    
    @Override
    public int getTextHeight() {
        return textOffset;
    }

    
    @Override
    public void drawSquare(Graphics g,int x, int y, int width, int height) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
        //System.out.println("Width: " + width + " Height: " + height);
        g.setColor(Color.black);
        g.drawString(String.valueOf(date.getDay()), 5, textOffset);
        super.drawSquare(g, x, y, width, height);
    }
    
    
//    @Override
//    public void mouseClicked(MouseEvent e) {
//        //set the selected date to equal this date
//       
//        SelectedDate.getInstance().changeSelectedDate(date);
//        System.out.println("Selecting Date: " + SelectedDate.getInstance());
//
//    }
//


//    @Override
//    public void mouseEntered(MouseEvent e) {
//        //yellow border on mouse over
//        if (!bordered)
//            this.setBorder(BorderFactory.createLoweredBevelBorder());
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e) {
//        //if square was bordered, replace the border
//        if (!bordered)
//            this.setBorder(BorderFactory.createEmptyBorder());
//    }

   

    


}
