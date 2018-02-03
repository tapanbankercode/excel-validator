package io.database.postgresql;

import java.sql.*;

/**
 * Utility to Connect to PostGre Database
 * @Author Tapan N. Banker
 * This Class Provides Connection to the Database Postgres SQL
 */
public class PostgresDbUtility {

	private Connection conn;
	private String host;
	private String dbName;
	private String user;
	private String pass;

	// Default Constructor
	protected PostgresDbUtility() {}

	// Postgres Database Constructor
	public PostgresDbUtility(String host, String dbName, String user, String pass) {
		this.host = host;
		this.dbName = dbName;
		this.user = user;
		this.pass = pass;
	}


	/**
	 * This method will return the Database Connection for Postgres Database
	 * @return Connection Object
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		if (host.isEmpty() || dbName.isEmpty() || user.isEmpty() || pass.isEmpty()) {
			throw new SQLException("Database credentials missing");
		}
		Class.forName("org.postgresql.Driver");
		this.conn = DriverManager.getConnection(
				this.host + this.dbName,
				this.user, this.pass);
		return conn;
	}

}