package io.database.postgresql;

/**
 * Created by Tapan Banker on 2/2/18.
 * @Author Tapan Banker
 * Interface to store Database credentials
 */
public interface DatabaseCredentials {
    // Database Host URL
    public static final String HOST = "jdbc:postgresql://localhost:5432/";
    // Database Name
    public static final String DB_NAME = "DB_NAME";
    // Database username
    public static final String USERNAME = "USERNAME";
    // Database password
    public static final String PASSWORD = "PASSWORD";
}