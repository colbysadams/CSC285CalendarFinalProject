/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


/**
 *
 * JTabbed pane that lets you cycle through the different calendar views
 * 
 * today button sets the selected date to today's date
 * 
 * the function of the previous/next buttons depends on the current view
 * 
 * Ex. current view = week, next button advances one week
 *     current view = month, next button advances one month
 * 
 * @author colbysadams
 */
public class MainPanel extends JPanel implements Observer
{
    
    private SelectedDate selectedDate;
    private CalendarViewPanel weekPanel;
    private CalendarViewPanel monthPanel;
    private CalendarViewPanel dayPanel;
    private CalendarViewPanel yearPanel;
    
    private EventPanel eventPanel;
    
    private JButton nextButton;
    private JButton prevButton;
    private JButton todayButton;
    
    
    private JLabel dayOfWeek;
    private JLabel date;
    
    private JButton eventButton;
    private JTabbedPane calendarView;
    
    //tester
    private HashMap<MyDate,DateSquareDecorator> events = new HashMap<>();
    
    
    public MainPanel(){
        super(new BorderLayout());
        
        
        
        dayPanel = new DayPanel();
        weekPanel = new WeekPanel();
        monthPanel = new MonthPanel();
        yearPanel = new YearPanel();
        
        eventPanel = new EventPanel();
        
        calendarView = new JTabbedPane();
        
        this.selectedDate = SelectedDate.getInstance();
        
        selectedDate.addObserver(dayPanel);
        selectedDate.addObserver(weekPanel);
        selectedDate.addObserver(monthPanel);
        selectedDate.addObserver(yearPanel);
        selectedDate.addObserver(eventPanel);
        selectedDate.addObserver(this);
        
        nextButton = new JButton("-->");
        prevButton = new JButton("<--");
        todayButton = new JButton("Today");
        
        eventButton = new JButton("Create Event");
        
        nextButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                
                switch(calendarView.getSelectedIndex()){
                    case 0:
                        selectedDate.nextDay(1);
                        break;
                    case 1:
                        selectedDate.nextDay(7);
                        break;
                    case 2:
                        selectedDate.nextDay(selectedDate.getDaysInMonth()-selectedDate.getDay()+1);
                        break;
                    case 3:
                        
                        selectedDate.nextYear();
                        break;
                }
                System.out.println("action next:" + selectedDate);
            }
        });
        
        prevButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                switch(calendarView.getSelectedIndex()){
                    case 0:
                        selectedDate.prevDay(1);
                        break;
                    case 1:
                        selectedDate.prevDay(7);
                        break;
                    case 2:
                        selectedDate.prevDay(selectedDate.getDay());
                        break;
                    case 3:
                        selectedDate.prevYear();
                        break;
                }
                System.out.println("action prev:" + selectedDate);
            
            }
        
        });
        
        todayButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDate.setToToday();
                System.out.println("action today:" + selectedDate);
            }
        
        });
        
        eventButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                eventPanel.setVisible(true);
                eventButton.setEnabled(false);
                eventButton.setVisible(false);
            }
        
        });
        
        //slap all them views in the tabbed pane
        calendarView.add("Day", dayPanel);
        calendarView.add("Week", weekPanel);
        calendarView.add("Month", monthPanel);
        calendarView.add("Year", yearPanel);
        
        Box labelBox = new Box(BoxLayout.X_AXIS);
        labelBox.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        
        
        //day of week label
        dayOfWeek = new JLabel(SelectedDate.getInstance().getDayOfWeek().name);
        dayOfWeek.setFont(dayOfWeek.getFont().deriveFont(24.0f));
        dayOfWeek.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        
        labelBox.add(dayOfWeek);
        
        //date label
        date = new JLabel( "  "+ SelectedDate.getInstance().toString()+ "   ");

        date.setFont(date.getFont().deriveFont(18.0f));
        date.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        labelBox.add(date);
        
        //box holding all them buttons
        Box changeBox = Box.createHorizontalBox();
        changeBox.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        changeBox.add(prevButton,BorderLayout.NORTH);
        changeBox.add(todayButton,BorderLayout.NORTH);
        changeBox.add(nextButton,BorderLayout.NORTH);
        changeBox.add(Box.createHorizontalGlue());
        changeBox.add(labelBox);
        changeBox.add(eventButton);
        
        this.add(changeBox,BorderLayout.NORTH);
        this.add(calendarView);
        this.add(eventPanel,BorderLayout.EAST);
        eventPanel.setVisible(false);
    }
    
    

    @Override
    public void update() {
        //updates text at top of screen when selected date is changed
        dayOfWeek.setText( SelectedDate.getInstance().getDayOfWeek().name);
        date.setText("  " + SelectedDate.getInstance().toString()+ "   ");
        
        repaint();
    }

    
}
