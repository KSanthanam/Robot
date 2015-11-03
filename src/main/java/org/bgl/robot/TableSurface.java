package org.bgl.robot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.bgl.robot.Commands.Command;

public class TableSurface {
	public static final int XMAX = 5;
	public static final int YMAX = 5;
    /**
     * Possible direction the Robot is facing.
     */
    public static enum Face {
        NORTH("NORTH"), EAST("EAST"), SOUTH("SOUTH"), WEST("WEST");
        private String stringVal;
    	Face(String stringVal) {
    		this.stringVal = stringVal;
    	}
    	public String getStringVal() {
    		return stringVal;
    	}
    }
	public static final String NORTH = Face.NORTH.getStringVal();
	public static final String EAST = Face.EAST.getStringVal();
	public static final String SOUTH = Face.SOUTH.getStringVal();
	public static final String WEST = Face.WEST.getStringVal();
	
	public static final List<Face> SET = Arrays.asList(
			Face.NORTH,
			Face.EAST,
			Face.SOUTH,
			Face.WEST
			);

	public static final HashMap<Face, String> LIST = new HashMap<Face, String>() {/**
		 * 
		 */
		private static final long serialVersionUID = 280961682145919032L;

	{
		put(Face.NORTH, "NORTH");
		put(Face.EAST, "EAST");
		put(Face.SOUTH, "SOUTH");
		put(Face.WEST, "WEST");
	}};


	public static boolean isLegitimateDirection(String search) {
		for (Face direction: SET) {
			if (direction.getStringVal().toUpperCase().equals(search.toUpperCase())) {
				return true;
			}
		}
		return false;
	}
	public static Face getDirection(String search) {
		for (Face direction: SET) {
			if (direction.getStringVal().toUpperCase().equals(search.toUpperCase())) {
				return direction;
			}
		}
		return Face.NORTH; // default direction
	}
}
