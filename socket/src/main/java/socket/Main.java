package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
public class Main {
	private static List<String> listSocket = new LinkedList<>();
	private static Lamport obj;
	private final static int port = 9999;
	private static ServerSocket socketServer;
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		listSocket.add("127.0.0.1");

		obj = new Lamport();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Server();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
		Thread.sleep(500);
		new Thread(new Runnable() {

			@Override
			public void run() {
				int n = 500;
				long start = 0;
				long sum = 0;
				 
		 
				Message mes = new Message("");
				for (int i = 0; i < n; i++) {
					start = System.currentTimeMillis();
					try {
						obj.send(new Socket("localhost",port), mes);
						Thread.sleep(2);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					sum += System.currentTimeMillis() - start;
				}
				System.out.println("Czas wysylania: " + n + " : " + (sum / 1000.0) + " s");
				System.out.println("Czas sredni wyslania: " + (sum / n) + "ms");
			}
		}).start();
		Thread.sleep(100000);

		AtomicInteger a = new AtomicInteger(0);
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					if (a.get() != obj.getTime()) {
						System.out.println("Time:" + obj.getTime());
						a.set(obj.getTime());
					}

					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	private static void Server() throws IOException {
		socketServer = new ServerSocket(port);
		while (true) {
			Socket s = socketServer.accept();
			obj.receive(s);
		}

	}
}
