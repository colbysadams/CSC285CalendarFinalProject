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
 * @author colbysadams
 */
public class DaySquareDecorator extends AbstractDateSquareDecorator
{

    private final static int offset = 15;
    private final static int diameter = 10;
    private int textHeight;

    DaySquareDecorator(CalendarEvent event, AbstractDateSquare square)
    {
        super(event, square);
        this.textHeight = square.getTextHeight() + offset;

    }

    public int getTextHeight()
    {
        return textHeight;
    }

    public void drawSquare(Graphics g, int x, int y, int width, int height)
    {
        getSquare().drawSquare(g, x, y, width, height);
        this.setOpaque(false);
        g.setColor(getEvent().getEventType().COLOR);
        g.fillOval(5, textHeight - diameter, diameter, diameter);
        g.setColor(Color.black);
        g.drawString(getEvent().getShortLabel() + " : "
                + MasterSchedule.repeatStrings[getEvent().getRepeating()], 17, textHeight);

    }
}
