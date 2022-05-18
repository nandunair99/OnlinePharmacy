package com.narola.pharmacy.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.narola.pharmacy.PharmacyDBException;

/**
 * @author nnandakrishnan
 * 
 *         this file is used to initate Database Connection
 */

public class PharmacyDBConnection {

	private static PharmacyDBConnection pharmacyDBConnection=null;
	private Connection connection;
	private String url = null;
	private String username = null;
	private String password = null;
	private String dbname = null;
	
	private PharmacyDBConnection()
	{
		
	}
	
	public static PharmacyDBConnection getInstance() {
		if (pharmacyDBConnection == null) {
			pharmacyDBConnection = new PharmacyDBConnection();
		}
		return pharmacyDBConnection;
	}
	
	public Connection getConnection() throws PharmacyDBException {
		try {
			if (connection == null) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(getUrl() + "/" + getDbname(), getUsername(), getPassword());
			}
		} catch (ClassNotFoundException ex) {
			throw new PharmacyDBException("Error while connecting DB" + ex);
		} catch (SQLException ex) {
			throw new PharmacyDBException("Error while connecting DB" + ex);
		}

		return connection;
	}
	
	

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	

	public static void releaseResource(PreparedStatement preparedStatement) {
		releaseResource(preparedStatement, null, null);
	}

	public static void releaseResource(Statement statement) {
		releaseResource(null, null, statement);
	}

	public static void releaseResource(Statement statement, ResultSet resultSet) {
		releaseResource(null, resultSet, statement);
	}

	public static void releaseResource(PreparedStatement pstatement, ResultSet resultSet) {
		releaseResource(pstatement, resultSet, null);
	}

	public static PharmacyDBConnection getPharmacyDBConnection() {
		return pharmacyDBConnection;
	}

	public static void setPharmacyDBConnection(PharmacyDBConnection pharmacyDBConnection) {
		PharmacyDBConnection.pharmacyDBConnection = pharmacyDBConnection;
	}

	
	public static void releaseResource(PreparedStatement preparedStatement, ResultSet resultSet, Statement statement) {
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
