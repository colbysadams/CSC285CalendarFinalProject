/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author colbysadams
 */
public class CalendarTester
{

    private JFrame frame;

    public CalendarTester()
    {

        frame = new MainPanel("Calendar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                try
                {
                    // Write to disk with FileOutputStream
                    FileOutputStream f_out = new FileOutputStream("calendar.ser");

                    // Write object with ObjectOutputStream
                    ObjectOutputStream obj_out = new ObjectOutputStream(f_out);

                    // Write object out to disk
                    obj_out.writeObject(MasterSchedule.getInstance());
                    obj_out.close();
                }
                catch (IOException ex)
                {
                    Logger.getLogger(CalendarTester.class.getName()).log(Level.SEVERE, null, ex);
                }
                finally
                {

                }
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        new CalendarTester();
    }

}
