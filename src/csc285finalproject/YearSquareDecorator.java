package csc285finalproject;

import java.awt.Graphics;

/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose
 * Tools | Templates and open the template in the editor.
 */
/**
 *
 * @author colbysadams
 */
public class YearSquareDecorator extends AbstractDateSquareDecorator
{

    public YearSquareDecorator(CalendarEvent event, AbstractDateSquare square)
    {
        super(event, square);
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

        g.setColor(super.getColor());
        g.drawRect(x, y, width - 1, height - 1);
        getSquare().drawSquare(g, x + 1, y + 1, width - 2, height - 2);

    }

    /**
     * 
     * @return 
     */
    @Override
    public int getTextHeight()
    {
        return 0;
    }
}