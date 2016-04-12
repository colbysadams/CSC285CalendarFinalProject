/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;


/**
 *
 * @author colbysadams
 */
public class DateSquareDecorator extends AbstractDateSquare{

    private CalendarEvent event;
    private AbstractDateSquare square;
    private int textHeight;
    private final static int offset = 15;
    private final static int diameter = 10;
    //private MyDate date;
    DateSquareDecorator(CalendarEvent event, AbstractDateSquare square){
        this.event = event;
        this.square = square;
        this.setOpaque(false);
        this.addMouseListener(this);
        this.textHeight = square.getTextHeight()+offset;
        
        
    }
    
    public int getTextHeight(){
        return textHeight;
    }
    
    public void drawSquare(Graphics g,int width, int height){
        square.drawSquare(g, width, height);
        this.setOpaque(false);
        g.setColor(event.getEventType().COLOR);
        g.fillOval(5, textHeight-diameter, diameter, diameter);
        g.setColor(Color.black);
        g.drawString(event.getName(), 17, textHeight);
        
        if (square.getDate().equals(SelectedDate.getInstance()))
            this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public MyDate getDate() {
        return square.getDate();
    }

@Override
    public void mouseClicked(MouseEvent e) {
        //set the selected date to equal this date
       
        SelectedDate.getInstance().changeSelectedDate(square.getDate());
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
        if (square.getDate().equals(SelectedDate.getInstance()))
            this.setBorder(BorderFactory.createLineBorder(Color.black));
        else
            this.setBorder(BorderFactory.createEmptyBorder());
    }
    

}
