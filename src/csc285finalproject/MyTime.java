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
    //reminder offset
    private int hours, minutes;
    //private boolean reminder;

    /**
     *
     * @param start
     */
    public MyTime(LocalTime start)
    {
        this.time = start;
        this.hours = -1;
        this.minutes = -1;
        //reminder = false;
    }

    public MyTime()
    {
        time = null;
        this.hours = -1;
        this.minutes = -1;
        //reminder = false;
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

    /**
     * sets a reminder
     * <p>
     * @param hours   - hours
     * @param minutes - minutes
     */
    public void setReminder(int hours, int minutes)
    {
        //this.reminder = reminder;
        this.hours = hours;
        this.minutes = minutes;
    }

    /**
     *
     * @return the reminder
     */
    public boolean hasReminder()
    {
        return (hours >= 0);
    }

    public LocalTime getReminder()
    {
        if (time == null)
            return LocalTime.of(7, 00);
//        System.out.println("time.getHour(): " + time.getHour());
//        System.out.println("hours: " + hours);
//        System.out.println("time.getMinutes(): " + time.getMinute());
//        System.out.println("minutes: " + minutes);
        LocalTime remindTime = LocalTime.of(time.getHour(), time.getMinute());
        remindTime = remindTime.minusHours(hours);
        remindTime = remindTime.minusMinutes(minutes);
        return remindTime;

    }

    public int getHoursBefore()
    {
        return hours;

    }

    public int getMinutesBefore()
    {
        return minutes;
    }

    /**
     *
     * @return the time
     */
    public String toString()
    {
        if (time == null)

            return "";

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
