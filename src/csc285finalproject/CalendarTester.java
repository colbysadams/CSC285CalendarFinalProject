/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;


import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author colbysadams
 */
public class CalendarTester {

    private JFrame frame;
   
    
    public CalendarTester() {
    

        
        
        frame = new JFrame("Calendar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ChangeDatePanel(),BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        new CalendarTester();
        
//        try
//        {
//            FileOutputStream fileOut = new FileOutputStream("selectedDate.ser");
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//            out.writeObject(SelectedDate.getInstance());
//            out.close();
//            fileOut.close();
//            System.out.println("Serialized dates are saved in selectedDate.ser");
//            
//        }
//        catch(IOException e)
//        {
//            e.printStackTrace();
//        }

    }
    
}
