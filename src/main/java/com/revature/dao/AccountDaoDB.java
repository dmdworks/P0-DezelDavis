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
	List<User> accList = new ArrayList<>();
	
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
		// TODO Auto-generated method stub
		return null;
	}

	public List<Account> getAccountsByUser(User u) {
		// TODO Auto-generated method stub
		return null;
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
