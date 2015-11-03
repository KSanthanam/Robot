package org.bgl.robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Commands {
	public enum Command {
	    MOVE(0b00000001), LEFT(0b00000010), RIGHT(0b00000100), PLACE(0b00001000), REPORT(0b00010000) ;				
	    private int numVal;
	    Command(int numVal) {
	        this.numVal = numVal;
	    }
	    public int getNumVal() {
	        return numVal;	        
	    }
	    public boolean isPresentIn(int commands) {
	    	return ((commands & numVal) != 0);
	    }
	    public int combineWith(Command with) {
	    	return (numVal & with.getNumVal());
	    }
	}	
	public static final int MOVE = Command.MOVE.getNumVal();
	public static final int LEFT = Command.LEFT.getNumVal();
	public static final int RIGHT = Command.RIGHT.getNumVal();
	public static final int PLACE = Command.PLACE.getNumVal();
	public static final int REPORT = Command.REPORT.getNumVal();
	public static final int ALL_COMMANDS = 0b11111111;
	public static final HashMap<Command, String> LIST = new HashMap<Command, String>() {/**
		 * 
		 */
		private static final long serialVersionUID = -3791267909156435929L;

	{
		put(Command.MOVE, "MOVE");
		put(Command.LEFT, "LEFT");
		put(Command.RIGHT, "RIGHT");
		put(Command.PLACE, "PLACE");
		put(Command.REPORT, "REPORT");
	}};
	public static final List<Command> SET = Arrays.asList(
			Command.MOVE,
			Command.LEFT,
			Command.RIGHT,
			Command.PLACE,
			Command.REPORT
			);
	
	public static List<Command> getCommands(int commands) {		
		List<Command> allowedCommandsCollect = SET
			    .stream()
			    .filter(e -> e.isPresentIn(commands))
			    .collect(Collectors.toList());		
		return allowedCommandsCollect;
	}
	
	public static List<String> getCommandStrings(int commands) {
		List<Command> allowedCommands = getCommands(commands);
		List<String> allowedCommandsCollect = allowedCommands
			    .stream()
			    .map(c -> LIST.get(c))
			    .collect(Collectors.toList());		
		return allowedCommandsCollect;		
	}
	public static List<String> getCommandStrings() {
		List<Command> allowedCommands = getCommands(ALL_COMMANDS);
		List<String> allowedCommandsCollect = allowedCommands
			    .stream()
			    .map(c -> LIST.get(c))
			    .collect(Collectors.toList());		
		return allowedCommandsCollect;		
	}
	public static boolean isLegitimateCommand(String search) {
		List<String> commands = getCommandStrings();
		for (String command: commands) {
			if (command.toUpperCase().equals(search.toUpperCase())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean validParameter(Command command, String paramsString) 
			throws PositionOutOfBoundException, InvalidDirectionException {
		if (command != Command.PLACE) return true;
		String[] params = paramsString.split(",");
		String xValue, yValue, dirValue;
		xValue = params[0].trim();
		yValue = params[1].trim();
		dirValue = params[2].trim();
		Integer x, y;
		try {
			x = Integer.parseInt(xValue,10);
			y = Integer.parseInt(yValue,10);
		} catch (NumberFormatException ex) {
			return false;
		}
		if (((x > TableSurface.XMAX) || (x < 0)) ||
		   ((y > TableSurface.YMAX) || (y < 0))) throw new PositionOutOfBoundException();

		if (!TableSurface.isLegitimateDirection(dirValue)) throw new InvalidDirectionException();
		return true;
	}
}
