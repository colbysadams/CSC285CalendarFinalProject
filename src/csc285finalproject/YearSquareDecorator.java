package csc285finalproject;

import java.awt.Graphics;

/**
 *
 * Wrapper/Decorator class for date squares in the YearPanel
 * <p>
 * Instead of text, a colored border is drawn around the date square
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
     * wrap the underlying date square and decorators with a colored border
     * recursively
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
