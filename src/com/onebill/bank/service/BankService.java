package com.onebill.bank.service;

import java.util.Date;
import java.util.LinkedList;

import com.onebill.bank.data.bank.BankStatement;
import com.onebill.bank.data.config.ConfigData;
import com.onebill.bank.data.user.UserData;
import com.onebill.bank.exception.InvalidAmountException;
import com.onebill.bank.exception.MaximumTimeOfIterationReachedException;
import com.onebill.bank.exception.NoUserFoundException;

public class BankService implements RBIBank {

	private UserData user;
	private String bankName;
	private static final int MAX_WITHDRAWALS = 3;
    private int withdrawCount = 0;

	public BankService(UserData user, String bankName) {
		this.user = user;
		this.setBankName(bankName);
	}

	public void deposit(double amount) {
		ConfigData configBankData = new ConfigData();
		double depositCharge = configBankData.getDepositCharges() * amount;
		double totalAmountDeposited = amount - depositCharge;
		user.setBalance(user.getBalance() + totalAmountDeposited);
		System.out.println("Deposited: " + amount + ", Deposit Charge: " + depositCharge);

		BankStatement depositStatement = new BankStatement(user.getName(), "Deposit", totalAmountDeposited,
				user.getBalance(), new Date(), user.getBankName());
		BankStatement.addTransactionDetails(depositStatement);
	}

	public void withdraw(double amount) {
		ConfigData configBankData = new ConfigData();
		try {
			if (amount % 100 != 0) {
				throw new InvalidAmountException("Enter amount in divisions of 100");
			}
			double withdrawCharge = configBankData.getWithdrawCharges() * amount;
			double totalAmountWithdrawn = amount + withdrawCharge;
			double minimumBalance = configBankData.getMinimumBalance();
			
			if (withdrawCount >= MAX_WITHDRAWALS) {
                throw new MaximumTimeOfIterationReachedException("Maximum of 3 withdrawals reached in a day");
            }

			if (totalAmountWithdrawn > user.getBalance()) {
				System.err.println("Insufficient balance");
				return;
			}

			if (user.getBalance() - totalAmountWithdrawn < minimumBalance) {
				System.err.println("Withdrawal failed: Minimum balance of " + minimumBalance + " must be maintained.");
				return;
			} else {
				user.setBalance(user.getBalance() - totalAmountWithdrawn);
				System.out.println("Withdrawn: " + amount + ", Withdraw Charge: " + withdrawCharge);

				BankStatement withdrawStatement = new BankStatement(user.getName(), "Withdrawal", totalAmountWithdrawn,
						user.getBalance(), new Date(), user.getBankName());
				BankStatement.addTransactionDetails(withdrawStatement);
				withdrawCount++;
			}
		} catch (InvalidAmountException e) {
			System.err.println(e.getMessage());
			return;
		} catch (MaximumTimeOfIterationReachedException e) {
			System.err.println(e.getMessage());
			return;
		}

	}

	public void transferFunds(double amount, String recipientUsername) {
		ConfigData configBankData = new ConfigData();
	    try {
	        UserData recipient = UserService.returnUserData(recipientUsername);
	        if (recipient == null) {
	            throw new NoUserFoundException("Recipient not found.");
	        }

	        double minimumBalance = configBankData.getMinimumBalance();

	        if (amount > user.getBalance()) {
	            System.err.println("Insufficient balance to transfer.");
	            return;
	        }

	        if (user.getBalance() - amount < minimumBalance) {
	            System.err.println("Transfer failed: Minimum balance of " + minimumBalance + " must be maintained.");
	            return;
	        }

	        user.setBalance(user.getBalance() - amount);
	        recipient.setBalance(recipient.getBalance() + amount);

	        BankStatement transferOut = new BankStatement(user.getName(), "Transfer to " + recipient.getName(), amount,
	                user.getBalance(), new Date(), user.getBankName());
	        BankStatement transferIn = new BankStatement(recipient.getName(), "Transfer from " + user.getName(), amount,
	                recipient.getBalance(), new Date(), recipient.getBankName());

	        BankStatement.addTransactionDetails(transferOut);
	        BankStatement.addTransactionDetails(transferIn);

	        System.out.println("Transfer successful. " + amount + " transferred to " + recipientUsername);
	        System.out.println(recipient.getName() + " New Balance: " + recipient.getBalance());
	    } catch (NoUserFoundException e) {
	        System.err.println(e.getMessage());
	        return;
	    }
	}

	public void showBalance() {
		System.out.println("Current Balance: " + user.getBalance());
	}

	public LinkedList<BankStatement> getTransactionDetails() {
		return BankStatement.getTransactionDetails(); 
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
}
