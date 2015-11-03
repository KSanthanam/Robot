package org.bgl.robot;

public class InvalidYPositionException  extends InvalidPositionException {
	private int position;
	public InvalidYPositionException(int position)  {
		super(position, new Integer(position).toString() + ": Invalid  Y Position");
		this.position = position;
	}
	@Override
	public int getPosition() {
		return position;
	}
}
