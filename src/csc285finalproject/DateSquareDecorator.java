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
public class DateSquareDecorator extends AbstractDateSquareDecorator {

    
    private int textHeight;
    private final static int offset = 15;
    private final static int diameter = 10;
    

    //private MyDate date;

    DateSquareDecorator(CalendarEvent event, AbstractDateSquare square) {
        super(event,square);
        this.textHeight = square.getTextHeight() + offset;
        

    }

    public int getTextHeight() {
        return textHeight;
    }

    public void drawSquare(Graphics g, int x, int y,int width, int height) {
        getSquare().drawSquare(g, x,y,width, height);
        this.setOpaque(false);
        g.setColor(getEvent().getEventType().COLOR);
        g.fillRect(5, textHeight - diameter, diameter, diameter);
        g.setColor(Color.black);
        g.drawString(getEvent().getName(), 17, textHeight);

        super.drawSquare(g, x, y, width, height);
    }

    
    
    
//    @Override
//    public void mouseEntered(MouseEvent e) {
//        //yellow border on mouse over
//        if (!square.getDate().equals(SelectedDate.getInstance()))
//            this.setBorder(BorderFactory.createLoweredBevelBorder());
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e) {
//        //if square was bordered, replace the border
//        if (!square.getDate().equals(SelectedDate.getInstance())) 
//            this.setBorder(BorderFactory.createEmptyBorder());
//        
//    }


    

}
