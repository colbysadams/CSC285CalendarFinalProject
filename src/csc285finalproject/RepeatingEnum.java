/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

/**
 *
 * Enumerated values for all possible options for regularly repeating events
 *
 * @author cadams
 */
public enum RepeatingEnum
{

    ONETIME(0, "Never", false, false, false),
    YEARLY(1, "Yearly", false, false, true),
    MONTHLY(2, "Monthly", true, false, true),
    WEEKLY(3, "Weekly", true, true, false),
    DAILY(4, "Everyday", true, true, true);
    public final int NUM;
    public final String STRING;
    boolean[] masked;

    RepeatingEnum(int i, String s, boolean month, boolean day, boolean year)
    {
        NUM = i;
        STRING = s;
        masked = new boolean[]
        {
            month, day, year
        };
    }

    public static RepeatingEnum getByNum(int i)
    {
        switch (i)
        {
            case 0:
                return ONETIME;
            case 1:
                return YEARLY;
            case 2:
                return MONTHLY;
            case 3:
                return WEEKLY;
            case 4:
                return DAILY;

        }
        return null;
    }

    public String toString()
    {
        return STRING;
    }

    /**
     * Uses a method similar to bit masking to match various dates to repeating
     * events.
     * <p>
     * Example: An event occurs every month on the 15th. So the event will be
     * stored
     * in the hashmap under MM_15_YYYY. This way, no matter what month or year
     * is currently selected, when the selected date is masked, it will find the
     * monthly
     * event.
     * <p>
     * <p>
     * @param date
     *             <p>
     * @return
     */
    public String getMapString(MyDate date)
    {
        String s;

        if (masked[0])
        {
            s = "MM_";
        } else
        {
            s = date.getMonthString() + "_";
        }

        if (masked[1])
        {
            s += "DD_";
        } else
        {
            s += date.getDay() + "_";
        }

        if (masked[2])
        {
            s += "YYYY";
        } else
        {
            s += date.getYear();
        }

        if (this.equals(WEEKLY))
        {
            s = date.getDayOfWeek().name;
        }
        return s;

    }
}
