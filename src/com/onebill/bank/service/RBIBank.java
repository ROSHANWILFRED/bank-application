package com.onebill.bank.service;

import java.util.LinkedList;

import com.onebill.bank.data.bank.BankStatement;

public interface RBIBank {

	public void deposit(double amount);

	public void withdraw(double amount);

	public void transferFunds(double amount, String recipientUsername);

	public void showBalance();

	public LinkedList<BankStatement> getTransactionDetails();
}
