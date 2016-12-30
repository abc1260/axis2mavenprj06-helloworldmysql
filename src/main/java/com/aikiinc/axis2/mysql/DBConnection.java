package com.aikiinc.axis2.mysql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import com.aikiinc.axis2.hello.pojo.Hello;

public class DBConnection {
	private static Connection connection = null;
	private static DBConnection dbConnection;

	private DBConnection() throws Exception {
		connection = initConnection();
	}

	public static DBConnection getDBConnection() throws Exception {
		if (dbConnection == null)
			dbConnection = new DBConnection();

		return dbConnection;
	}

	public static Connection getConnection() throws Exception {
		if (connection == null)
			getDBConnection();

		return connection;
	}

	private static Connection initConnection() throws Exception {
		String driverName = "com.mysql.jdbc.Driver";
		String host = null;
		String port = null;
		String userName = null;
		String password = null;
		String conectionURI = null;
		Properties properties = new Properties();

		try {
			InputStream propertyInputStream = DBConnection.class.getClassLoader()
					.getResourceAsStream("mysql.properties");
			properties.load(propertyInputStream);
			userName = properties.getProperty("mysql.username");
			password = properties.getProperty("mysql.password");
			host = properties.getProperty("mysql.host");
			port = properties.getProperty("mysql.port");

			conectionURI = "jdbc:mysql://" + host + ":" + port + "/" + properties.getProperty("mysql.db");
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Class driverClass = Class.forName(driverName);
			Connection connection = DriverManager.getConnection(conectionURI, userName, password);
			connection.setAutoCommit(true);
			return connection;
		} catch (ClassNotFoundException e) {
			throw new Exception("Can not find the driver class ", e);
		} catch (SQLException e) {
			throw new Exception("Can not get the data base conneciton", e);
		}
	}

	public boolean setMessage(String msg) throws Exception {
		boolean inserted = false;
		String sql = "insert into MESSAGE(message) value('" + msg + "')";
		Statement statement = null;

		try {
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			inserted = true;
		} catch (SQLException e) {
			throw new Exception("Can not execute the sql query", e);
		} finally {
			try {
				statement.close();
			} catch (Exception e) {
			}
		}

		return inserted;
	}

	public Hello getMessage(String msg) throws Exception {
		Hello hello = null;
		PreparedStatement statement = null;

		try {
			String message = null;

			String sqlStatement = "SELECT * FROM MESSAGE WHERE message like ?";
			statement = connection.prepareStatement(sqlStatement);
			statement.setString(1, "%" + msg + "%");

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				message = rs.getString("message");
			}

			// System.out.println("" + statement.toString());
			// System.out.println("message: " + message + ", data: " + msg);
			if (message != null) {
				hello = new Hello();
				hello.setMessage(message);
			}
		} catch (SQLException e) {
			throw new Exception("Can not execute the sql query", e);
		} finally {
			try {
				statement.close();
			} catch (Exception e) {
			}
		}

		return hello;
	}

	public Hello getUniqueMessage(String msg) throws Exception {
		Hello hello;

		hello = getMessage(msg);
		if (hello == null) {
			if (setMessage(msg))
				hello = getMessage(msg);
		}

		return hello;
	}
}