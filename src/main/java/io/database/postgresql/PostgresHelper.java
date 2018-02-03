package io.database.postgresql;

import io.excel.validation.Measures.Measures;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Utility to Connect to PostGre
 * @Author Tapan N. Banker
 */
public class PostgresHelper {

	private Connection conn;
	private String host;
	private String dbName;
	private String user;
	private String pass;
	
	// Default Constructo
	protected PostgresHelper() {}
	
	public PostgresHelper(String host, String dbName, String user, String pass) {
		this.host = host;
		this.dbName = dbName;
		this.user = user;
		this.pass = pass;
	}

	/**
	 * This method will provide the database connection Object
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean connect() throws SQLException, ClassNotFoundException {
		if (host.isEmpty() || dbName.isEmpty() || user.isEmpty() || pass.isEmpty()) {
			throw new SQLException("Database credentials missing");
		}
		Class.forName("org.postgresql.Driver");
		this.conn = DriverManager.getConnection(
				this.host + this.dbName,
				this.user, this.pass);
		return true;
	}



	public ResultSet execQuery(String query) throws SQLException {
		return this.conn.createStatement().executeQuery(query);
	}


	public void insertData(List<Measures> measureList)throws SQLException {

		PreparedStatement preparedStatement = null;
		try {
		// Insert SQL Statement
		String insertTableSQL = "INSERT INTO Measures "+
				"(ptn, measure, standardizedMeasureName, measureType, improvementAreaGoal, nationalStandardDefinitionUsed, acronyms, identifiers," +
				" nqf, pqrs, cms, other, numeratorDefinition, denominatorDefinition) VALUES "
				+  "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		preparedStatement = this.conn.prepareStatement(insertTableSQL);

		for (Measures b : measureList) {
			preparedStatement.setString(1, b.getPtn());
			preparedStatement.setString(2, b.getMeasure());
			preparedStatement.setString(3, b.getStandardizedMeasureName());
			preparedStatement.setString(4, b.getMeasureType());
			preparedStatement.setString(5, b.getImprovementAreaGoal());
			preparedStatement.setString(6, b.getNationalStandardDefinitionUsed());
			preparedStatement.setString(7, b.getAcronyms());
			preparedStatement.setString(8, b.getIdentifiers());
			preparedStatement.setString(9, b.getNqf());
			preparedStatement.setString(10, b.getPqrs());
			preparedStatement.setString(11, b.getCms());
			preparedStatement.setString(12, b.getOther());
			preparedStatement.setString(13, b.getNumeratorDefinition());
			preparedStatement.setString(14, b.getDenominatorDefinition());

			// execute insert SQL statement
			preparedStatement.executeUpdate();
		}
		} catch (SQLException e) {
			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
	}


}