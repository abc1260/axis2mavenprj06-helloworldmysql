package com.aikiinc.axis2.hello;

import java.sql.Connection;

import com.aikiinc.axis2.mysql.DBConnection;

public class HelloWorld {

	public HelloWorld() throws Exception {
		Connection con = DBConnection.getConnection();
	}

	public String message(String name) {
		return "Hello : " + name;
	}
}