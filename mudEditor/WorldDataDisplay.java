package mudEditor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WorldDataDisplay extends JFrame {
    private WorldData worldData;
    private JTextField nameField;
    private JTextField northField;
    private JTextField eastField;
    private JTextField southField;
    private JTextField westField;
    private JTextField totalRoomsField;

    public WorldDataDisplay(WorldData worldData) {
        this.worldData = worldData;

        setTitle("World Information Display For " + worldData.getName());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel inputPanel = new JPanel(new GridLayout(6, 1)); // Changed to a single column layout

        nameField = new JTextField(worldData.getName());
        northField = new JTextField(String.valueOf(worldData.getNorth()));
        eastField = new JTextField(String.valueOf(worldData.getEast()));
        southField = new JTextField(String.valueOf(worldData.getSouth()));
        westField = new JTextField(String.valueOf(worldData.getWest()));
        totalRoomsField = new JTextField(String.valueOf(worldData.getTotalRooms()));

        
        addRowToPanel(inputPanel, "World Name: ", nameField);
        addRowToPanel(inputPanel, "North:      ", northField);
        addRowToPanel(inputPanel, "East:       ", eastField);
        addRowToPanel(inputPanel, "South:      ", southField);
        addRowToPanel(inputPanel, "West:       ", westField);
        addRowToPanel(inputPanel, "Total Rooms:", totalRoomsField);

        JButton displayRoomsButton = new JButton("Display Rooms");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });
        
        displayRoomsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayRoomsInformation();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(displayRoomsButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addRowToPanel(JPanel panel, String label, JTextField textField) {
        JPanel rowPanel = new JPanel(new BorderLayout());

        rowPanel.add(new JLabel(label), BorderLayout.WEST);
        rowPanel.add(textField, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveField(textField));

        rowPanel.add(saveButton, BorderLayout.EAST);

        panel.add(rowPanel);
    }

    private void saveField(JTextField textField) {
        // Implement the logic to save the field value
        if (textField == nameField) {
            // Handle saving for the name field
        	if(worldData.getName().equals("NewWorld") && nameField.getText().equals("NewWorld") ) {
                JOptionPane.showMessageDialog(this, "Cannot save the default world name.", "Error", JOptionPane.INFORMATION_MESSAGE);
        	}else if(WorldData.findWorld(nameField.getText()) != null){
                JOptionPane.showMessageDialog(this, "This world already exists.", "Error", JOptionPane.INFORMATION_MESSAGE);
        	}else if(WorldData.findWorld(worldData.getName()) != null){
                JOptionPane.showMessageDialog(this, "No options to rename worlds yet.", "Error", JOptionPane.INFORMATION_MESSAGE);
        	}else if(nameField.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this, "You must enter a name.", "Error", JOptionPane.INFORMATION_MESSAGE);

        	}
        	else {
        		//save the list of worlds
	            worldData.setName(nameField.getText());
	            setTitle("World Information Display For" + worldData.getName());
	            
        		worldData.initialize();
        		worldData.createFirstRoom();
                worldData.writeList();
                // Save WorldData to file or database
                worldData.save();
                
                JOptionPane.showMessageDialog(this, "Changes saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        	}		
        } else if (textField == northField) {
            // Handle saving for the north field
            if(worldData.pushNorth(Integer.parseInt(northField.getText()))) {
            	worldData.save();
                JOptionPane.showMessageDialog(this, "Changes saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Must be a number greater than current", "Error", JOptionPane.INFORMATION_MESSAGE);
                northField.setText(String.valueOf(worldData.getNorth()));
            }
        } else if (textField == eastField) {
        	if(worldData.pushEast(Integer.parseInt(eastField.getText()))) {
            	worldData.save();
                JOptionPane.showMessageDialog(this, "Changes saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Must be a number greater than current", "Error", JOptionPane.INFORMATION_MESSAGE);
                southField.setText(String.valueOf(worldData.getEast()));
            }
        } else if (textField == southField) {
        	if(worldData.pushSouth(Integer.parseInt(southField.getText()))) {
            	worldData.save();
                JOptionPane.showMessageDialog(this, "Changes saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Must be a number negative and less than current", "Error", JOptionPane.INFORMATION_MESSAGE);
                southField.setText(String.valueOf(worldData.getSouth()));
            }
        } else if (textField == westField) {
        	if(worldData.pushWest(Integer.parseInt(westField.getText()))) {
            	worldData.save();
                JOptionPane.showMessageDialog(this, "Changes saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Must be a number negative and less than current", "Error", JOptionPane.INFORMATION_MESSAGE);
                southField.setText(String.valueOf(worldData.getWest()));
            }
        } else if (textField == totalRoomsField) {
            // Handle saving for the totalRooms field
            // Example: worldData.setTotalRooms(Integer.parseInt(totalRoomsField.getText()));
        }
        
        totalRoomsField.setText(String.valueOf(worldData.getTotalRooms()));


    }

    private void displayRoomsInformation() {
        RoomDataDisplay roomDisplay = new RoomDataDisplay(worldData.findRoomMap(Constants.SKY_LEVEL,0,0));
    }

   // private void saveWorldData() {
   //     // Save changes to WorldData based on edited fields
   // }
    
    private void onClose() {
        // Clean-up tasks before closing the window
        // Remove WorldData instance from the list, if needed
        

        // Close the window
        dispose();
    }
    
/*
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java WorldDataDisplay <worldDataName>");
            System.exit(1);
        }

        // Parse command-line arguments or configuration parameters to create WorldData
        WorldData worldData = createWorldDataFromArgs(args);

        SwingUtilities.invokeLater(() -> {
            // Create and display the WorldDataDisplay window
            new WorldDataDisplay(worldData);
        });
    }

    private static WorldData createWorldDataFromArgs(String[] args) {
        // Implement the logic to create a WorldData instance from command-line arguments
        // Example: Parse args and create a WorldData instance
        return new WorldData();
    }*/
    
}
