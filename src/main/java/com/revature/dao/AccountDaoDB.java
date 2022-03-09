package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.Account;
import com.revature.beans.User;
import com.revature.utils.ConnectionUtil;

/**
 * Implementation of AccountDAO which reads/writes to a database
 */
public class AccountDaoDB implements AccountDao {
	private Connection con;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	List<Account> accList = new ArrayList<>();
	
	public AccountDaoDB() {
		con = ConnectionUtil.getConnectionUtil().getConnection();
	}

	public Account addAccount(Account a) {
		String query = "INSERT INTO accounts (owner_id, balance, acc_type, approval) VALUES (?, ?, ?, ?)";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, a.getOwnerId());
			pstmt.setDouble(2, a.getBalance());
			if(a.getType().equals(Account.AccountType.CHECKING)) {
				pstmt.setInt(3, 0);
			}else {
				pstmt.setInt(3, 1);
			}
			pstmt.setBoolean(4, a.isApproved());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return a;
	}

	public Account getAccount(Integer actId) {
		String query = "SELECT * FROM accounts WHERE acc_id="+actId;
		Account acc = new Account();
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()) {
				acc.setId(rs.getInt("acc_id"));
				acc.setOwnerId(rs.getInt("owner_id"));
				acc.setBalance(rs.getDouble("balance"));
				if(rs.getInt("acc_type")==0) {
					acc.setType(Account.AccountType.CHECKING);
				}else {
					acc.setType(Account.AccountType.SAVINGS);
				}
				acc.setApproved(rs.getBoolean("approval"));
	
				return acc;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public List<Account> getAccounts() {
		String query = "SELECT * FROM accounts";
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				Account acc = new Account();
				acc.setId(rs.getInt("acc_id"));
				acc.setOwnerId(rs.getInt("owner_id"));
				acc.setBalance(rs.getDouble("balance"));
				if(rs.getInt("acc_type")==0) {
					acc.setType(Account.AccountType.CHECKING);
				}else {
					acc.setType(Account.AccountType.SAVINGS);
				}
				acc.setApproved(rs.getBoolean("approval"));
				accList.add(acc);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return accList;
	}

	public List<Account> getAccountsByUser(User u) {
		String query = "SELECT * FROM accounts WHERE owner_id="+u.getId();
		List<Account> tempAccs = new ArrayList<>();
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				Account acc = new Account();
				acc.setId(rs.getInt("acc_id"));
				acc.setOwnerId(rs.getInt("owner_id"));
				acc.setBalance(rs.getDouble("balance"));
				if(rs.getInt("acc_type")==0) {
					acc.setType(Account.AccountType.CHECKING);
				}else {
					acc.setType(Account.AccountType.SAVINGS);
				}
				acc.setApproved(rs.getBoolean("approval"));
				tempAccs.add(acc);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return tempAccs;
	}

	public Account updateAccount(Account a) {
		String query = "UPDATE accounts SET balance=?, acc_type=?, approval=? WHERE acc_id="+a.getId();
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setDouble(1, a.getBalance());
			if(a.getType().equals(Account.AccountType.CHECKING)) {
				pstmt.setInt(2, 0);
			}else {
				pstmt.setInt(2, 1);
			}
			pstmt.setBoolean(3, a.isApproved());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return a;
	}

	public boolean removeAccount(Account a) {
		String query = "DELETE FROM accounts WHERE acc_id="+a.getId();
		
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return true;
	}

}
