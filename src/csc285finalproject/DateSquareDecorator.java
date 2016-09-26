/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * Concrete instance of Decorator/Wrapper class for date squares
 * <p>
 * draws a string representing an event on top of the date square
 *
 * @author colbysadams
 */
public class DateSquareDecorator extends AbstractDateSquareDecorator
{

    private final static int offset = 15;
    private final static int diameter = 10;
    private int textHeight;

    DateSquareDecorator(CalendarEvent event, AbstractDateSquare square)
    {
        super(event, square);

        //this increases the height of the text for each event, so they will
        //be displayed in rows
        this.textHeight = square.getTextHeight() + offset;

    }

    /**
     *
     * @return the textHeight
     */
    public int getTextHeight()
    {
        return textHeight;
    }

    /**
     *
     * @param g
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void drawSquare(Graphics g, int x, int y, int width, int height)
    {
        getSquare().drawSquare(g, x, y, width, height);
        this.setOpaque(false);
        g.setColor(getEvent().getEventType().COLOR);
        g.fillOval(5, textHeight - diameter, diameter, diameter);
        g.setColor(Color.black);
        g.drawString(getEvent().getName(), 17, textHeight);
    }
}
