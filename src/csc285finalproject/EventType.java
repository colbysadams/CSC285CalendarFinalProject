/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import javafx.scene.paint.Color;

/**
 *
 * @author colbysadams
 */
public enum EventType {
    
    social(Color.AQUA),
    business(Color.BLUEVIOLET),
    school(Color.CORAL),
    family(Color.DARKGOLDENROD),
    other(Color.GREENYELLOW);
    
    public final Color COLOR;
    
    EventType(Color color) 
    {
        COLOR = color;
    }
}
