package com.onebill.bank.data.config;

public class ConfigData {

	// Bank details for Axis
	private static final double AXIS_WITHDRAW_CHARGES = (0.14 / 100);
	private static final double AXIS_DEPOSIT_CHARGES = (0.012 / 100);
	public static final double AXIS_MINIMUM_BALANCE = 500.0;

	// Public methods to access bank charges and minimum balance

	public double getWithdrawCharges() {
		return AXIS_WITHDRAW_CHARGES;
	}

	public double getDepositCharges() {
		return AXIS_DEPOSIT_CHARGES;
	}

	public double getMinimumBalance() {
		return AXIS_MINIMUM_BALANCE;
	}
}
