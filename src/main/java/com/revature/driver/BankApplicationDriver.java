package com.revature.driver;

/**
 * This is the entry point to the application
 */
import java.util.Scanner;

import com.revature.beans.User;
import com.revature.services.UserService;
import com.revature.dao.UserDaoFile;
import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.UsernameAlreadyExistsException;

import com.revature.beans.Account;
import com.revature.beans.Transaction;
import com.revature.dao.AccountDaoFile;
import com.revature.services.AccountService;
import com.revature.utils.SessionCache;

public class BankApplicationDriver {
	static boolean running = true;
	static boolean loggedOn = false;
	public Scanner sc = new Scanner(System.in);
	public int option = 0;
	public UserDaoFile userDao = new UserDaoFile();
	public AccountDaoFile accountDao = new AccountDaoFile();
	public UserService userSrv = new UserService(userDao, accountDao);
	public AccountService accSrv = new AccountService(accountDao);

	public static void main(String[] args) {
		BankApplicationDriver driver = new BankApplicationDriver();
		do {
			driver.welcomeMenu();
			if(loggedOn) {
				driver.mainMenu();
			}
		}while(running);
	
	}
	
	public void welcomeMenu() {
		System.out.println();
		System.out.println("Welcome to Revature Banking");
		System.out.println("*******************************************************************");
		System.out.print("1) LOGIN 2) REGISTER 3) EXIT [1-3]: ");
		option = sc.nextInt();
			
		switch(option) {
		case 1:
			loginForm();
			break;
		case 2:
			regForm();
			break;
		case 3:
			running = false;
			System.out.println("Have a great day! Goodbye!");
			break;
		default:
			System.out.println("Invalid option.");
		}
	}
	
	public void mainMenu() {
		while(true) {
			System.out.println();
			System.out.println("Hello, "+SessionCache.getCurrentUser().get().getUsername());
			System.out.println("*******************************************************************");
			System.out.println("MENU: 1) Make Transactions 2) Account Services 3) Logout");
			System.out.print("What would you like to do? [1-3]: ");
			option = sc.nextInt();
			
			switch(option) {
				case 1:
					transMenu();
					break;
				case 2:
					accMenu();
					break;
				case 3:
					SessionCache.setCurrentUser(null);
					loggedOn = false;
					return;
				default:
					System.out.println("Invalid option.");
			}
		}
	}
	
	public void loginForm() {
		String userName="", password="";
		
		System.out.println();
		System.out.println("Welcome Back! Please sign in.");
		System.out.println("*******************************************************************");
		System.out.print("Enter your username: ");
		userName = sc.next();
		System.out.print("Enter your password: ");
		password = sc.next();
		try {
			SessionCache.setCurrentUser(userSrv.login(userName, password));
			loggedOn=true;
		}catch(InvalidCredentialsException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void regForm() {
		String userName, password, firstName, lastName = "";
		User newUser = new User();
		boolean nameTaken = false;
		
		System.out.print("Are a 1) customer or 2) employee [1-2]: ");
		int ans = sc.nextInt();
		if(ans == 1) {
			newUser.setUserType(User.UserType.CUSTOMER);
		}else if(ans == 2) {
			newUser.setUserType(User.UserType.EMPLOYEE);
		}else {
			newUser.setUserType(User.UserType.CUSTOMER);
		}
		
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
		
		newUser.setId(Math.abs(newUser.hashCode()));
		do {
			try {
				userSrv.register(newUser);
				nameTaken=false;
			}catch(UsernameAlreadyExistsException e) {
				nameTaken=true;
				System.out.println(e.getMessage());
				System.out.print("Please enter a different username: ");
				userName = sc.next();
				newUser.setUsername(userName);
			}
		}while(nameTaken);
		
		try {
			SessionCache.setCurrentUser(userSrv.login(userName, password));
			loggedOn=true;
		}catch(InvalidCredentialsException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void displayAccounts() {
		System.out.println("*******************************************************************");
		System.out.println("Accounts: ");
		if(SessionCache.getCurrentUser().get().getAccounts() == null || SessionCache.getCurrentUser().get().getAccounts().isEmpty()) {
			System.out.println("No accounts to dsplay.");
		}else {
			for(Account a : SessionCache.getCurrentUser().get().getAccounts()) {
				System.out.println("ID: "+a.getId()+" Balance: "+a.getBalance()+" Type: "+a.getType()+" Status: "+(a.isApproved()?"Enabled":"Disabled"));
			}
		}
		System.out.println("*******************************************************************");
	}
	
	public void transMenu() {
		int accid = 0, toId = 0;
		double amount = 0.0d;
		displayAccounts();
		System.out.println("MENU: 1) DEPOSIT 2) WITHDRAW 3) TRANSFER 4) BACK");
		System.out.print("What would you like to do? [1-4]: ");
		option = sc.nextInt();
		switch(option) {
			case 1:
				System.out.print("Which account to use? Enter id: ");
				accid = sc.nextInt();
				System.out.print("How much are you depositing? Enter amount: ");
				amount = sc.nextDouble();
				accSrv.deposit(accSrv.actDao.getAccount(accid), amount);
				break;
			case 2:
				System.out.print("Which account to use? Enter id: ");
				accid = sc.nextInt();
				System.out.print("How much are you withdrawing? Enter amount: ");
				amount = sc.nextDouble();
				accSrv.withdraw(accSrv.actDao.getAccount(accid), amount);
				break;
			case 3:
				System.out.print("Which account to use? Enter id: ");
				accid = sc.nextInt();
				System.out.print("Which account are you transfering to? Enter id: ");
				toId = sc.nextInt();
				System.out.print("How much are you transfering? Enter amount: ");
				amount = sc.nextDouble();
				accSrv.transfer(accSrv.actDao.getAccount(accid), accSrv.actDao.getAccount(toId), amount);
				break;
			case 4:
				return;
			default:
				System.out.println("Invalid option.");
		}
	}
	
	public void accMenu() {
		displayAccounts();
		System.out.println("MENU: 1) CREATE Account 2) CHANGE Account Type 3) VEIW Transactions 4) BACK");
		System.out.print("What would you like to do? [1-4]: ");
		option = sc.nextInt();
		
		switch(option) {
			case 1:
				accSrv.createNewAccount(SessionCache.getCurrentUser().get());
				userDao.updateUser(SessionCache.getCurrentUser().get());
				System.out.println("New account created.");
				break;
			case 2:
				System.out.print("Change which account's type? Enter id: ");
				int accId = sc.nextInt();
				Account temp = accSrv.actDao.getAccount(accId);
				if(temp.getType().equals(Account.AccountType.CHECKING)) {
					temp.setType(Account.AccountType.SAVINGS);
				}else {
					temp.setType(Account.AccountType.CHECKING);
				}
				accSrv.actDao.updateAccount(temp);
				SessionCache.getCurrentUser().get().setAccounts(accSrv.actDao.getAccountsByUser(SessionCache.getCurrentUser().get()));
				userDao.updateUser(SessionCache.getCurrentUser().get());
				System.out.println("Account type changed to "+temp.getType());
				break;
			case 3:
				System.out.println("View the transactions of which account: ");
				accId = sc.nextInt();
				if(accSrv.actDao.getAccount(accId).getTransactions().size() == 0) {
					System.out.println("There have been no transactions made.");
				}else {
					for(Transaction t : accSrv.actDao.getAccount(accId).getTransactions()) {
						System.out.println(t);
					}
				}
				break;
			case 4:
				return;
			default:
				System.out.println("Invalid option.");
		}
	}
}
