/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

/**
 *
 * Enum representing every day of the week
 * <p>
 * @author colbysadams
 */
public enum Weekday
{

    sun("Sunday", 0),
    mon("Monday", 1),
    tue("Tuesday", 2),
    wed("Wednesday", 3),
    thu("Thursday", 4),
    fri("Friday", 5),
    sat("Saturday", 6);

    final String name;
    final int index;

    Weekday(String s, int i)
    {
        name = s;
        index = i;
    }

    /**
     *
     * returns day of the week at a given index i Sunday = 0, Monday = 1, etc...
     * <p>
     * @param i index of day of the week
     * <p>
     * @return day of the week
     */
    public static Weekday getWeekday(int i)
    {
        switch (i)
        {
            case 0:
                return Weekday.sun;

            case 1:
                return Weekday.mon;

            case 2:
                return Weekday.tue;

            case 3:
                return Weekday.wed;

            case 4:
                return Weekday.thu;

            case 5:
                return Weekday.fri;

            case 6:
                return Weekday.sat;
        }
        return null;
    }

    public String toString()
    {
        return name;
    }

    /**
     *
     * @return the first letter of the day of the week
     */
    public String getShortString()
    {
        return String.valueOf(name.charAt(0));
    }
}
