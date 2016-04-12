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
    work("Work",Color.blue),
    family("Family",Color.yellow),
    school("School",Color.green),
    social("Social",Color.red),
    other("Other",Color.orange);
    
    
    public final Color COLOR;
    public final String TYPE;
    EventType(String name, Color color) 
    {
        TYPE = name;
        COLOR = color;
    }
    
    

}
