package team3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

public class Worker implements Runnable{
	
	private Socket socket;
	private static String webServer = "127.0.0.1:8080";	// port 8080
	
	public Worker(Socket socket){
		this.socket = socket;
	}

	@Override
	public void run() {
		
		try {
			
			System.out.println(Thread.currentThread().getName() + " - Calling getHeaders");
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			HashMap<String, String> hmap = Http.getHeaders(reader);
			
			System.out.println(Thread.currentThread().getName() + " - Calling getConnection");
			List<String> page = Http.getConnection(webServer);
			
			System.out.println(Thread.currentThread().getName() + " - starting output stream");
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			System.out.println(Thread.currentThread().getName() + " - Setting Output Headers");
			Http.setResponseHeaders(writer);
			
			System.out.println(Thread.currentThread().getName() + " - Setting Output Body");
			for(String line : page){
				writer.println(line);
			}
			
			socket.close();
			
			System.out.println(Thread.currentThread().getName() + " - Done");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
