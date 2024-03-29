package com.onebill.bank;

import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

import com.onebill.bank.data.bank.BankHelpAndSupport;
import com.onebill.bank.data.bank.BankStatement;
import com.onebill.bank.data.user.UserData;
import com.onebill.bank.exception.InvalidUserCredentialException;
import com.onebill.bank.exception.NoUserFoundException;
import com.onebill.bank.service.BankService;
import com.onebill.bank.service.UserService;

public class BankExecutor {

	private static LinkedList<BankHelpAndSupport> complaints = BankHelpAndSupport.createSampleComplaints();
	private static LinkedList<BankHelpAndSupport> resolvedComplaints = BankHelpAndSupport.getResolvedComplaints();

	private static void addUserComplaint(String userName, String complaint) {
		boolean isUnique = true;
		for (BankHelpAndSupport existingUserComplaint : complaints) {
			if (existingUserComplaint.getUserName().equalsIgnoreCase(userName)) {
				isUnique = false;
				break;
			}
		}

		if (isUnique) {
			BankHelpAndSupport userComplaint = new BankHelpAndSupport(complaint, userName);
			complaints.add(userComplaint);
			System.out.println("Your complaint has been successfully submitted. We'll look into it shortly.");
		} else {
			System.out.println("Your complaint is already exist. We'll look into it shortly and resolve the issue.");
		}
	}

