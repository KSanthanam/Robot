package org.bgl.robot;

public class Sensors {
	private enum Sensor {
	    FRONT(0b00000001), LEFT(0b00000010), RIGHT(0b00000100);				
	    private int numVal;
	    Sensor(int numVal) {
	        this.numVal = numVal;
	    }
	    public int getNumVal() {
	        return numVal;
	    }
	}	
	public static final int FRONT = Sensor.FRONT.getNumVal();
	public static final int LEFT = Sensor.LEFT.getNumVal();
	public static final int RIGHT = Sensor.RIGHT.getNumVal();
}
