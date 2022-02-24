package com.revature.dao;

import java.util.ArrayList;
import java.util.List;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import com.revature.beans.User;

/**
 * Implementation of UserDAO that reads and writes to a file
 */
public class UserDaoFile implements UserDao {
	
	public static String fileLocation = "C:\\Users\\Dezel\\Desktop\\Revature Projects\\P0-DezelDavis\\Users.txt";
	List<User> userList = new ArrayList<>();
	FileOutputStream userOutFile;
	ObjectOutputStream userOutput;
	FileInputStream userInFile;
	ObjectInputStream userInput;

	@SuppressWarnings("unchecked")
	public User addUser(User user) {
		
		try{
			userInFile = new FileInputStream(fileLocation);
			userInput = new ObjectInputStream(userInFile);
			
			userList = (List<User>)userInput.readObject();
			userInput.close();
		}catch(FileNotFoundException e) {
			System.out.println("Users file is missing/in wrong location");
		}catch(IOException e) {
			System.out.println("An exception was thrown: "+e.getMessage());
		}catch(ClassNotFoundException e) {
			System.out.println("An exception was thrown: "+e.getMessage());
		}
		
		userList.add(user);
	
		try{
			userOutFile = new FileOutputStream(fileLocation);
			userOutput = new ObjectOutputStream(userOutFile);
			
			userOutput.writeObject(userList);
			userOutput.close();
		}catch(FileNotFoundException e) {
			System.out.println("Users file is missing/in wrong location");
		}catch(IOException e) {
			System.out.println("An exception was thrown: "+e.getMessage());
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	public User getUser(Integer userId) {
		
		try{
			userInFile = new FileInputStream(fileLocation);
			userInput = new ObjectInputStream(userInFile);
			
			userList = (List<User>)userInput.readObject();
			userInput.close();
		}catch(FileNotFoundException e) {
			System.out.println("Users file is missing/in wrong location");
		}catch(IOException e) {
			System.out.println("An exception was thrown: "+e.getMessage());
		}catch(ClassNotFoundException e) {
			System.out.println("An exception was thrown: "+e.getMessage());
		}
		
		for(User u: userList) {
			if(u.getId().equals(userId)) {
				return u;
			}
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	public User getUser(String username, String pass) {
		
		try{
			userInFile = new FileInputStream(fileLocation);
			userInput = new ObjectInputStream(userInFile);
			
			userList = (List<User>)userInput.readObject();
			userInput.close();
		}catch(FileNotFoundException e) {
			System.out.println("Users file is missing/in wrong location");
		}catch(IOException e) {
			System.out.println("An exception was thrown: "+e.getMessage());
		}catch(ClassNotFoundException e) {
			System.out.println("An exception was thrown: "+e.getMessage());
		}
		
		for(User u: userList) {
			if(u.getUsername().equals(username)) {
				return u;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		try{
			userInFile = new FileInputStream(fileLocation);
			userInput = new ObjectInputStream(userInFile);
			
			userList = (List<User>)userInput.readObject();
			userInput.close();
		}catch(FileNotFoundException e) {
			System.out.println("Users file is missing/in wrong location");
		}catch(IOException e) {
			System.out.println("An exception was thrown: "+e.getMessage());
		}catch(ClassNotFoundException e) {
			System.out.println("An exception was thrown: "+e.getMessage());
		}
		
		return userList;
	}

	public User updateUser(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean removeUser(User u) {
		// TODO Auto-generated method stub
		return false;
	}

}