	private static void helpSupport(String userName) {
		System.out.println("If you're facing issues logging in, please contact our support team.");
		System.out.println("You can reach us at support@yourbank.com or call us at 1-800-123-4567.");
		System.out.println();
		
		System.out.print("Would you like to submit a complaint? (yes/no): ");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String response = scanner.nextLine().toLowerCase();
		if (response.equals("yes")) {
			System.out.print("Enter your complaint: ");
			String complaint = scanner.nextLine();
			addUserComplaint(userName, complaint);
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		UserService userService = new UserService();

		while (true) {
			System.out.println("\nWelcome to Your Bank!");
			System.out.println("1. Login");
			System.out.println("2. Help & Support");
			System.out.println("3. Exit");
			System.out.print("Enter your choice: ");
			int choice = Integer.parseInt(scanner.nextLine());

			switch (choice) {
			case 1:
				loginUser(scanner, userService);
				break;
			case 2:
				System.out.print("Enter your username: ");
				String userName = scanner.nextLine();
				helpSupport(userName);
				break;
			case 3:
				System.out.println();
				System.out.println("--------------Thank you for using our Bank. Goodbye!--------------");
				scanner.close();
				System.exit(0);
			default:
				System.err.println("Invalid choice. Please enter a valid option.");
			}
		}
	}

	private static void loginUser(Scanner scanner, UserService userService) {
		try {
            System.out.print("Enter your username: ");
            String enteredUsername = scanner.nextLine();
            UserData selectedUser = UserService.returnUserData(enteredUsername);

            if (selectedUser == null) {
                throw new NoUserFoundException("User not found.");
            }
            
            System.out.print("Enter password for " + selectedUser.getName() + ": ");
            String enteredPassword = scanner.nextLine();

            boolean passwordCheck = userService.validatePassword(enteredUsername, enteredPassword);
            if (!passwordCheck) {
                throw new InvalidUserCredentialException("Password Incorrect");
            }

            String userType = selectedUser.getRole();

            switch (userType) {
                case "user":
                    System.out.println("---------------------------LOGIN SUCCESSFUL---------------------------");
                    handleUser(scanner, userService, selectedUser);
                    break;
                case "admin":
                    System.out.println("---------------------------LOGIN SUCCESSFUL---------------------------");
                    handleAdmin(scanner, userService, selectedUser);
                    break;
                default:
                    System.err.println("Invalid user role.");
            }
        } catch (NoUserFoundException e) {
            System.err.println(e.getMessage());
            System.out.println("Try Again!!");
            loginUser(scanner, userService);
        } catch (InvalidUserCredentialException e) {
        	System.err.println(e.getMessage());
        	System.out.println("Try Again!!");
        	loginUser(scanner,userService);
        } 
	}

	private static void handleUser(Scanner scanner, UserService userService, UserData selectedUser) {
		System.out.println();
		System.out.println("Welcome to " + selectedUser.getBankName() + " bank services, " + selectedUser.getName());

		BankService bankService = new BankService(selectedUser, selectedUser.getBankName());

		while (true) {
			System.out.println("\nMenu:");
			System.out.println("1. Deposit to My Account");
			System.out.println("2. Withdraw");
			System.out.println("3. Show Balance");
			System.out.println("4. Bank Statement");
			System.out.println("5. Transfer Funds");
			System.out.println("6. Help & Support");
			System.out.println("7. Logout");

			System.out.print("Enter your choice: ");
			int choice = Integer.parseInt(scanner.nextLine());

			switch (choice) {
			case 1:
				System.out.print("Enter amount to deposit: ");
				double depositAmount = Double.parseDouble(scanner.nextLine());
				bankService.deposit(depositAmount);
				break;
			case 2:
				System.out.print("Enter amount to withdraw: ");
				double withdrawAmount = Double.parseDouble(scanner.nextLine());
				bankService.withdraw(withdrawAmount);
				break;
			case 3:
				bankService.showBalance();
				break;
			case 4:
				LinkedList<BankStatement> allTransactions = bankService.getTransactionDetails();
				if (allTransactions != null && !allTransactions.isEmpty()) {
					System.out.println();
					System.out.println("Bank Statement:");
					System.out.println();
					for (BankStatement statement : allTransactions) {
						if (statement.getUsername() == selectedUser.getName()) {
							System.out.println("Username: " + statement.getUsername());
							System.out.println("Transaction Type: " + statement.getType());
							System.out.println("Amount: " + statement.getAmount());
							System.out.println("User Balance: " + statement.getUserBalance());
							System.out.println("Transaction Date: " + statement.getTransactionDate());
							System.out.println("Bank Name: " + statement.getBankName());
							System.out.println();
						}
					}
				} else {
					System.err.println("No transactions found.");
				}
				break;
			case 5:
				System.out.print("Enter recipient's username: ");
				String recipientUsername = scanner.nextLine();
				System.out.print("Enter amount to transfer: ");
				double transferAmount = Double.parseDouble(scanner.nextLine());
				bankService.transferFunds(transferAmount, recipientUsername);
				break;
			case 6:
				helpSupport(selectedUser.getName());
				break;
			case 7:
				System.out.println("---------------------------LOGOUT SUCCESSFUL---------------------------");
				return;
			default:
				System.err.println("Invalid choice. Please enter a valid option.");
			}
		}
	}

	private static void handleAdmin(Scanner scanner, UserService userService,UserData selectedUser) {
		System.out.println("Welcome, Admin!");

		while (true) {
			System.out.println("\nAdmin Menu:");
			System.out.println("1. Add User");
			System.out.println("2. Remove User");
			System.out.println("3. Show All User Details");
			System.out.println("4. View and Solve Complaint");
	        System.out.println("5. View Resolved Complaints");
	        System.out.println("6. Logout");
			System.out.print("Enter your choice: ");
			int adminChoice = Integer.parseInt(scanner.nextLine());

			switch (adminChoice) {
			case 1:
				System.out.print("Enter username for the new user: ");
				String newUsername = scanner.nextLine();
				System.out.print("Enter password for the new user: ");
				String newPassword = scanner.nextLine();
				System.out.print("Enter initial balance for the new user: ");
				double initialBalance = Double.parseDouble(scanner.nextLine());
				System.out.print("Enter bank name for the new user: ");
				String bankName = scanner.nextLine();
				UserData newUser = new UserData(newUsername, newPassword, initialBalance, bankName, "user");
				boolean isUserExist = userService.checkIfUserExist(newUsername);
				if (isUserExist) {
					System.err.println("User Already exist!");
				} else {
					userService.addUser(newUser);
					System.out.println("User added successfully!");
				}
				break;
			case 2:
				System.out.print("Enter username to remove: ");
				String removeUsername = scanner.nextLine();

				try {
				    UserData userToRemove = UserService.returnUserData(removeUsername);
				    if (userToRemove != null) {
				        userService.removeUser(userToRemove);
				        System.out.println("User removed successfully!");
				    } else {
				        throw new NoUserFoundException("User not found.");
				    }
				} catch (NoUserFoundException e) {
				    System.err.println(e.getMessage());
				}
				break;
			case 3:
				System.out.println("\nAll User Details:");
				for (UserData user : UserData.getUsers()) {
					if (!user.getRole().equals("admin")) {
						System.out.println("Username: " + user.getName());
						System.out.println("Password: " + user.getPassword());
						System.out.println("Balance: " + user.getBalance());
						System.out.println("Bank Name: " + user.getBankName());
						System.out.println("User Type: " + user.getRole());
						System.out.println();
					}
				}
				break;
			case 4:
                // View and Solve Complaint
                
                if (!complaints.isEmpty()) {
                    BankHelpAndSupport complaint = complaints.getFirst(); // Read the first complaint
                    System.out.println("\nComplaint:");
                    System.out.println("User: " + complaint.getUserName());
                    System.out.println("Complaint: " + complaint.getComplaint());
                    System.out.println("Date: " + complaint.getDate());
                    System.out.print("Status: " + complaint.getStatus());
                    System.out.println("\n1. Solve\n2. Skip");
                    System.out.print("Enter your choice: ");
                    int solveChoice = Integer.parseInt(scanner.nextLine());
                    if (solveChoice == 1) {
                        complaint.setStatus("Resolved");
                        complaint.setAdminUserName(selectedUser.getName());
                        complaint.setDateResolved(new Date());
                        resolvedComplaints.add(complaint);
                        complaints.remove(complaint);
                        
                        System.out.println("Complaint solved successfully!");
                    }
                } else {
                    System.err.println("No complaints to solve.");
                }
                break;
            case 5:
                // View Resolved Complaints
                LinkedList<BankHelpAndSupport> resolvedComplaints = BankHelpAndSupport.getResolvedComplaints();
                System.out.println("\nResolved Complaints:");
                for (BankHelpAndSupport complaint : resolvedComplaints) {
                    System.out.println("User: " + complaint.getUserName());
                    System.out.println("Complaint: " + complaint.getComplaint());
                    System.out.println("Date Filed: " + complaint.getDate());
                    System.out.println("Solved By: " + complaint.getAdminUserName());
                    System.out.println("Date Resolved: "+complaint.getDateResolved());
                    System.out.println();
                }
                break;
            case 6:
                System.out.println("---------------------------LOGOUT SUCCESSFUL---------------------------");
                return;
            default:
                System.err.println("Invalid choice. Please enter a valid option.");
			}
		}
	}
}
