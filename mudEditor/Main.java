package mudEditor;
/*
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	private static final String COMMAND_FILE = "commands.txt";
	private static final String WORLD_LIST = "worlds.txt";
	private static final String WORLD_DIR = "worlds/";
	private static final String MESSAGE_FILE = "messages.txt";

	private static Map<String, CommandData> commands = new HashMap<>();
	private static Map<String, CommandData> directions = new HashMap<>();
	private static Map<String, String> messagemap = new HashMap<>();

	public static void main(String[] args) {
		loadCommands();
		loadDirections();
		loadMessages();
		loadWorlds();
	}

	// Create a file editor window
	SwingUtilities.invokeLater(()-> createFileEditor1());
}

	private static void createFileEditor() {
		JFrame editorFrame = new JFrame("File Editor");
		editorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		editorFrame.setSize(600, 400);

		JTextArea textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);

		JPanel panel = new JPanel();
		JButton openButton = new JButton("Open File");
		JButton saveButton = new JButton("Save File");

		openButton.addActionListener(e -> openFile(textArea));
		saveButton.addActionListener(e -> saveFile(textArea));

		panel.add(openButton);
		panel.add(saveButton);

		editorFrame.add(scrollPane, BorderLayout.CENTER);
		editorFrame.add(panel, BorderLayout.SOUTH);

		editorFrame.setVisible(true);
	}

	private static void openFile(JTextArea textArea) {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
				StringBuilder content = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					content.append(line).append("\n");
				}
				textArea.setText(content.toString());
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private static void saveFile(JTextArea textArea) {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showSaveDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
				writer.write(textArea.getText());
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private static void loadCommands() {
		System.out.println("Loading Commands...");
		try (Scanner scanner = new Scanner(new FileReader(COMMAND_FILE))) {
			while (scanner.hasNext()) {
				String word = scanner.next().trim();
				if (word.equals("#COMMAND")) {
					CommandData command = new CommandData(scanner);
				} else if (word.equals("#END")) {
					break;
				} else {
					System.err.println("loadCommands: " + word);
				}
			}
		} catch (IOException e) {
			System.err.println("Could not open command file: " + COMMAND_FILE);
		}
		System.out.println("Commands Loaded!");
	}

	private static void loadDirections() {
		// Assuming commands is a Map<String, CommandData> defined somewhere
		Map<String, CommandData> commands = new HashMap<>();
		// Assuming commandIter is an Iterator<CommandData> defined somewhere
		Map<String, CommandData> directions = new HashMap<>();

		// Populate the commands map before calling this method

		String[] directionCommands = { "north", "south", "east", "west", "northeast", "northwest", "southeast",
				"southwest", "up", "down" };

		for (String directionCommand : directionCommands) {
			CommandData command = commands.get(directionCommand);
			directions.put(directionCommand.substring(0, 1), command);
		}
	}

	private static void loadMessage(BufferedReader reader) throws IOException {
		String key = reader.readLine().trim();
		StringBuilder textBuilder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null && !line.equals("~")) {
			textBuilder.append(line);
		}
		String text = textBuilder.toString();
		if (!key.isEmpty()) {
			messagemap.put(key, text);
		}
	}

	private static void loadMessages() {
		System.out.println("Loading Messages...");
		try (BufferedReader reader = new BufferedReader(new FileReader(MESSAGE_FILE))) {
			String word;
			while ((word = reader.readLine()) != null) {
				if (word.equals("#MESSAGE")) {
					loadMessage(reader);
				} else if (word.equals("#END")) {
					break;
				} else {
					System.err.println("loadMessages: " + word);
				}
			}
		} catch (IOException e) {
			System.err.println("Could not open messages file: " + MESSAGE_FILE);
		}
		System.out.println("Messages Loaded!");
	}

	private static void loadWorlds() {
		System.out.println("Loading Worlds...");
		try (BufferedReader reader = new BufferedReader(new FileReader(WORLD_LIST))) {
			String word;
			while ((word = reader.readLine()) != null) {
				word = word.trim();
				if (word.equals("END")) {
					break;
				} else {
					try (BufferedReader wfp = new BufferedReader(new FileReader(WORLD_DIR + word + ".world"))) {
						WorldData world = new WorldData(wfp);
					} catch (IOException e) {
						System.err.println("Could not open world: " + WORLD_DIR + word + ".world");
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Could not open world list: " + WORLD_LIST);
		}
		System.out.println("Worlds Loaded!");
	}
}

abstract class CommandData {
	
	public CommandData( Scanner scanner) {
		
	}
}

abstract class WorldData {
	
	public WorldData( Scanner scanner) {
		
	}
}*/
