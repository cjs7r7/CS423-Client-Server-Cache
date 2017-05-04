package team3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class Worker implements Runnable {

	private Socket socket;
	private static final String webServer = "127.0.0.1:8080"; // port 8080

	private Repository database = Repository.getInstance();

	public Worker(Socket socket) {
		this.socket = socket;

	}

	@Override
	public void run() {

		try {
			
			// pull the headers from the request
			System.out.println(Thread.currentThread().getName() + " - Calling getHeaders");
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			HashMap<String, String> hmap = Http.getHeaders(reader);
			
			System.out.println("done with headers");

			// display headers
			System.out.println(hmap.keySet().toString());
			
			System.out.println("augsdfuhasd");
			
			for (String key : hmap.keySet()) {
				System.out.println("key: " + key);
				System.out.println("value: " + hmap.get(key));
			}
			
			// pull specific file requested
			String uri;
			if(hmap.isEmpty()){
				uri = "/";
			}
			else{
				uri = hmap.get("request").split(" ")[1];
			}
			System.out.println("Requested: '"+uri+"'");
			
			// check cache for page uri
			if( database.hasKey(uri) ){
				System.out.println(Thread.currentThread().getName() + " - Pulling from cache");
			}
			else {
				
				System.out.println(Thread.currentThread().getName() + " - Calling getConnection");
				
				String value= Http.getConnection(webServer + uri);
				database.addEntry(uri, value);
			}
			
			//opening output
			System.out.println(Thread.currentThread().getName() + " - starting output stream");
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

			// setting the response headers
			System.out.println(Thread.currentThread().getName() + " - Setting Output Headers");
			Http.setResponseHeaders(writer);
			
			// returning file
			System.out.println(Thread.currentThread().getName() + " - Setting Output Body");
			
			writer.println(database.getEntry(uri));
			

			writer.flush();
			writer.close();
			reader.close();
			socket.close();
			
			System.out.println("Saved to database:");

			for(String i : database.getKeys()){
				System.out.println(i);
			}
			
			System.out.println(Thread.currentThread().getName() + " - Done");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
