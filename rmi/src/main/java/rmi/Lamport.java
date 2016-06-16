package rmi;

import java.rmi.RemoteException;
 

import java.util.concurrent.atomic.AtomicInteger;

public class Lamport implements LamportInt {
	private AtomicInteger time = new AtomicInteger();

	public int getTime() {
		return time.get();
	}

	public void setTime(int time) {
		this.time.set(time);
	}

	public void incrementTime() {
		time.incrementAndGet();
	}

	public Lamport() {
		this.time.set(0);
	}

	@Override
	public Message recive(int senderTime) throws RemoteException {
		setTime(Math.max(senderTime, time.get()) + 1);
		Message m = new Message("message");
		m.setTimestep(getTime());
		return m;
	}

}