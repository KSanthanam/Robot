package org.bgl.robot;

import java.util.List;

import org.bgl.robot.TableSurface.Face;

public class RobotState implements State{
    // LogicState with allowed commands for given sensors set
	private static final LogicGate logicGate = new LogicGate();

	// Sensors states
	private int sensors = 0;

	// Possible Commands
	private int commands = 0;

	// X Position
	private int xpos = 0;
	
	// Y Position
	private int ypos = 0;

	// Facing Direction 
	private Face direction;
	
	// Is it active?
	private boolean isActive = false;
	
	
//	private Commands.Command command;
/*
	public StateX(int x, int y, Face face) {
		this.face = face;
		if ((x >= 0 ) && (x <= TableSurface.XMAX)) this.xpos = x; else this.isActive = false;			
		if ((y >= 0 ) && (y <= TableSurface.YMAX)) this.ypos = y; else this.isActive = false;			
		this.isActive = true;
	}
*/	
    // Constructor with Initial position
    public RobotState(int x, int y, TableSurface.Face face) {
    	setRobotState(x,y,face);
    	setSensors();
    }
    // Constructor with No Initial position
    public RobotState() {
		isActive = false;			    		
    	this.sensors = 0;
    	setSensors();
    }

    private void setXpos(int x) throws InvalidXPositionException {
    	if ((x >= 0 ) && (x <= TableSurface.XMAX)) {
    		xpos = x; 
    	} else {
    		throw new InvalidXPositionException(x);
    	}    	
    }
	private int getXpos() {
		return xpos;
	}
    private void setYpos(int y) throws InvalidYPositionException {
		if ((y >= 0 ) && (y <= TableSurface.YMAX)) {
			ypos = y;
		} else {
    		throw new InvalidYPositionException(y);
		}
    }
	private int getYpos() {
		return ypos;
	}
	private void faceRobot(Face face) {
		direction = face;
	}
	private boolean isOnBoard() {
		return isActive;
	}
	private boolean isOffBoard() {
		return !isActive;
	}

	private Face getFaceDirection() {
		return direction;
	}
	private void moveNorth() {
		ypos++;
	}
	private void moveEast() {
		xpos++;
	}
	private void moveSouth() {
		ypos--;
	}
	private void moveWest() {
		xpos--;
	}
	private void faceNorth() {
		direction = Face.NORTH;
	}
	private void faceEast() {
		direction = Face.EAST;
	}
	private void faceSouth() {
		direction = Face.SOUTH;
	}
	private void faceWest() {
		direction = Face.WEST;
	}
	@Override
	public void setIsActive(boolean placed) {
		isActive = placed;
	}
	@Override
	public boolean getIsActive() {
		return isActive;
	}

	private void setRobotState(int x, int y, Face face) {
    	try {
        	setXpos(x);    		
        	setYpos(y);
        	faceRobot(face);
    		isActive = true;			    		
    	} catch (InvalidStateException ex) {
    		isActive = false;			    		
    	}
	}
	private void setRobotState(String paramsString) {
		String[] params = paramsString.trim().split(",");
		Integer x, y;
		Face face;
		try {
			x = Integer.parseInt(params[0].trim(),10);
			y = Integer.parseInt(params[1].trim(),10);
		} catch (NumberFormatException ex) {
			return;
		}
		if ((x > TableSurface.XMAX) || (x < 0)) return;
		if ((y > TableSurface.YMAX) || (y < 0)) return;
		face = TableSurface.getDirection(params[2].trim());
		setRobotState(x,y,face);
	}
	
