package mudEditor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

public class StartWindow extends JFrame {
	private List<String> worldList;
	private JList<String> worldJList;
	private JTextArea additionalInfoTextArea;

	public StartWindow() {
		setTitle("Choose or Create a World");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Read world list from file
		worldList = readWorldListFromFile(Constants.WORLD_LIST);

		if (!worldList.isEmpty()) {
			loadWorlds(worldList);
		}

		// Create JList with world names
		List<String> worldNames = WorldData.worlds.stream().map(WorldData::getName).collect(Collectors.toList());
		worldJList = new JList<>(worldNames.toArray(new String[0]));
		worldJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		worldJList.addListSelectionListener(e -> {
			// Update additional information when a world is selected
			updateAdditionalInfo(worldJList.getSelectedValue());
		});

		// Create "Open World" button
		JButton openWorldButton = new JButton("Open World");
		openWorldButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					openSelectedWorld();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// Create "Create New World" button
		JButton createWorldButton = new JButton("Create New World");
		createWorldButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createNewWorld();
			}
		});
		// Create "Refresh" button
		JButton refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshWorldList();
			}
		});

		// Add the refresh button to the button panel

		// Create layout
		JPanel buttonPanel = new JPanel(new FlowLayout()); // Use FlowLayout for the button panel
		buttonPanel.add(openWorldButton);
		buttonPanel.add(createWorldButton);
		buttonPanel.add(refreshButton);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(worldJList),
				createAdditionalInfoPanel());
		splitPane.setDividerLocation(200); // Adjust this value to set initial divider location

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(splitPane, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH); // Add the button panel to the main panel

		add(mainPanel);

		setVisible(true);
	}

	private void loadWorlds(List<String> listOfWorlds) {
		for (String worldName : listOfWorlds) {

			try (BufferedReader reader = new BufferedReader(
					new FileReader(Constants.WORLD_DIR + worldName + Constants.WORLD_EXT))) {
				new WorldData(reader);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private void refreshWorldList() {
		// Reload world list from file and update JList
		worldList = readWorldListFromFile(Constants.WORLD_LIST);

		// Update JList model with the new world names
		List<String> worldNames = WorldData.worlds.stream().map(WorldData::getName).collect(Collectors.toList());
		worldJList.setListData(worldNames.toArray(new String[0]));

	}

	private JPanel createAdditionalInfoPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		additionalInfoTextArea = new JTextArea();
		additionalInfoTextArea.setEditable(false);
		panel.add(new JScrollPane(additionalInfoTextArea), BorderLayout.CENTER);
		return panel;
	}

	private void updateAdditionalInfo(String selectedWorldName) {
		if (selectedWorldName != null) {
			if (!WorldData.worlds.isEmpty()) {
				WorldData selectedWorld = WorldData.findWorld(selectedWorldName);
				additionalInfoTextArea.setText(getAdditionalInfoText(selectedWorld));
			} else {
				additionalInfoTextArea.setText("updateAdditionalInfo loading world information.");
			}
		} else {
			additionalInfoTextArea.setText("Click on a world in the list to display information.");
		}
	}

	private String getAdditionalInfoText(WorldData world) {
		// Customize this based on your WorldData class structure
		StringBuilder infoText = new StringBuilder();
		infoText.append("Total number of Worlds: ").append(WorldData.worlds.size()).append("\n\n");

		infoText.append("World Information:\n");
		infoText.append("Name: ").append(world.getName()).append("\n\n");

		infoText.append("Borders:\n");
		infoText.append("North: ").append(world.getNorth()).append("\n");
		infoText.append("South: ").append(world.getSouth()).append("\n");
		infoText.append("East: ").append(world.getEast()).append("\n");
		infoText.append("West: ").append(world.getWest()).append("\n\n");

		infoText.append("Total Rooms: ").append(world.getTotalRooms()).append("\n");
		// infoText.append("Total Mobs: ").append(world.getTotalMobs()).append("\n");
		// Add more information as needed

		return infoText.toString();
	}

	private List<String> readWorldListFromFile(String filename) {
		List<String> list = new ArrayList<>();
		File file = new File(filename);

		if (file.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
				String line;
				while ((line = reader.readLine()) != null) {
					list.add(line.trim());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				file.createNewFile();
	            System.out.println("File does not exist. Created a new file: " + file.getAbsolutePath());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;

	}

	private void openSelectedWorld() throws Exception {

		int selectedIndex = worldJList.getSelectedIndex();

		if (selectedIndex != -1) {
			String selectedWorldName = worldList.get(selectedIndex);

			// Load and display the selected world
			WorldData selectedWorld = WorldData.findWorld(selectedWorldName);
			if (selectedWorld != null) {

				// Open the WorldDataDisplay window for the selected world
				new WorldDataDisplay(selectedWorld);
			} else {
				JOptionPane.showMessageDialog(this, "Failed to load selected world.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Please select a world.", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void createNewWorld() {

		WorldData world = new WorldData();
		WorldDataDisplay worldDisplay = new WorldDataDisplay(world);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new StartWindow();
		});
	}
}
