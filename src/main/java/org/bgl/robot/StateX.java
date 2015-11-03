package org.bgl.robot;
import org.bgl.robot.TableSurface.Face;

public class StateX {
	private int xpos = 0;
	private int ypos = 0;
	private Face face;
	private boolean isActive = false;
	public StateX(int x, int y, Face face) {
		this.face = face;
		if ((x >= 0 ) && (x <= TableSurface.XMAX)) this.xpos = x; else this.isActive = false;			
		if ((y >= 0 ) && (y <= TableSurface.YMAX)) this.ypos = y; else this.isActive = false;			
		this.isActive = true;
	}
	public void setState(int x, int y, Face face) {
		setXpos(x);
		setYpos(y);
		setFaceDirection(face);
		placeOnBoard();
	}
	public void setState(String paramsString) {
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
		setState(x,y,face);
	}
	public StateX() {
		isActive = false;
	}
	@Override
	public String toString() {
		String state = "Robot is ";
    	if (!isActive) {
    		state += " off the board";
    	} else {
    		state += " at " + new Integer(this.xpos).toString() + 
    				 " x " + new Integer(this.ypos).toString() + 
    				 " facing " + this.face.getStringVal();
    	}
    	return state;
	}
	public void setXpos(int x) {
		xpos = x;
	}
	public int getXpos() {
		return xpos;
	}
	public void setYpos(int y) {
		ypos = y;
	}
	public int getYpos() {
		return ypos;
	}
	public void setFaceDirection(Face face) {
		this.face = face;
	}
	public Face getFaceDirection() {
		return face;
	}
	public void moveNorth() {
		ypos++;
	}
	public void moveEast() {
		xpos++;
	}
	public void moveSouth() {
		ypos--;
	}
	public void moveWest() {
		xpos--;
	}
	public void faceNorth() {
		face = Face.NORTH;
	}
	public void faceEast() {
		face = Face.EAST;
	}
	public void faceSouth() {
		face = Face.SOUTH;
	}
	public void faceWest() {
		face = Face.WEST;
	}
	public void setIsActive(boolean placed) {
		isActive = placed;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void placeOnBoard() {
		isActive = true;
	}
	public void takeOffBoard() {
		isActive = false;
	}
	public void faceRobot(Face face) {
		this.face = face;
	}
	public boolean isOnBoard() {
		return isActive;
	}
	public boolean isOffBoard() {
		return !isActive;
	}
	public String reportState() {
    	return (new Integer(xpos).toString() + "," + new Integer(ypos).toString()  + "," + 
    			this.face.getStringVal());    	
	}
	public String reportCurrentState() {
    	String state = "Robot is ";
    	if (isOffBoard()) {
    		state += " off the board";
    	} else {
    		state += " at " + new Integer(xpos).toString() + 
    				 " x " + new Integer(ypos).toString() + 
    				 " facing " + face.getStringVal();
    	}
    	return state;
	}
}
