/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.time.LocalTime;

/**
 *
 * @author colbysadams
 */
public class MyTime {
    
    private LocalTime start,end;
    
    public MyTime(LocalTime start)
    {
        this.start = start;
        this.end = start.plusHours(1);
    }
    
    
}
