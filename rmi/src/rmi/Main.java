package rmi;

import java.io.IOException;

import java.net.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import rmi.Lamport;
import rmi.LamportInt;
import rmi.Message;

public class Main {
	private static List<String> listSocket = new LinkedList<>();
	private static Lamport obj = new Lamport();

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		listSocket.add("192.168.1.191");
		System.setProperty("java.rmi.server.hostname", "192.168.1.191");

		Server();
		Thread.sleep(1);

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					obj.incrementTime();
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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

	private static void Client() {

		try {
			Registry registry = LocateRegistry.getRegistry(listSocket.get(0));
			LamportInt stub = (LamportInt) registry.lookup("LamportInt");
			obj.incrementTime();
			System.out.println(obj.getTime());
			Message response = stub.recive(obj.getTime());

			obj.setTime(response.getTimestep());
			System.out.println("response: " + response);

		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}

	}

	private static void Server() throws IOException {

		try {
			System.out.println(obj.getTime());
			LamportInt stub = (LamportInt) UnicastRemoteObject.exportObject(obj, 0);
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.bind("LamportInt", stub);
			System.err.println("Server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}