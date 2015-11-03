package org.bgl.robot;

public class InvalidXPositionException extends InvalidPositionException {
	private int position;
	public InvalidXPositionException(int position) {
		super(position, new Integer(position).toString() + ": Invalid  X Position");
		this.position = position;
	}
	@Override
	public int getPosition() {
		return position;
	}
}
