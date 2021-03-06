/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * The week and month views were similar, so this class implements
 * the common methods for both week and month views.
 * <p>
 * Created to display an understanding of inheritance for assignment.
 * <p>
 * @author colbysadams
 */
public abstract class WeekMonthParent extends AbstractCalendarViewPanel
{

    public WeekMonthParent(MyDate date, boolean shortLabels)
    {
        super(date, shortLabels);

    }

    public WeekMonthParent()
    {
        super();

    }

    /**
     * puts the weekday names at the top of each column
     */
    @Override
    public void addLabels()
    {
        Box labelBox = new Box(BoxLayout.X_AXIS);
        labelBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label;
        for (int i = 0; i < 7; ++i)
        {

            label = new JLabel(getWeekdayName(i));

            label.setVerticalAlignment(SwingConstants.BOTTOM);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            labelBox.add(Box.createHorizontalGlue());
            labelBox.add(label);

            labelBox.add(Box.createHorizontalGlue());
        }

        getSubPanel().add(labelBox, BorderLayout.NORTH);
    }

    /**
     *
     * returns a string representing the day of the week type of string to be
     * returned depends on boolean value of
     * shortLabels;
     * <p>
     * @param i the index of the day of the week
     * <p>
     * @return a string representing the day of the week
     */
    public String getWeekdayName(int i)
    {
        if (shortLabels)
        {
            return Weekday.getWeekday(i).getShortString();
        }
        return Weekday.getWeekday(i).name;
    }

    @Override
    public int getRowSize()
    {
        return 7;
    }
}
