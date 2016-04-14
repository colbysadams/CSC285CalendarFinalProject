/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;

/**
 *
 * @author colbysadams
 */
public abstract class AbstractDateSquare extends JComponent implements MouseListener
{
    
    protected final static int textOffset = 15;
    
    private MyDate date;
    
    
    
    @Override
    public void paintComponent(Graphics g){
        drawSquare(g,0,0,getWidth(),getHeight());
        if (getDate().equals(SelectedDate.getInstance())){
            g.setColor(Color.black);
            g.drawRect(0,0,getWidth(),getHeight());
            g.drawRect(1,1,getWidth()-2,getHeight()-2);
        }
    }
    
    public MyDate getDate(){
        return date;
    }
    
    public abstract void drawSquare(Graphics g, int x, int y, int width, int height);
    
    public abstract int getTextHeight();
    
    
        @Override
    public void mouseClicked(MouseEvent e) {
        //set the selected date to equal this date
       
        SelectedDate.getInstance().changeSelectedDate(getDate());
        System.out.println("Selecting Date: " + SelectedDate.getInstance());
        

    }


 
        @Override
    public void mouseEntered(MouseEvent e) {
        //yellow border on mouse over
//        System.out.println("mouseEntered" );
//        ;
//        if (!getDate().equals(SelectedDate.getInstance()))
//            setBorder(BorderFactory.createLoweredBevelBorder());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //if square was bordered, replace the border
//        if (!getDate().equals(SelectedDate.getInstance())) 
//            setBorder(BorderFactory.createEmptyBorder());
//        
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
    
    
  
    
}
