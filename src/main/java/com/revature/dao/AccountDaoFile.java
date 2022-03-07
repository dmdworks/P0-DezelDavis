package com.revature.dao;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
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
	public static String fileLocation = "Accounts.txt";
	List<Account> accList = new ArrayList<>();
	FileOutputStream accOutFile;
	ObjectOutputStream accOutput;
	FileInputStream accInFile;
	ObjectInputStream accInput;
	
	public AccountDaoFile() {
		File fs = new File(fileLocation);
		
		if(!fs.exists()) {
			//Creating file if not found
			try {
				fs.createNewFile();
			}catch(IOException e) {
				System.out.println("File not created:"+e.getMessage());
			}
			
			try{
				accOutFile = new FileOutputStream(fileLocation);
				accOutput = new ObjectOutputStream(accOutFile);
				
				accOutput.writeObject(accList);
				accOutput.close();
			}catch(FileNotFoundException e) {
				System.out.println("Account file is missing/in wrong location");
			}catch(IOException e) {
				System.out.println("An exception was thrown: "+e.getMessage());
			}
		}
	}

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
		
		return a;
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

	@SuppressWarnings("unchecked")
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
		List<Account> tempAccs = new ArrayList<>();
		accList = getAccounts();
		
		for(Account a : accList) {
			if(u.getId().equals(a.getOwnerId())) {
				tempAccs.add(a);
			}
		}
		
		return tempAccs;
	}

	public Account updateAccount(Account a) {
		accList = getAccounts();
		int index = 0;
		
		for(Account acc: accList) {
			if(acc.getId().equals(a.getId())) {
				break;
			}
			index++;
		}
		
		accList.set(index, a);
		
		try{
			accOutFile = new FileOutputStream(fileLocation);
			accOutput = new ObjectOutputStream(accOutFile);
			
			accOutput.writeObject(accList);
			accOutput.close();
		}catch(FileNotFoundException e) {
			System.out.println("Users file is missing/in wrong location");
		}catch(IOException e) {
			System.out.println("An exception was thrown: "+e.getMessage());
		}
		
		return a;
	}

	public boolean removeAccount(Account a) {
		accList = getAccounts();
		int index = 0;
		
		for(Account acc: accList) {
			if(acc.getId().equals(a.getId())) {
				break;
			}
			index++;
		}
		
		accList.remove(index);
		
		try{
			accOutFile = new FileOutputStream(fileLocation);
			accOutput = new ObjectOutputStream(accOutFile);
			
			accOutput.writeObject(accList);
			accOutput.close();
		}catch(FileNotFoundException e) {
			System.out.println("Users file is missing/in wrong location");
		}catch(IOException e) {
			System.out.println("An exception was thrown: "+e.getMessage());
		}
		
		if(accList.contains(a)) {
			return false;
		}else {
			return true;
		}
	}

}
