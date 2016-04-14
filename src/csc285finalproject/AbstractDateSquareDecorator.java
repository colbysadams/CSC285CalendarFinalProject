package csc285finalproject;

import java.awt.Color;
import java.awt.event.MouseEvent;

/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose
 * Tools | Templates and open the template in the editor.
 */
/**
 *
 * @author colbysadams
 */
public abstract class AbstractDateSquareDecorator extends AbstractDateSquare
{

    private CalendarEvent event;
    private AbstractDateSquare square;

    //private MyDate date;
    AbstractDateSquareDecorator(CalendarEvent event, AbstractDateSquare square)
    {
        this.event = event;
        this.square = square;
        this.setOpaque(false);
        this.addMouseListener(this);

    }

    public CalendarEvent getEvent()
    {
        return event;
    }

    public MyDate getDate()
    {
        return square.getDate();
    }

    public Color getColor()
    {
        return event.getEventType().COLOR;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        square.mouseClicked(e);
    }

    protected AbstractDateSquare getSquare()
    {
        return square;
    }

}
