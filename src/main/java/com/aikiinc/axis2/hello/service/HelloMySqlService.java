package com.aikiinc.axis2.hello.service;

import com.aikiinc.axis2.hello.pojo.Hello;
import com.aikiinc.axis2.mysql.DBConnection;

/**
 * Send a <name> to the service, it echos the name with Hello <name>! Plus, log the unique hello message in the
 * database.
 * 
 * @author Philip Jahmani Chauvet
 */
public class HelloMySqlService {
	private DBConnection dbConnection;

	public HelloMySqlService() {
		try {
			dbConnection = DBConnection.getDBConnection();
		} catch (Exception e) {
		}
	}

	public String message(String msg) {
		try {
			if(msg == null)
				throw new Exception("Message can not be empty!");
			
			Hello hello = dbConnection.getUniqueMessage(msg);

			return hello.getMessage();
		} catch (Exception e) {
			return "Message '" + msg + "' error: " + e.getMessage();
		}
	}
}