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
public enum EventType
{

    work("Work", Color.blue, 0),
    family("Family", Color.ORANGE, 1),
    school("School", Color.green, 2),
    social("Social", Color.red, 3),
    holiday("Holiday", Color.MAGENTA, 4),
    other("Other", Color.cyan, 5);

    public final Color COLOR;
    public final String TYPE;
    public final int index;

    EventType(String name, Color color, int index)
    {
        this.index = index;
        TYPE = name;
        COLOR = color;
    }

    public static EventType get(int index)
    {
        switch (index)
        {
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
