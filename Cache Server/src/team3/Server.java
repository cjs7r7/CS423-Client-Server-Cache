package team3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	private static ServerSocket serverSocket;
	private static int portNumber = 80;

	public static void main(String[] args) throws IOException {
		
        Socket socket;
		ExecutorService executor = Executors.newFixedThreadPool(3);
		try {
			while (true) {
				System.out.println(Thread.currentThread().getName() + " - Server listening on port 80");
				serverSocket = new ServerSocket(portNumber);
				
				// wait for client
				socket = serverSocket.accept();
				System.out.println(Thread.currentThread().getName() + " - Client connected");

				// add to thread queue
				executor.submit(new Worker(socket)); // new thread
				serverSocket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
