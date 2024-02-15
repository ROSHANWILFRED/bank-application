package com.onebill.bank.exception;

public class InvalidUserCredentialException extends RuntimeException {
	public InvalidUserCredentialException(String s) {
		super(s);
	}
}
