package com.onebill.bank.exception;

public class InvalidAmountException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3409486232063551726L;

	public InvalidAmountException(String s) {
		super(s);
	}
}
