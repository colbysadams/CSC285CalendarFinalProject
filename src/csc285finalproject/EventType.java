/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.awt.Color;



/**
 *
 * @author colbysadams
 */
public enum EventType {
    
    social(Color.red),
    business(Color.blue),
    school(Color.green),
    family(Color.yellow),
    other(Color.orange);
    
    public final Color COLOR;
    
    EventType(Color color) 
    {
        COLOR = color;
    }
    
    

}
