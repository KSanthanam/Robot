package org.bgl.robot;

public class InvalidDirectionException extends CommandParameterException {
	public InvalidDirectionException() {
		super("Invalid direction supplied. Valid directions are NORTH, EAST, SOUTH, WEST");
	}
}
