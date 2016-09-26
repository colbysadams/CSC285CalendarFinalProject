/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * This panel is used to display the details of events on the selected date
 *
 * @author colbysadams
 */
public class EventDetailPanel extends ObserverPanel implements ActionListener
{

    private static CalendarEvent selectedEvent;
    private static JButton editEventButton;
    private static ArrayList<EventDetailPanel> everyInstance = new ArrayList();
    private static boolean visible = true;
    private ArrayList<CalendarEvent> daysEvents;
    private JPanel buttonPanel;
    private JPanel detailPanel;
    private JLabel nameLabel;
    private JLabel timeLabel;
    private JLabel typeLabel;
    private JLabel locationLabel;
    private JLabel descriptionLabel;
    private JLabel repeatingLabel;
    private JLabel selectEventLabel;

    public EventDetailPanel()
    {

        super();
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(170, this.getHeight()));
        SelectedDate.getInstance().addObserver(this);
        selectedEvent = null;
        buttonPanel = new JPanel(new GridLayout(0, 1));
        detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));

        everyInstance.add(this);

        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(detailPanel, BorderLayout.CENTER);

        selectEventLabel = new JLabel("<html><h3>Select Event:</html></h3>");
        selectEventLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel = new JLabel();
        nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 20));
        typeLabel = new JLabel();
        typeLabel.setFont(new Font(nameLabel.getFont().getName(), Font.BOLD, 16));
        timeLabel = new JLabel();
        timeLabel.setFont(new Font(nameLabel.getFont().getName(), Font.BOLD, 14));
        repeatingLabel = new JLabel();
        locationLabel = new JLabel();
        descriptionLabel = new JLabel();

        detailPanel.add(nameLabel);
        detailPanel.add(Box.createVerticalStrut(10));
        detailPanel.add(typeLabel);
        detailPanel.add(Box.createVerticalStrut(5));
        detailPanel.add(timeLabel);
        detailPanel.add(Box.createVerticalStrut(3));
        detailPanel.add(repeatingLabel);
        detailPanel.add(Box.createVerticalStrut(3));
        detailPanel.add(locationLabel);
        detailPanel.add(Box.createVerticalStrut(3));
        detailPanel.add(descriptionLabel);

        addButtonsToPanel();
    }

    /**
     *
     * Edit event button is only enabled if an event is selected
     * <p>
     * @param editEventButton
     */
    public static void addEditEventButton(JButton editEventButton)
    {
        EventDetailPanel.editEventButton = editEventButton;
    }

    /**
     *
     * if there are no events on the selected date, then this panel will
     * not be shown.
     * <p>
     * @param visible
     */
    public static void makeVisible(boolean visible)
    {
        EventDetailPanel.visible = visible;
        for (EventDetailPanel e : everyInstance)
        {
            e.setVisible(visible);
        }
    }

    /**
     *
     * if user clicks on an event in the panel, they can choose to edit that
     * event
     * <p>
     * @return the selectedEvent
     */
    public static CalendarEvent getSelectedEvent()
    {
        return selectedEvent;
    }

    /**
     * when an event is clicked on, display details about that event
     * <p>
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        int n = Integer.parseInt(e.getActionCommand());
        selectedEvent = daysEvents.get(n);

        editEventButton.setEnabled(true);

        nameLabel.setText(selectedEvent.getName());
        timeLabel.setText(selectedEvent.getTimeObject().getClock());
        typeLabel.setForeground(selectedEvent.getEventType().COLOR);
        typeLabel.setText(selectedEvent.getEventType().TYPE);
        repeatingLabel.setText(selectedEvent.getRepeatingString());
        locationLabel.setText(selectedEvent.getLocationLabel());
        descriptionLabel.setText(selectedEvent.getDescriptionLabel());

        detailPanel.revalidate();
        detailPanel.repaint();
        //System.out.println("EventSelected " + selectedEvent);
    }

    /**
     * implements method required by observer interface
     * when a new date is selected, new events are shown
     */
    @Override
    public void update()
    {
        this.setVisible(visible);
        addButtonsToPanel();
    }

    /**
     * replaces events when selected date is changed
     */
    private void addButtonsToPanel()
    {
        buttonPanel.removeAll();
        selectedEvent = null;
        editEventButton.setEnabled(false);
        nameLabel.setText("");
        timeLabel.setText("");
        typeLabel.setText("");
        repeatingLabel.setText("");
        locationLabel.setText("");
        descriptionLabel.setText("");
        detailPanel.repaint();

        daysEvents = MasterSchedule.getInstance().getDaysEvents(SelectedDate.getInstance());
        JButton eventButton = null;

        //Box buttonBox;
        if (daysEvents.size() == 0)
        {
            eventButton = new JButton("<html><h2>No Events</html></h2>");
            eventButton.setEnabled(false);
            buttonPanel.add(eventButton);
        } else
        {

            buttonPanel.add(selectEventLabel);
            for (int i = 0; i < daysEvents.size(); ++i)
            {
                eventButton = new JButton(daysEvents.get(i).getShortLabel());
                eventButton.setActionCommand(String.valueOf(i));
                eventButton.addActionListener(this);
                buttonPanel.add(eventButton);

            }
            if (daysEvents.size() == 1)
            {
                eventButton.doClick();
            }
        }
    }
}
