package org.bgl.robot;

public class PositionOutOfBoundException extends CommandParameterException {
	public PositionOutOfBoundException() {
		super("Supplied position is outside the boundary. Board size is " + 
	          new Integer(TableSurface.XMAX).toString() + " x " +
	          new Integer(TableSurface.YMAX).toString());
	}	
}