    private void setSensors() {
    	int frontSensor = 0, leftSensor = 0, rightSensor = 0;
    	if (isOffBoard()) {
    		this.sensors = 0;
    		return;
    	}
    	switch (getFaceDirection()) {
    		case NORTH: 
    			if (getXpos() > 0 ) leftSensor = Sensors.LEFT; else leftSensor = 0;
    			if (getXpos() < TableSurface.XMAX) rightSensor = Sensors.RIGHT; else rightSensor = 0;
    			if (getYpos() < TableSurface.YMAX) frontSensor = Sensors.FRONT; else  frontSensor = 0;
    			break;
    		case EAST:
    			if (getYpos() < TableSurface.YMAX) leftSensor = Sensors.LEFT; else  leftSensor = 0;
    			if (getYpos() > 0 ) rightSensor = Sensors.RIGHT; else rightSensor = 0;
    			if (getXpos() < TableSurface.XMAX) frontSensor = Sensors.FRONT; else frontSensor = 0;
    			break;
    		case SOUTH:
    			if (getXpos() < TableSurface.XMAX) leftSensor = Sensors.LEFT; else  leftSensor = 0;
    			if (getXpos() > 0 ) rightSensor = Sensors.RIGHT; else rightSensor = 0;
    			if (getYpos() > 0) frontSensor = Sensors.FRONT; else frontSensor = 0;
    			break;
    		case WEST:
    			if (getYpos() > 0) leftSensor = Sensors.LEFT; else  leftSensor = 0;
    			if (getYpos() < TableSurface.YMAX ) rightSensor = Sensors.RIGHT; else rightSensor = 0;
    			if (getXpos() > 0) frontSensor = Sensors.FRONT; else frontSensor = 0;
    			break;
    	}
    	this.sensors = frontSensor | leftSensor | rightSensor;
    	return;
    }
    public void move() throws WrongMoveException {
    	int commands = logicGate.getCommands(this.sensors);
    	if ( (commands & Commands.MOVE) == 0) {
    		throw new WrongMoveException(Commands.MOVE);
    	}
    	switch (getFaceDirection()) {
    		case NORTH:
    			moveNorth();;
    			break;
    		case EAST:
    			moveEast();;
    			break;
    		case SOUTH:
    			moveSouth();;
    			break;
    		case WEST:
    			moveWest();;
    			break;
    		default:
    			break;
    	}
    	setSensors();
    	return;
    }
    
    public void left() throws WrongTurnException {
    	int commands = logicGate.getCommands(this.sensors);
    	if ( (commands & Commands.LEFT) == 0) {
    		throw new WrongTurnException(Commands.LEFT);
    	}
    	switch (getFaceDirection()) {
		case NORTH:
			faceWest();
			break;
		case EAST:
			faceNorth();
			break;
		case SOUTH:
			faceEast();
			break;
		case WEST:
			faceSouth();
			break;
		default:
			break;
    	}
    	setSensors();
    	return;
    }

    
    public void right() throws WrongTurnException {
    	int commands = logicGate.getCommands(this.sensors);
    	if ( (commands & Commands.RIGHT) == 0) {
    		throw new WrongTurnException(Commands.RIGHT);
    	}
    	switch (getFaceDirection()) {
		case NORTH:
			faceEast();
			break;
		case EAST:
			faceSouth();
			break;
		case SOUTH:
			faceWest();
			break;
		case WEST:
			faceNorth();
			break;
		default:
			break;
    	}
    	setSensors();
    	return;
    }
    
    public void place(int x, int y, TableSurface.Face face) {
    	setRobotState(x, y, face);
    	setSensors();
    	return;
    }
    public void place(String params) {
    	setRobotState(params);    	
    	setSensors();
    	return;
    }
    @Override
    public String report() {
    	return reportState();
    }
    
    
	private String reportState() {
    	return (new Integer(xpos).toString() + "," + new Integer(ypos).toString()  + "," + 
    			direction.getStringVal());    	
	}
	public String reportCurrentState() {
    	String state = "Robot is ";
    	if (isOffBoard()) {
    		state += " off the board";
    	} else {
    		state += " at " + new Integer(xpos).toString() + 
    				 " x " + new Integer(ypos).toString() + 
    				 " facing " + direction.getStringVal();
    	}
    	return state;
	}
    
    
    public List<String> movesGenerator() {
    	this.commands = 0;
		this.commands |= Commands.PLACE;
    	if (isOnBoard()) {
    		this.commands |= Commands.REPORT;
    		this.commands |= logicGate.getCommands(this.sensors);
    	}
    	return Commands.getCommandStrings(this.commands);
    }
    
    public boolean validCommand(String command) {
    	List<String> validCommands = movesGenerator();
    	for (String validCommand: validCommands) {
    		if (validCommand.toUpperCase().equals(command.toUpperCase())) return true;
    	}
    	return false;
    }
}
