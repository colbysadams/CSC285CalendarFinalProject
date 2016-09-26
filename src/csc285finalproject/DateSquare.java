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
 * concrete class of date square
 * will be wrapped by date square decorators if events occur on the
 * date represented
 *
 * @author colbysadams
 */
public class DateSquare extends AbstractDateSquare
{

    private MyDate date;
    private Color color;

    public DateSquare(int month, int day, int year, Color color)
    {
        super();
        try
        {
            this.date = new MyDate(month, day, year);
        }
        catch (IllegalDateException ex)
        {

        }
        this.color = color;

        this.addMouseListener(this);

    }

    /**
     *
     * @return the date
     */
    @Override
    public MyDate getDate()
    {
        return date;
    }

    /**
     *
     * @return the textOffset - used to print events by row
     * <p>
     */
    @Override
    public int getTextHeight()
    {
        return textOffset;
    }

    /**
     *
     * draws the actual square
     * <p>
     * @param g
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @Override
    public void drawSquare(Graphics g, int x, int y, int width, int height)
    {
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
        g.drawString(String.valueOf(date.getDay()), 5, textOffset);
    }
}
