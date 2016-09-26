package csc285finalproject;

import java.awt.Color;
import java.awt.event.MouseEvent;

/**
 *
 * Decorator/Wrapper class for date squares.
 * <p>
 * The wrapper represents a calendar event that takes place on a particular day
 * <p>
 * @author colbysadams
 */
public abstract class AbstractDateSquareDecorator extends AbstractDateSquare
{

    private CalendarEvent event;
    private AbstractDateSquare square;

    AbstractDateSquareDecorator(CalendarEvent event, AbstractDateSquare square)
    {
        this.event = event;
        this.square = square;
        this.setOpaque(false);
        this.addMouseListener(this);

    }

    /**
     *
     * @return the event represented by this wrapper
     */
    public CalendarEvent getEvent()
    {
        return event;
    }

    /**
     *
     * @return the date represented by the wrapped date square
     */
    public MyDate getDate()
    {
        return square.getDate();
    }

    /**
     *
     * each type of event is represented by a different color
     * <p>
     * @return the color of the event
     */
    public Color getColor()
    {
        return event.getEventType().COLOR;
    }

    /**
     *
     * Propogates click down to wrapped date square.
     * <p>
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        square.mouseClicked(e);
    }

    /**
     *
     * @return the wrapped date square
     */
    protected AbstractDateSquare getSquare()
    {
        return square;
    }
}
