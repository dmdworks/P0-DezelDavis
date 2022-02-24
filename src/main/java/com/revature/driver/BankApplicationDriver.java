package com.revature.driver;

/**
 * This is the entry point to the application
 */
import java.util.Scanner;

import com.revature.beans.User;
import com.revature.services.UserService;
import com.revature.dao.UserDaoFile;
import com.revature.dao.AccountDaoFile;

public class BankApplicationDriver {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		UserDaoFile userDao = new UserDaoFile();
		AccountDaoFile accountDao = new AccountDaoFile();
		UserService userSrv = new UserService(userDao, accountDao);
		
		String userName, password, firstName, lastName = "";
		
		System.out.print("Enter 1 to LOGIN or 2 to REGISTER: ");
		int ans = sc.nextInt();
		
		switch(ans) {
			case 1:
				System.out.print("Enter username: ");
				userName = sc.next();
				System.out.print("Enter password: ");
				password = sc.next();
				
				User currUser = userSrv.login(userName, password);
				System.out.println("Hello, "+currUser.getUsername()+"!");
				break;
			case 2:
				User newUser = new User();
				System.out.print("Enter your first name: ");
				firstName = sc.next();
				newUser.setFirstName(firstName);
				System.out.print("Enter your last name: ");
				lastName = sc.next();
				newUser.setLastName(lastName);
				System.out.print("Enter a username: ");
				userName = sc.next();
				newUser.setUsername(userName);
				System.out.print("Enter a password: ");
				password = sc.next();
				newUser.setPassword(password);
				
				newUser.setUserType(User.UserType.CUSTOMER);
				newUser.setId(Math.abs(newUser.hashCode()));
				
				userSrv.register(newUser);
				System.out.println("Account created, "+newUser.getUsername());
				break;
			default:
				System.out.println("Invailed option.");
		}
		
		sc.close();	
	}
}
