package com.revature.dao;

import java.util.ArrayList;
import java.util.List;

import java.io.File;

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
	
	public UserDaoFile() {
		File fs = new File(fileLocation);
		
		if(!fs.exists()) {
			//Creating file if not found
			try {
				fs.createNewFile();
			}catch(IOException e) {
				System.out.println("File not created:"+e.getMessage());
			}
			
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
		}
	}

	public User addUser(User user) {
		userList = getAllUsers();
		
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
		
		
		return user;
	}

	public User getUser(Integer userId) {
		userList = getAllUsers();
		
		for(User u: userList) {
			if(u.getId().equals(userId)) {
				return u;
			}
		}
		
		return null;
	}

	public User getUser(String username, String pass) {
		userList = getAllUsers();

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
		userList = getAllUsers();
		int index = 0;
		
		for(User user: userList) {
			if(user.getId().equals(u.getId())) {
				break;
			}
			index++;
		}
		
		userList.set(index, u);
		
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
		
		return u;
	}

	public boolean removeUser(User u) {
		userList = getAllUsers();
		int index = 0;
		
		for(User user: userList) {
			if(user.getId().equals(u.getId())) {
				break;
			}
			index++;
		}
		
		userList.remove(index);
		
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
		
		if(userList.contains(u)) {
			return false;
		}else {
			return true;
		}
		
	}

}
