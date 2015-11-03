package org.bgl.robot;

public class InvalidStateException extends Exception {
	private String message;
	public InvalidStateException(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}

}
