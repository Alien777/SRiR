package rest;

import java.util.concurrent.atomic.AtomicInteger;

public class Lamport {
	private static final AtomicInteger time = new AtomicInteger(0);

	public static int getTime() {
		return Lamport.time.get();
	}

	public static void setTime(int time) {
		Lamport.time.set(time);
	}

	public static void incrementTime() {
		Lamport.time.incrementAndGet();
	}

	public static int recive(int senderTime) {
		setTime(Math.max(senderTime, time.get()) + 1);
		return Lamport.getTime();
	}

}