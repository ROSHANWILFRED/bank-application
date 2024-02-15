package com.onebill.bank.exception;

public class NoUserFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 305281491772483003L;

	public NoUserFoundException(String s) {
		super(s);
	}
}
