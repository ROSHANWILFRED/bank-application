package com.onebill.bank.data.bank;

import java.util.Date;
import java.util.LinkedList;

public class BankStatement {

	private String username;
	private String type;
	private double amount;
	private double userBalance;
	private Date transactionDate;
	private String bankName;
	private static LinkedList<BankStatement> transactionDetails;

	public BankStatement(String username, String type, double amount, double userBalance, Date transactionDate,
			String bankName) {
		this.username = username;
		this.type = type;
		this.amount = amount;
		this.userBalance = userBalance;
		this.transactionDate = transactionDate;
		this.bankName = bankName;
	}

	public static LinkedList<BankStatement> createSampleTransactions() {
		LinkedList<BankStatement> transactions = new LinkedList<>();

		return transactions;
	}

	public static void addTransactionDetails(BankStatement transaction) {
		if (transactionDetails == null) {
			transactionDetails = new LinkedList<>();
		}
		transactionDetails.add(transaction);
	}

	public static LinkedList<BankStatement> getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(LinkedList<BankStatement> transactionDetails) {
		BankStatement.transactionDetails = transactionDetails;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getUserBalance() {
		return userBalance;
	}

	public void setUserBalance(double userBalance) {
		this.userBalance = userBalance;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
}
