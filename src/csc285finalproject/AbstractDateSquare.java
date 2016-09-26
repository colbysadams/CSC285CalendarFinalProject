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
 * each square represents a particular day on the calendar for the various
 * calendar
 * views.
 *
 * @author colbysadams
 */
public abstract class AbstractDateSquare extends JComponent implements MouseListener
{

    protected final static int textOffset = 15;
    private MyDate date;

    @Override
    public void paintComponent(Graphics g)
    {
        drawSquare(g, 0, 0, getWidth(), getHeight());
        if (getDate().equals(SelectedDate.getInstance()))
        {
            g.setColor(Color.black);
            g.drawRect(0, 0, getWidth(), getHeight());
            g.drawRect(1, 1, getWidth() - 2, getHeight() - 2);
        }
    }

    /**
     *
     * @return the date
     */
    public MyDate getDate()
    {
        return date;
    }

    /**
     *
     * @param g
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public abstract void drawSquare(Graphics g, int x, int y, int width, int height);

    /**
     *
     * @return
     */
    public abstract int getTextHeight();

    @Override
    public void mouseClicked(MouseEvent e)
    {
        //set the selected date to equal this date
        if (getDate().equals(SelectedDate.getInstance()))
        {
            return;
        }
        SelectedDate.getInstance().changeSelectedDate(getDate());

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }
}
