package corba;

import java.util.concurrent.atomic.AtomicInteger;

import org.omg.CORBA.ORB;

import LamportApp.LamportIntPOA;

public class Lamport extends LamportIntPOA {
	private AtomicInteger time = new AtomicInteger();

	private ORB orb;

	public void setORB(ORB orb2) {
		orb = orb2;
	}
	
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
	public int receive(int time2) {
		setTime(Math.max(time2, time.get()) + 1);
		return getTime();
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
}
