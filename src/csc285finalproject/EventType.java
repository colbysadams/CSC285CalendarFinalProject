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
    holiday("Holiday",Color.MAGENTA),
    other("Other",Color.cyan);
    
    
    public final Color COLOR;
    public final String TYPE;
    EventType(String name, Color color) 
    {
        TYPE = name;
        COLOR = color;
    }
    
    public static EventType get(int index) {
        switch (index){
            case 0:
                return work;
            case 1:
                return family;
            case 2:
                return school;
            case 3:
                return social;
            case 4:
                return holiday;
        }
        return other;
    }

}
