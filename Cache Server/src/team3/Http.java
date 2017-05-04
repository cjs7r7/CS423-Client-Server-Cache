package team3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Http {

	/**
	 * Gets a connection to the host and returns a List<String> containing the text returned from host.
	 * @param host
	 * @return List<String>
	 * @throws IOException
	 */
	public static List<String> getConnection(String host) throws IOException {

		List<String> page = new ArrayList<String>();

		URL hostUrl = new URL("http://"+host);
		URLConnection conn = hostUrl.openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		String line;
		while ((line = reader.readLine()) != null) {
			page.add(line);
		}
		reader.close();
		
		return page;
	}

	/**
	 * Reads the headers from an incomming request and returns a Hashmap of the
	 * header, content
	 * 
	 * @param socket
	 * @return returns
	 * @throws IOException
	 */
	public static HashMap<String, String> getHeaders(BufferedReader reader) throws IOException {
		HashMap<String, String> hmap = new HashMap<String, String>();

		String line;
		String key;
		String value;

		if (reader.ready()) {
			line = reader.readLine();
			while (!line.isEmpty()) {

				if (line.contains(":")) {

					key = line.substring(0, line.indexOf(":"));
					value = line.substring(line.indexOf(":"));

					hmap.put(key, value);
				} else {
					hmap.put("request", line);
				}
				line = reader.readLine();

			}
		}

		return hmap;
	}
	
	public static void setResponseHeaders(PrintWriter writer){
		
		writer.println("HTTP/1.1 200");
		writer.println("Content-Type: text/html; charset=utf-8");
		writer.println("");
		
	}
}
