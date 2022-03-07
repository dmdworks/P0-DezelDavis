package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;
import java.util.ArrayList;

import com.revature.beans.User;
import com.revature.utils.ConnectionUtil;

/**
 * Implementation of UserDAO that reads/writes to a relational database
 */
public class UserDaoDB implements UserDao {
	private Connection con;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	List<User> userList = new ArrayList<>();
	
	public UserDaoDB() {
		con = ConnectionUtil.getConnectionUtil().getConnection();
	}

	public User addUser(User user) {
		String query = "INSERT INTO users (username, pass, first_name, last_name, user_type) VALUES (?, ?, ?, ?, ?)";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getFirstName());
			pstmt.setString(4, user.getLastName());
			if(user.getUserType().equals(User.UserType.EMPLOYEE)) {
				pstmt.setInt(5, 1);
			}else {
				pstmt.setInt(5, 0);
			}
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return user;
	}

	public User getUser(Integer userId) {
		String query = "SELECT * FROM users WHERE user_id="+userId;
		User user = new User();
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()) {
				user.setId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("pass"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				if(rs.getInt("user_type")==1) {
					user.setUserType(User.UserType.EMPLOYEE);
				}else {
					user.setUserType(User.UserType.CUSTOMER);
				}
				return user;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public User getUser(String username, String pass) {
		String query = "SELECT * FROM users WHERE username='"+username+"'";
		User user = new User();
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()) {
				user.setId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("pass"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				if(rs.getInt("user_type")==1) {
					user.setUserType(User.UserType.EMPLOYEE);
				}else {
					user.setUserType(User.UserType.CUSTOMER);
				}
				return user;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}

	public List<User> getAllUsers() {
		String query = "SELECT * FROM users";
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("pass"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				if(rs.getInt("user_type")==1) {
					user.setUserType(User.UserType.EMPLOYEE);
				}else {
					user.setUserType(User.UserType.CUSTOMER);
				}
				userList.add(user);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return userList;
	}

	public User updateUser(User u) {
		String query = "UPDATE users SET username=?, pass=?, first_name=?, last_name=?, user_type=? WHERE user_id="+u.getId();
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, u.getUsername());
			pstmt.setString(2, u.getPassword());
			pstmt.setString(3, u.getFirstName());
			pstmt.setString(4, u.getLastName());
			if(u.getUserType().equals(User.UserType.EMPLOYEE)) {
				pstmt.setInt(5, 1);
			}else {
				pstmt.setInt(5, 0);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return u;
	}

	public boolean removeUser(User u) {
		String query = "DELETE FROM users WHERE user_id="+u.getId();
		
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return true;
	}

}
