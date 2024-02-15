package com.onebill.bank.data.bank;

import java.util.Date;
import java.util.LinkedList;

public class BankHelpAndSupport {
	private String complaint;
	private String userName;
	private Date date;
	private String status;
	private String adminUserName;
	private Date dateResolved;
	
	private static LinkedList<BankHelpAndSupport> resolvedComplaints = new LinkedList<>();


	// Constructor
	public BankHelpAndSupport(String complaint, String userName) {
		this.complaint = complaint;
		this.userName = userName;
		this.date = new Date(); // Current date and time
		this.status = "Pending"; // Initial status
		this.adminUserName = "admin";
		this.dateResolved = null;
	}

	// Method to create dummy complaints linked list
	public static LinkedList<BankHelpAndSupport> createSampleComplaints() {
		LinkedList<BankHelpAndSupport> complaints = new LinkedList<>();
		// Add dummy complaints
		complaints.add(new BankHelpAndSupport("ATM card not working", "John"));
		complaints.add(new BankHelpAndSupport("Transaction failed", "Alice"));
		complaints.add(new BankHelpAndSupport("Account balance discrepancy", "Bob"));
		return complaints;
	}

	public String getComplaint() {
		return complaint;
	}

	public void setComplaint(String complaint) {
		this.complaint = complaint;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public String getAdminUserName() {
		return adminUserName;
	}

	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}

	public Date getDateResolved() {
		return dateResolved;
	}

	public void setDateResolved(Date dateResolved) {
		this.dateResolved = dateResolved;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static LinkedList<BankHelpAndSupport> getResolvedComplaints() {
		return resolvedComplaints;
	}

}
