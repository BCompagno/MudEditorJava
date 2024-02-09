package mudEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomDataDisplay extends JFrame {
    private RoomData roomData;
    private JTextArea textArea;

    public RoomDataDisplay(RoomData roomData) {
        this.roomData = roomData;

        setTitle("Room Information Display");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton displayExitsButton = new JButton("Display Exits");
        JButton saveButton = new JButton("Save");

        displayExitsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayExitData();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveRoomData();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(displayExitsButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        displayRoomInformation();

        setVisible(true);
    }

    private void displayRoomInformation() {
        String information = "Number of rooms in this world: " + roomData.world.getTotalRooms() + "\n" +
        		"Room Vnum: " + String.valueOf(roomData.getVnum()) + "\n" +
                "Name: " + roomData.getName() + "\n" +
                "Description: " + roomData.getDescription() + "\n" +
                "Sector: " + roomData.getSector().toString() + "\n" +
                "Light: " + roomData.getLight().toString() + "\n" +
                "Flags: " + roomData.getFlags().toString() + "\n" +
                "Exits: \n";
        		for(ExitData exit: roomData.exits) {
        			information += exit.direction.toString() + "\n";
        		}
        textArea.setText(information);
    }

    private void displayExitData() {
        // Implement the logic to display ExitData here
        // You can create a new window, dialog, or update the current JTextArea with exit information
        // Example: new ExitDataDisplay(roomData.getExits());
    }

    private void saveRoomData() {
        // Implement the logic to save RoomData here
        // Example: roomData.save("path/to/save/file");
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            // Create an instance of your RoomData
//            RoomData roomData = new RoomData(/* pass required parameters */);
//
//            // Create and display the RoomDataDisplay window
//            new RoomDataDisplay(roomData);
//        });
//    } 
}
