package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AtozMp3FeedTest {


	public static void main(String[] args) {
		try {
			String urlString = "http://feeds.feedburner.com/Atozmp3dotnet?format=xml";
			URL url = new URL(urlString);
			HttpURLConnection connection = null;
			try {
				connection = (HttpURLConnection) url.openConnection();
				connection.connect();
				InputStreamReader inStream = new InputStreamReader(connection.getInputStream());
				BufferedReader buff = null;
				try {
					buff = new BufferedReader(inStream);
					while (true) {
						String nextLine = buff.readLine();
						if (nextLine != null) {
							System.out.println(nextLine);
						} else {
							break;
						}
					}
				} finally {
					if (buff != null) {
						buff.close();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				connection.disconnect();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
}
