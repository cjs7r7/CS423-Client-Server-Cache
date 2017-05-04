package team3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

public class Worker implements Runnable {

	private Socket socket;
	private static String webServer = "127.0.0.1:8080"; // port 8080

	private static List<String> page;

	public Worker(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {

		try {

			System.out.println(Thread.currentThread().getName() + " - Calling getHeaders");
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			HashMap<String, String> hmap = Http.getHeaders(reader);

			for (String key : hmap.keySet()) {
				System.out.println("key: " + key);
				System.out.println("value: " + hmap.get(key));
			}

			if (page == null || page.isEmpty()) {
				System.out.println(Thread.currentThread().getName() + " - Calling getConnection");
				page = Http.getConnection(webServer);
			}
			else{
				System.out.println(Thread.currentThread().getName() + " - Pulling from cache");
			}

			System.out.println(Thread.currentThread().getName() + " - starting output stream");
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

			
			System.out.println(Thread.currentThread().getName() + " - Setting Output Headers");
			Http.setResponseHeaders(writer);

			System.out.println(Thread.currentThread().getName() + " - Setting Output Body");
			for (String line : page) {
				writer.println(line);
			}

			writer.close();
			socket.close();

			System.out.println(Thread.currentThread().getName() + " - Done");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
