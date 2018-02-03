package io.database.postgresql;

/**
 * Created by Tapan Banker on 2/2/18.
 * @Author Tapan Banker
 * Interface to store Database credentials
 */
public interface DbContract {
    public static final String HOST = "jdbc:postgresql://localhost:5432/";
    public static final String DB_NAME = "measuresdb";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "root";
}