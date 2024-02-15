package com.onebill.bank.exception;

public class InvalidUserCredentialException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4465548753463874372L;

	public InvalidUserCredentialException(String s) {
		super(s);
	}
}
