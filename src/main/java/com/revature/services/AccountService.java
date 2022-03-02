package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import com.revature.beans.Account;
import com.revature.beans.User;
import com.revature.dao.AccountDao;
import com.revature.exceptions.OverdraftException;

import com.revature.beans.Transaction;

import com.revature.utils.SessionCache;
import com.revature.exceptions.UnauthorizedException;

/**
 * This class should contain the business logic for performing operations on Accounts
 */
public class AccountService {
	
	public AccountDao actDao;
	public static final double STARTING_BALANCE = 25d;
	List<Account> accList = new ArrayList<>();
	List<Transaction> transList = new ArrayList<>();
	
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
			
			transList = a.getTransactions();
			transList.add(newTran);
			a.setTransactions(transList);
			transList = null;
			
			actDao.updateAccount(a);
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
			
			
			transList = a.getTransactions();
			transList.add(newTran);
			a.setTransactions(transList);
			transList = null;
			
			actDao.updateAccount(a);
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
			
			transList = fromAct.getTransactions();
			transList.add(newTran);
			fromAct.setTransactions(transList);
			transList = null;
			
			transList = toAct.getTransactions();
			transList.add(newTran);
			toAct.setTransactions(transList);
			transList = null;
			
			actDao.updateAccount(toAct);
			actDao.updateAccount(fromAct);
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
		newAcc.setApproved(false);
		newAcc.setId(Math.abs(newAcc.hashCode()+(u.getAccounts().size()*34)));
		
		actDao.addAccount(newAcc);
		
		accList = actDao.getAccountsByUser(u);
		if(accList.size() == 0) {
			accList.add(newAcc);
		}
		u.setAccounts(accList);
		accList = null;
		

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
		if(SessionCache.getCurrentUser().get().getUserType().equals(User.UserType.EMPLOYEE)) {
			a.setApproved(approval);
			actDao.updateAccount(a);
		}else {
			throw new UnauthorizedException();
		}
		return approval;
	}
}
