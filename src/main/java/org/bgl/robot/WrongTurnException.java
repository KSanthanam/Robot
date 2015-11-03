package org.bgl.robot;

public class WrongTurnException extends WrongCommandException {
	public WrongTurnException(int command) {
		super(command);
	}
	public int getCommand() {
		return super.getCommand();
	}
}
