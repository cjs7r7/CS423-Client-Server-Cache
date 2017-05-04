package team3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class Http {

	/**
	 * Gets a connection to the host and returns a List<String> containing the
	 * text returned from host.
	 * 
	 * @param host
	 * @return List<String>
	 * @throws IOException
	 */
	public static String getConnection(String host) throws IOException {

		StringBuffer page = new StringBuffer();

		URL hostUrl = new URL("http://" + host);
		URLConnection conn = hostUrl.openConnection();
		conn.setRequestProperty("Connection", "close");
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		String line;
		while ((line = reader.readLine()) != null) {
			page.append(line + "\r\n");
		}
		reader.close();

		return page.toString();
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

		while (!(line = reader.readLine()).isEmpty()) {

			if (line.contains(":")) {

				key = line.substring(0, line.indexOf(":"));
				value = line.substring(line.indexOf(":"));

				hmap.put(key, value);
			} else {
				hmap.put("request", line);
			}
		}

		return hmap;
	}

	public static void setResponseHeaders(PrintWriter writer) {

		writer.println("HTTP/1.1 200");
		writer.println("Connection: close");
		writer.println("Content-Type: text/html; charset=utf-8");
		writer.println("Cache-Control: no-store, no-cache, must-revalidate, max-age=0");
		writer.println("Pragma: no-cache");
		writer.println("X-nananana: Batcache");
		writer.println("");
	}
}
