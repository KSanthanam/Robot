package org.bgl.robot;

public class InvalidPositionException extends InvalidStateException {	
	private int position;
	public InvalidPositionException(int position) {
		super("Invalid Position");
		this.position = position;
	}
	public InvalidPositionException(int position, String message) {
		super(message);
		this.position = position;
	}
	public int getPosition() {
		return position;
	}

}
