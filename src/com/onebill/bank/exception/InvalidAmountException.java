package com.onebill.bank.exception;

public class InvalidAmountException extends RuntimeException {
	public InvalidAmountException(String s) {
		super(s);
	}
}
