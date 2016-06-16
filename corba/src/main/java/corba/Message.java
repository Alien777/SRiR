package corba;

import java.io.Serializable;

public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Message(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Message [content=" + content + ", timestep=" + timestep + "]";
	}

	public Message(String content, Integer timestep) {
		this.content = content;
		this.timestep = timestep;
	}

	String content;
	Integer timestep;

	public Integer getTimestep() {
		return timestep;
	}

	public void setTimestep(Integer timestep) {
		this.timestep = timestep;
	}

}