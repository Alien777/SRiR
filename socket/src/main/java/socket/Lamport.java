package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class Lamport {
	private AtomicInteger time = new AtomicInteger();

	public int getTime() {
		return time.get();
	}

	private void setTime(int time) {
		this.time.set(time);
	}

	public void incrementTime() {
		time.incrementAndGet();
	}

	public Lamport() {
		this.time.set(0);
	}

	public void send(Socket socket, Message message) {
		incrementTime();
		message.setTimestep(getTime());
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("Send :" +getTime());
					OutputStream os = socket.getOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(os);
					oos.writeObject(message);
					oos.close();
					os.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void receive(Socket socket) {
		new Thread(new Runnable() {
			@Override
			public void run() {

				InputStream is = null;
				ObjectInputStream ois = null;
				try {

					is = socket.getInputStream();
					ois = new ObjectInputStream(is);
					Message m = (Message) ois.readObject();
					
					if (m != null) {
						setTime(Math.max(m.getTimestep(), time.get()) + 1);
						System.out.println("Recive :" +getTime());
					}
					

				} catch (ClassNotFoundException | IOException e) {

					e.printStackTrace();
				} finally {
					try {
						ois.close();
						is.close();
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		}).start();

	}

}
