package com.revature.services;

import com.revature.beans.Account;
import com.revature.beans.User;
import com.revature.dao.AccountDao;
import com.revature.exceptions.OverdraftException;

import com.revature.beans.Transaction;
import com.revature.dao.TransactionDaoFile;

/**
 * This class should contain the business logic for performing operations on Accounts
 */
public class AccountService {
	
	public AccountDao actDao;
	public static final double STARTING_BALANCE = 25d;
	
	public AccountService(AccountDao dao) {
		this.actDao = dao;
	}
	
	/**
	 * Withdraws funds from the specified account
	 * @throws OverdraftException if amount is greater than the account balance
	 * @throws UnsupportedOperationException if amount is negative
	 */
	public void withdraw(Account a, Double amount) {
		if(amount < 0) {
			throw new UnsupportedOperationException();
		}else if(amount > a.getBalance()) {
			throw new OverdraftException();
		}else {
			a.setBalance(a.getBalance()-amount);
			
			Transaction newTran = new Transaction();
			newTran.setType(Transaction.TransactionType.WITHDRAWAL);
			newTran.setAmount(amount);
			newTran.setSender(a);
			newTran.setTimestamp();
			
			a.getTransactions().add(newTran);
		}
	}
	
	/**
	 * Deposit funds to an account
	 * @throws UnsupportedOperationException if amount is negative
	 */
	public void deposit(Account a, Double amount) {
		if (!a.isApproved()) {
			throw new UnsupportedOperationException();
		}
		
		if(amount < 0) {
			throw new UnsupportedOperationException();
		}else {
			a.setBalance(a.getBalance()+amount);
			
			Transaction newTran = new Transaction();
			newTran.setType(Transaction.TransactionType.DEPOSIT);
			newTran.setAmount(amount);
			newTran.setSender(a);
			newTran.setTimestamp();
			
			a.getTransactions().add(newTran);
		}
	}
	
	/**
	 * Transfers funds between accounts
	 * @throws UnsupportedOperationException if amount is negative or 
	 * the transaction would result in a negative balance for either account
	 * or if either account is not approved
	 * @param fromAct the account to withdraw from
	 * @param toAct the account to deposit to
	 * @param amount the monetary value to transfer
	 */
	public void transfer(Account fromAct, Account toAct, double amount) {
		if( (amount < 0) || (amount > fromAct.getBalance()) ) {
			throw new UnsupportedOperationException();
		}else if( !fromAct.isApproved() || !toAct.isApproved() ) {
			throw new UnsupportedOperationException();
		}else {
			fromAct.setBalance(fromAct.getBalance()-amount);
			toAct.setBalance(toAct.getBalance()+amount);
			
			Transaction newTran = new Transaction();
			newTran.setType(Transaction.TransactionType.TRANSFER);
			newTran.setAmount(amount);
			newTran.setSender(fromAct);
			newTran.setRecipient(toAct);
			newTran.setTimestamp();
			
			fromAct.getTransactions().add(newTran);
			toAct.getTransactions().add(newTran);
		}
		
	}
	
	/**
	 * Creates a new account for a given User
	 * @return the Account object that was created
	 */
	public Account createNewAccount(User u) {
		Account newAcc = new Account();
		
		newAcc.setOwnerId(u.getId());
		newAcc.setBalance(STARTING_BALANCE);
		newAcc.setType(Account.AccountType.CHECKING);
		newAcc.setApproved(true);
		newAcc.setId(Math.abs(newAcc.hashCode()));
		
		u.getAccounts().add(newAcc);
		return newAcc;
	}
	
	/**
	 * Approve or reject an account.
	 * @param a
	 * @param approval
	 * @throws UnauthorizedException if logged in user is not an Employee
	 * @return true if account is approved, or false if unapproved
	 */
	public boolean approveOrRejectAccount(Account a, boolean approval) {
		
		return false;
	}
}
