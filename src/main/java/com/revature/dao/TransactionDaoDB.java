package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.Transaction;
import com.revature.utils.ConnectionUtil;

public class TransactionDaoDB implements TransactionDao {
	Connection con;
	PreparedStatement pstmt;
	Statement stmt;
	ResultSet rs;
	AccountDaoDB actDao = new AccountDaoDB();
	
	public TransactionDaoDB() {
		con = ConnectionUtil.getConnectionUtil().getConnection();
	}
	
	public void saveTransaction(Transaction t) {
		if(t.getType().equals(Transaction.TransactionType.TRANSFER)) {
			String query = "INSERT INTO transactions (fromact_id, toact_id, amount, trans_type, t_stamp) VALUES (?, ?, ?, ?, ?)";
			try {
				pstmt = con.prepareStatement(query);
				
				pstmt.setInt(1, t.getSender().getId());
				pstmt.setInt(2, t.getRecipient().getId());
				pstmt.setDouble(3, t.getAmount());
				if(t.getType().equals(Transaction.TransactionType.DEPOSIT)) {
					pstmt.setInt(4, 0);
				}else if(t.getType().equals(Transaction.TransactionType.WITHDRAWAL)) {
					pstmt.setInt(4, 1);
				}else {
					pstmt.setInt(4, 2);
				}
				pstmt.setTimestamp(5, Timestamp.valueOf(t.getTimestamp()));
				pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			String query = "INSERT INTO transactions (fromact_id, amount, trans_type, t_stamp) VALUES (?, ?, ?, ?)";
			try {
				pstmt = con.prepareStatement(query);
				
				pstmt.setInt(1, t.getSender().getId());
				pstmt.setDouble(2, t.getAmount());
				if(t.getType().equals(Transaction.TransactionType.DEPOSIT)) {
					pstmt.setInt(3, 0);
				}else if(t.getType().equals(Transaction.TransactionType.WITHDRAWAL)) {
					pstmt.setInt(3, 1);
				}else {
					pstmt.setInt(3, 2);
				}
				pstmt.setTimestamp(4, Timestamp.valueOf(t.getTimestamp()));
				pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public List<Transaction> getAllTransactions() {
		String query = "SELECT * FROM transactions";
		List<Transaction> tranList = new ArrayList<>();
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				Transaction temp = new Transaction();
				temp.setSender(actDao.getAccount(rs.getInt("fromact_id")));
				temp.setRecipient(actDao.getAccount(rs.getInt("fromact_id")));
				temp.setAmount(rs.getDouble("amount"));
				if(rs.getInt("trans_type") == 0) {
					temp.setType(Transaction.TransactionType.DEPOSIT);
				}else if(rs.getInt("trans_type") == 1) {
					temp.setType(Transaction.TransactionType.WITHDRAWAL);
				}else {
					temp.setType(Transaction.TransactionType.TRANSFER);
				}
				temp.setTimestamp(rs.getTimestamp("t_stamp").toLocalDateTime());
				tranList.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tranList;
	}

}
