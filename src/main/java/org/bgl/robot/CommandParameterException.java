package org.bgl.robot;

public class CommandParameterException extends Exception {
	String message;
	public CommandParameterException(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
