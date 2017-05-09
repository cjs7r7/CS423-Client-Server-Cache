package team3;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Client extends JFrame {

	// Default
	private static final long serialVersionUID = 1L;

	// Variables
	URLConnection urlConnection;
	private static final String SERVERCON = "http://server.com";
	private final static String[] choices = { "index", "lorem.html", "test.html", "LargeAndRandom.txt", "ExtremelyLarge.txt" };
	private JComboBox<String> cb;
	private JTextArea fileLabel;

	public Client() {

		// Generate Screen
		Container contentPane = getContentPane();
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel label = new JLabel("Select a file to retreive: ");
		panel.add(label);

		// Retrieve what file?
		cb = new JComboBox<>(choices);
		panel.add(cb);

		// Add Get File Button
		JButton getFileButton = new JButton("Get File");
		getFileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("Button Pressed");
					String file = (String) cb.getSelectedItem();
					System.out.println("Retrieving File " + file);

					long startTime = 0, endTime = 0;
					
					if (file.equals("index"))
						urlConnection = (new URL(SERVERCON).openConnection());
					else
						urlConnection = (new URL(SERVERCON + "/" + file).openConnection());
					
					urlConnection.setConnectTimeout(5000);
					urlConnection.setRequestProperty("Connection", "close");
					//Header with Epoch Time 1
					urlConnection.setRequestProperty("Time1", ((Long)System.currentTimeMillis()).toString());

					//Print out Headers
					System.out.println("Response Headers:");
					for (Entry<String, List<String>> entry : urlConnection.getHeaderFields().entrySet()) {
						System.out.println(entry.getKey() + " : " + entry.getValue());
						if("x-epoch-request".equals(entry.getKey()))
							startTime = Long.parseLong(entry.getValue().get(0));
						else if("x-epoch-response".equals(entry.getKey()))
							endTime = Long.parseLong(entry.getValue().get(0));
					}
					
					if (startTime > 0 && endTime > 0)
						System.out.println("Time Took: " + (endTime - startTime) + "ms");

					//Get Response
					InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
					BufferedReader in = new BufferedReader(reader);
					String inputLine;

					StringBuffer sb = new StringBuffer();
					while ((inputLine = in.readLine()) != null) {
						sb.append(inputLine + "\n");
						//System.out.println(inputLine);
					}

					System.out.println("\nFile Received");
					System.out.println("Request took: " + (System.currentTimeMillis() - startTime) + "ms\n" );

					//Put the file to the screen
					fileLabel.setText(sb.toString());
					fileLabel.setSize(250, 250);
					setSize(500, 500);

					in.close();
					reader.close();
				} catch (IOException error) {
					System.out.println(error);
				}
			}

		});
		contentPane.add(getFileButton, BorderLayout.SOUTH);

		fileLabel = new JTextArea();
		fileLabel.setEditable(false);
		
		contentPane.add(new JScrollPane(fileLabel), BorderLayout.CENTER);

	}

	public static void main(String[] args) {
		Client frame = new Client();
		// frame.pack();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
