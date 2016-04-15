/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.io.Serializable;
import java.time.LocalTime;

/**
 *
 * @author colbysadams
 */
public class MyTime implements Serializable, Comparable<MyTime>
{

    private LocalTime time;
    private int hours, minutes;

    public MyTime(LocalTime start)
    {
        this.time = start;
        this.hours = 1;
        this.minutes = 0;
    }

    public MyTime()
    {
        time = null;

    }

    /**
     * @return the start
     */
    public LocalTime getTime()
    {
        return time;
    }

    /**
     * @param start the start to set
     */
    public void setTime(LocalTime start)
    {
        this.time = start;
    }

    /**
     * @return the end
     */
    public LocalTime getEnd()
    {
        LocalTime end = time.plusHours(hours);
        end.plusHours(minutes);
        return end;
    }

    public void setDuration(int hours, int minutes)
    {
        this.hours = hours;
        this.minutes = minutes;
    }

    public String toString()
    {
        if (time == null)

            return "";
//        String s;
//        hour int = S
        return time.toString();
    }

    public String getClock()
    {
        if (time == null)

            return "";

        String s = "";

        s += time.getHour() % 12;

        if (s.equals("0"))
            s = "12";
        s += ":";
        if (time.getMinute() < 10)
            s += 0;
        s += time.getMinute();

        if (time.getHour() / 12 == 1)
            s += "PM";
        else
            s += "AM";

        return s;

    }

    @Override
    public int compareTo(MyTime o)
    {
        if (time == null)
        {
            if (o.time == null)
                return 0;
            return 1;
        }
        if (o.time == null)
            return -1;
        return time.compareTo(o.time);
    }

}
