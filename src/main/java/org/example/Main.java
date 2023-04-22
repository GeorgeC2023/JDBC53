package org.example;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        String dbLocation = "localhost:3306";
        String dbName = "jdbc_db";
        String dbUser = "root";
        String dbPassword = "Javagroup53";

// MysqlDataSource vine din mysql connector si o folosim ca sa configuram conexiunea la baza de date
        MysqlDataSource dataSource = new MysqlDataSource();
// Formatul pentru url-ul de conectare la baza de date
// jdbc:mysql://<<locația server-ului de baze de data>>/<<numele bazei de date>>
// jdbc:mysql://localhost:3306/jdbc_db
        dataSource.setUrl("jdbc:mysql://" + dbLocation + "/" + dbName);
        dataSource.setUser(dbUser);
        dataSource.setPassword(dbPassword);


        try {

            LOGGER.log(Level.INFO, "Trying to connect to DB");
            Connection connection = dataSource.getConnection();
            LOGGER.log(Level.INFO, "The connection succeed");
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, "Error when connection to database " + dbName
                    + " from " + dbLocation
                    + "with user " + dbUser);
            sqlException.printStackTrace();  //pt print cu object-ul sqlException
        }

    }
}