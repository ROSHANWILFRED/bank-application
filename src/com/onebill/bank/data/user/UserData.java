package com.onebill.bank.data.user;

import java.util.LinkedList;

public class UserData {

	public static LinkedList<UserData> users;

	static {
		users = new LinkedList<>();
		users.add(new UserData("Krishna", "Krish@456", 10000, "SBI", "user"));
		users.add(new UserData("Nasim", "Nasim123", 8000, "SBI", "user"));
		users.add(new UserData("Roshan", "roshan@17", 5000, "AXIS", "user"));
		users.add(new UserData("Kiran", "kiran@111", 20000, "SBI", "user"));
		users.add(new UserData("admin", "admin", 0, null, "admin"));
	}

	private String name;
	private String password;
	private double balance;
	private String bankName;
	private String role;

	public UserData(String name, String password, double balance, String bankName, String role) {
		this.name = name;
		this.password = password;
		this.balance = balance;
		this.bankName = bankName;
		this.role = role;

	}

	public static LinkedList<UserData> getUsers() {
		return users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
