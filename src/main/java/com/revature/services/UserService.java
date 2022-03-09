package com.revature.services;

import com.revature.beans.User;
import com.revature.dao.AccountDao;
import com.revature.dao.UserDao;

import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.exceptions.InvalidCredentialsException;

import org.apache.log4j.Logger;

/**
 * This class should contain the business logic for performing operations on users
 */
public class UserService {
	
	UserDao userDao;
	AccountDao accountDao;
	static Logger log = Logger.getLogger(UserService.class.getName());
	
	public UserService(UserDao udao, AccountDao adao) {
		this.userDao = udao;
		this.accountDao = adao;
	}
	
	/**
	 * Validates the username and password, and return the User object for that user
	 * @throws InvalidCredentialsException if either username is not found or password does not match
	 * @return the User who is now logged in
	 */
	public User login(String username, String password) {
		User chkUser = userDao.getUser(username, password);
		if(chkUser == null) {
			log.debug("A user attempted login with invalied credentials.");
			throw new InvalidCredentialsException();
		}else if(!chkUser.getPassword().equals(password)){
			log.debug("A user attempted login with invalied credentials.");
			throw new InvalidCredentialsException();
		}else {
			log.info("User logged in.");
			return chkUser;
		}
	}
	
	/**
	 * Creates the specified User in the persistence layer
	 * @param newUser the User to register
	 * @throws UsernameAlreadyExistsException if the given User's username is taken
	 */
	public void register(User newUser) {
		User chkUser = userDao.getUser(newUser.getUsername(), newUser.getPassword());
		if(chkUser != null) {
			log.debug("A user attempted to register with taken username.");
			throw new UsernameAlreadyExistsException();
		}else {
			log.info("User registered.");
			userDao.addUser(newUser);
		}
	}
}
