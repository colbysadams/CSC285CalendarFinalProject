/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author colbysadams
 */
public class EventPanel extends JPanel implements Observer, ActionListener {

    private static int offsetX = 5;

    private JButton saveEventButton, cancelButton;
    private JTextField eventNameField, eventLocationField;
    private JTextArea eventDescriptionArea;

    private JRadioButton[] eventTypeRadio;
    private ButtonGroup eventTypes;

    private JPanel subPanel;

    private JButton eventCreatorButton;

    private String[] comboBoxStrings = new String[]{"Never", "Yearly", "Monthly", "Weekly"};
    private JComboBox repeatComboBox;

    private int textFieldSize = 10;

    public EventPanel(JButton panelButton) {
        super();

        this.eventCreatorButton = panelButton;

        subPanel = new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel,BoxLayout.Y_AXIS));
        subPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        //subPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        saveEventButton = new JButton("Save");
        saveEventButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String eventName = eventNameField.getText();
                if (eventName.equals("")){
                    eventNameField.requestFocusInWindow();
                    return;
                }
                EventType eventType = null;
                for (int i = 0; i < eventTypeRadio.length; ++i) {
                    if (eventTypeRadio[i].isSelected()) {
                        eventType = EventType.get(i);
                        break;
                    }
                }
                CalendarEvent newEvent = new CalendarEvent(eventName, eventType);

                if (!eventLocationField.getText().equals("")) {
                    newEvent.setLocation(eventLocationField.getText());
                }
                if (!eventDescriptionArea.getText().equals("")) {
                    newEvent.setDescription(eventDescriptionArea.getText());
                }

                MasterSchedule.getInstance().addEventToSchedule(SelectedDate.getInstance(),
                        newEvent,
                        repeatComboBox.getSelectedIndex());
                cancelButton.doClick();
                SelectedDate.getInstance().notifyObservers();

            }

        });
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        eventNameField = new JTextField(textFieldSize);
        eventLocationField = new JTextField(textFieldSize);

        repeatComboBox = new JComboBox(comboBoxStrings);

        eventTypes = new ButtonGroup();
        eventTypeRadio = new JRadioButton[]{new JRadioButton(EventType.work.TYPE),
            new JRadioButton(EventType.family.TYPE),
            new JRadioButton(EventType.school.TYPE),
            new JRadioButton(EventType.social.TYPE),
            new JRadioButton(EventType.holiday.TYPE),
            new JRadioButton(EventType.other.TYPE)};

        eventTypeRadio[0].setSelected(true);

        eventDescriptionArea = new JTextArea(5, 5);
        eventDescriptionArea.setWrapStyleWord(true);
        eventDescriptionArea.setLineWrap(true);

        JScrollPane descriptionPane = new JScrollPane();
        descriptionPane.setViewportView(eventDescriptionArea);
//        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
//        subPanel.setAlignmentX(SwingConstants.LEFT);
        Box buttonBox = Box.createHorizontalBox();

        
        subPanel.add(createLabel("Event Name:"));
        subPanel.add(eventNameField);
        subPanel.add(createLabel("Location:"));
        subPanel.add(eventLocationField);
        subPanel.add(createLabel("Description:"));
        subPanel.add(descriptionPane);
        subPanel.add(createLabel("Repeats:"));
        subPanel.add(repeatComboBox);
        subPanel.add(createLabel("Type of Event:"));
        JPanel radioPanel = new JPanel(new GridLayout(3, 2));
        for (int i = 0; i < eventTypeRadio.length; ++i) {
            eventTypeRadio[i].setActionCommand(String.valueOf(i));
            eventTypes.add(eventTypeRadio[i]);
            radioPanel.add(eventTypeRadio[i]);
        }
        subPanel.add(radioPanel);
        buttonBox.add(saveEventButton);
        buttonBox.add(cancelButton);
        subPanel.add(buttonBox);

        this.add(subPanel);

    }
    
    public Component createLabel(String label){
        Box labelBox = Box.createHorizontalBox();
        labelBox.add(new JLabel(label));
        labelBox.add(Box.createHorizontalGlue());
        return labelBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        eventNameField.setText("");
        eventLocationField.setText("");
        eventDescriptionArea.setText("");
        repeatComboBox.setSelectedIndex(0);
        eventTypeRadio[0].setSelected(true);
        this.setVisible(false);
        eventCreatorButton.setVisible(true);
    }

    @Override
    public void update() {

        this.revalidate();
        this.repaint();
    }

}
