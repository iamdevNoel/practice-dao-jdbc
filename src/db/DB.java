package src.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties properties = loadProperties();
                String url = properties.getProperty("dburl");
                connection = DriverManager.getConnection(url, properties);
            }
            catch (SQLException exception) {
                throw new DbException(exception.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                throw new DbException(exception.getMessage());
            }
        }
    }

    private static Properties loadProperties() {
        try (FileInputStream fileSource = new FileInputStream("jdbc/db.properties")) {
            Properties properties = new Properties();
            properties.load(fileSource);
            return properties;
        } catch(IOException exception) {
            throw new DbException(exception.getMessage());
        }
    }

}
