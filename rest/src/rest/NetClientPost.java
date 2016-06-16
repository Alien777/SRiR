package rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetClientPost {
        private static Lamport lamport;
	// http://localhost:8080/RESTfulExample/json/product/post
	public static void main(String[] args) {
            lamport = new Lamport();
            int n = 1000;
            Long start, sum=0L;
            for(int i = 0; i < n; i++) {
                start = System.currentTimeMillis();
                update();
                sum += System.currentTimeMillis() - start;
            }
            System.out.println("Czas wysłania " + n + ": " + (sum/1000.0) + "s");
            System.out.println("Średni czas wysyłania " + (sum / n / 1000.0) + "s");
	}
        
        public static void update() {

	  try {

		URL url = new URL("http://localhost:8080/GetSomRest/webresources/json/lamport/post");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "text/html;charset=utf8");
                
                lamport.incrementTime();
		System.out.println("Sent time: " + lamport.getTime());
                
		String input = String.valueOf(lamport.getTime());

		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Faileds : HTTP error code : "
				+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output;
                
		while ((output = br.readLine()) != null) {
                        lamport.setTime(Math.max(lamport.getTime(), Integer.parseInt(output))+1);
			System.out.println("Get time: " + lamport.getTime());
		}

		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	 }
        }

}
