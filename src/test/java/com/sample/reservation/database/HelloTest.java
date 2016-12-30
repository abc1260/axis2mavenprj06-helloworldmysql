package com.sample.reservation.database;

import java.sql.Connection;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.aikiinc.axis2.hello.pojo.Hello;
import com.aikiinc.axis2.mysql.DBConnection;
import junit.framework.Assert;

public class HelloTest {
	private static DBConnection dbConnection;

	@BeforeClass
	public static void init() throws Exception {
		dbConnection = DBConnection.getDBConnection();
	}

	@AfterClass
	public static void after() throws Exception {
		try {
			Connection connection = DBConnection.getConnection();
			connection.close();
		} catch (Exception e) {
		}
	}

	@Test
	public void getConnection() {
		try {
			Connection con = dbConnection.getConnection();
			Assert.assertNotNull(con);
		} catch (Exception e) {
			Assert.fail("We did not get the connection");
		}
	}

	@Test
	public void setMessage() {
		try {
			int rnum = new Random().nextInt(1000);

			boolean rtn = dbConnection.setMessage("fellow" + rnum);
			Assert.assertTrue(rtn);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("We did not add hello");
		}
	}

	@Test
	public void getMessage() {
		try {
			Hello hello = dbConnection.getMessage("fellow138");
			Assert.assertNotNull(hello);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("We did not add hello");
		}
	}

	@Test
	public void getUniqueMessage() {
		try {
			Hello hello = dbConnection.getUniqueMessage("fellow138");
			Assert.assertNotNull(hello);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("We did not add hello");
		}
	}

}
