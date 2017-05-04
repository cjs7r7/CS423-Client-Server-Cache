package team3;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Client extends JFrame{

	//Default
	private static final long serialVersionUID = 1L;

	//Variables
	URLConnection urlConnection;
	private static final String SERVERCON = "Server.com/";
	JTextField ipTextField;
	JLabel imageLabel;

	public Client() {
		//On window close - close application
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		//Generate Screen
		Container contentPane = getContentPane();
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel label = new JLabel("Enter the name of the file to grab from the Server: ");
		panel.add(label);

		// Line to input
		ipTextField = new JTextField(20);
		ipTextField.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel.add(ipTextField);

		// Execute
		JButton getImageButton = new JButton("Get File");
		getImageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("Button Pressed");
					urlConnection = (new URL(SERVERCON).openConnection());
					//Add Headers Here
					urlConnection.addRequestProperty("Host", "localhost");
					urlConnection.addRequestProperty("Connection", "keep-alive");
					
					
					
					InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
					BufferedReader in = new BufferedReader(reader);
					String inputLine;

			        while ((inputLine = in.readLine()) != null) 
			            System.out.println(inputLine);
			        in.close();
					
					System.out.println("File Received");
			        
			        in.close();
					//server.close();
				} catch (IOException error) {
					System.out.println(error);
				}
			}

		});
		contentPane.add(getImageButton, BorderLayout.CENTER);
		
		imageLabel = new JLabel("", SwingConstants.CENTER);
        
        contentPane.add(imageLabel, BorderLayout.SOUTH);

		
	}
	
	
	
	
	public static void main(String[] args) {
		Client frame = new Client();
		frame.pack();
		frame.setVisible(true);
	}

}
