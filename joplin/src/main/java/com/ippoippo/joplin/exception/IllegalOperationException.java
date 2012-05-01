package com.ippoippo.joplin.exception;

public class IllegalOperationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4235946230620932671L;

	public IllegalOperationException() {
		super();
	}

	public IllegalOperationException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalOperationException(String message) {
		super(message);
	}

	public IllegalOperationException(Throwable cause) {
		super(cause);
	}
}
