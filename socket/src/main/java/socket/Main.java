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
