package org.bgl.robot;

import java.lang.Exception;

public class WrongMoveException extends WrongCommandException {
	public WrongMoveException(int command) {
		super(command);
	}
	public int getCommand() {
		return super.getCommand();
	}
}
