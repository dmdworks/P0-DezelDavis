package com.revature.dao;

import java.util.List;
import java.util.ArrayList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import com.revature.beans.Account;
import com.revature.beans.User;

/**
 * Implementation of AccountDAO which reads/writes to files
 */
public class AccountDaoFile implements AccountDao {
	// use this file location to persist the data to
	public static String fileLocation = "C:\\Users\\Dezel\\Desktop\\Revature Projects\\P0-DezelDavis\\Accounts.txt";
	List<Account> accList = new ArrayList<>();
	FileOutputStream accOutFile;
	ObjectOutputStream accOutput;
	FileInputStream accInFile;
	ObjectInputStream accInput;

	public Account addAccount(Account a) {
		accList = getAccounts();
		
		accList.add(a);
		
		try{
			accOutFile = new FileOutputStream(fileLocation);
			accOutput = new ObjectOutputStream(accOutFile);
			
			accOutput.writeObject(accList);
			accOutput.close();
		}catch(FileNotFoundException e) {
			System.out.println("Accounts file is missing/in wrong location");
		}catch(IOException e) {
			System.out.println("An exception was thrown: "+e.getMessage());
		}
		
		return null;
	}

	public Account getAccount(Integer actId) {
		accList = getAccounts();
		
		for(Account a: accList) {
			if(a.getId().equals(actId)) {
				return a;
			}
		}
		return null;
	}

	public List<Account> getAccounts() {
		try{
			accInFile = new FileInputStream(fileLocation);
			accInput = new ObjectInputStream(accInFile);
			
			accList = (List<Account>)accInput.readObject();
			accInput.close();
		}catch(FileNotFoundException e) {
			System.out.println("Accounts file is missing/in wrong location");
		}catch(IOException e) {
			System.out.println("An exception was thrown: "+e.getMessage());
		}catch(ClassNotFoundException e) {
			System.out.println("An exception was thrown: "+e.getMessage());
		}
		
		return accList;
	}

	public List<Account> getAccountsByUser(User u) {
		return u.getAccounts();
	}

	public Account updateAccount(Account a) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean removeAccount(Account a) {
		// TODO Auto-generated method stub
		return false;
	}

}
