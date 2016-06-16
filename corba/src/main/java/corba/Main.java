package corba;

import java.io.IOException;

import java.net.UnknownHostException;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import corba.Lamport;

public class Main {
	private static List<String> listSocket = new LinkedList<>();
	private static Lamport obj = new Lamport();

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		listSocket.add("127.0.0.1");

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Server(args, obj);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		Thread.sleep(800);
		new Thread(new Runnable() {

			@Override
			public void run() {
				int n = 1000;
				long start = 0;
				long sum = 0;

				for (int i = 0; i < n; i++) {
					start = System.currentTimeMillis();
					Client(args, obj);
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

	private static void Client(String[] args, Lamport lamport) {

		String[] args1 = { "-ORBInitialPort", "1050", "-ORBInitialHost", "localhost" };
		new Client().start(args1, lamport);

	}

	private static void Server(String[] args, Lamport lamport) throws IOException {
		String[] args1 = { "-ORBInitialPort", "1050", "-ORBInitialHost", "localhost" };
		new HelloServer().start(args1, lamport);
	}
}