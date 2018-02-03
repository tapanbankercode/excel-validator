package io.database.postgresql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.Instant;

/**
 * Utility to Connect to PostGre Database
 * @Author Tapan N. Banker
 * This Class Provides Connection to the Database Postgres SQL
 */
public class PostgresDbUtility {

	private static Connection connectionDB;

    private static Logger LOGGER = (Logger) LoggerFactory.getLogger(PostgresDbUtility.class);


    static {
        try {
            // Obtain the Database Driver for PostgreSQL
            Class.forName("org.postgresql.Driver");
            // Get the Connection
            connectionDB = DriverManager.getConnection(
                    DatabaseCredentials.HOST + DatabaseCredentials.DB_NAME,
                    DatabaseCredentials.USERNAME, DatabaseCredentials.PASSWORD);
            LOGGER.info(" Database Connection Obtained at "+ Instant.now());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * This method will return the Database Connection for Postgres Database (Singelton Instance)
     * @return Connection Object
     */
    public static Connection getConnection() {
        return connectionDB;
	}

}