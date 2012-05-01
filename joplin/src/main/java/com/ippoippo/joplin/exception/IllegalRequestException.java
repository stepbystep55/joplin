package com.ippoippo.joplin.exception;

public class IllegalRequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5436596303283095637L;

	public IllegalRequestException() {
		super();
	}

	public IllegalRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalRequestException(String message) {
		super(message);
	}

	public IllegalRequestException(Throwable cause) {
		super(cause);
	}
}
