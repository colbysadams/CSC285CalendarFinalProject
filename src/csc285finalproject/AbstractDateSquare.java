/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;

/**
 *
 * @author colbysadams
 */
public abstract class AbstractDateSquare extends JComponent implements MouseListener, MouseMotionListener
{
    @Override
    public void paintComponent(Graphics g){
        drawSquare(g,getWidth(),getHeight());
    }
    
    public abstract MyDate getDate();
    
    public abstract void drawSquare(Graphics g, int width, int height);
    
    public abstract int getTextHeight();
    
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
