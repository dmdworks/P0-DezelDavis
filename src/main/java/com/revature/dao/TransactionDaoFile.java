package com.revature.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.Transaction;

public class TransactionDaoFile implements TransactionDao {
	
	public static String fileLocation = "C:\\Users\\Dezel\\Desktop\\Revature Projects\\P0-DezelDavis\\Transactions.txt";
	List<Transaction> transList = new ArrayList<>();
	FileOutputStream transOutFile;
	ObjectOutputStream transOutput;
	FileInputStream transInFile;
	ObjectInputStream transInput;
	
	public TransactionDaoFile() {
		File fs = new File(fileLocation);
		
		if(fs.exists()) {
			//Writing an empty array to file.
			try{
				transOutFile = new FileOutputStream(fileLocation);
				transOutput = new ObjectOutputStream(transOutFile);
				
				transOutput.writeObject(transList);
				transOutput.close();
			}catch(FileNotFoundException e) {
				System.out.println("Users file is missing/in wrong location");
			}catch(IOException e) {
				System.out.println("An exception was thrown: "+e.getMessage());
			}

		}else {
			//Creating file if not found
			try {
				fs.createNewFile();
			}catch(IOException e) {
				System.out.println("File not created:"+e.getMessage());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<Transaction> getAllTransactions() {
		try{
			transInFile = new FileInputStream(fileLocation);
			transInput = new ObjectInputStream(transInFile);
			
			transList = (List<Transaction>)transInput.readObject();
			transInput.close();
		}catch(FileNotFoundException e) {
			System.out.println("Users file is missing/in wrong location");
		}catch(IOException e) {
			System.out.println("An exception was thrown: "+e.getMessage());
		}catch(ClassNotFoundException e) {
			System.out.println("An exception was thrown: "+e.getMessage());
		}
		
		return transList;
	}

}
