package com.onebill.bank.service;

import java.util.LinkedList;
import com.onebill.bank.data.user.UserData;

public class UserService {

	static LinkedList<UserData> users = UserData.getUsers();

	public boolean checkIfUserExist(String userName) {
		for (UserData user : users) {
			if (user.getName().equalsIgnoreCase(userName)) {
				return true;
			}
		}
		return false;
	}

	public static UserData returnUserData(String userName) {
		for (UserData user : users) {
			if (user.getName().equalsIgnoreCase(userName)) {
				return user;
			}
		}
		return null;
	}

	public void addUser(UserData user) {
		boolean flag = checkIfUserExist(user.getName());
		if (flag) {
			System.out.println("User already exists");
			return;
		}
		users.add(user);
	}

	public void removeUser(UserData user) {
		boolean flag = checkIfUserExist(user.getName());
		if (!flag) {
			System.out.println("User not found");
			return;
		}
		users.remove(user);
	}

	public boolean validatePassword(String userName, String password) {
		for (UserData user : users) {
			if (user.getName().equalsIgnoreCase(userName)) {
				return user.getPassword().equals(password);
			}
		}
		return false;
	}
}
