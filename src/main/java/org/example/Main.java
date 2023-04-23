package org.example;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.example.dao.AnimalDao;
import org.example.dao.AnimalDaoImpl;
import org.example.dao.FoodDao;
import org.example.dao.FoodDaoImpl;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private final
    static Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        String dbLocation = "localhost:3306";
        String dbName = "jdbc_db";
        String dbUser = "root";
        String dbPassword = "Javagroup53";

// !!!! -MysqlDataSource vine din mysql connector si o folosim ca sa configuram conexiunea la baza de date !!!!

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

            AnimalDao animalDao = new AnimalDaoImpl(connection);
            FoodDao foodDao = new FoodDaoImpl(connection);

            // statement  - folosit pt comenzi de tranfer sql la baza de date
            Statement statement = connection.createStatement();

            animalDao.createTable();
            foodDao.createTable();

           /* statement.execute("Create table if not exists food(id integer auto_increment, " +
                    "name varchar(100)," +
                    "description varchar(100)," +
                    "calories_per_100 integer," +
                    "expiration_date date," +
                    "primary key (id))");*/

            // putem sa refolosim obiectul statement pentru a trimite alte instructiuni sql catre baza de date
           // statement.execute("Create table if not exists animals ( id integer not null auto_increment, name varchar(100), species varchar(100), primary key(id))");

            LOGGER.info(("Tables create successful"));

            // we can reuse statement object
            statement.execute("insert into animals (name, species) values (\"Lucky\", \"Dog\")");
            statement.execute("insert into animals (name, species) values (\"Lucky\", \"Dog\")");
            LOGGER.info(("Data insertion was successful"));

            statement.execute("Update Animals Set Name = \"Bubu\" where Id =2");



            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into food (name, description, calories_per_100, expiration_date) values (?, ?, ?, ?)");
            preparedStatement.setString(1, "ciocolată");
            preparedStatement.setString(2, "ciocolată de casă");
            preparedStatement.setInt(3, 550);
            Date expirationDate = Date.valueOf("2024-10-12");
            preparedStatement.setDate(4, expirationDate);
            // intotdeauna trebuie rulat.execute() daca vrem sa fie executat codul sql pe baza de date
            // comanda care trimite instructiunile sql catre server ( instructiunile pregatite mai sus)
            preparedStatement.execute();

            preparedStatement.setString(1, "Alune");
            preparedStatement.setString(2, "Punga de 500g alune prajite");
            preparedStatement.setInt(3, 600);
            preparedStatement.setDate(4, expirationDate);
            preparedStatement.execute();

           /* ResultSet rs = statement.executeQuery("SELECT * FROM animals");
            while(rs.next()== true){
                System.out.println("Id: "+ rs.getInt(1));
                System.out.println("Name:" + rs.getString(2));
                System.out.println("Species: "+ rs.getString(3));
            }*/

            ResultSet rst = statement.executeQuery("SELECT * FROM food order by calories_per_100 desc");
            while (rst.next() == true) {
                System.out.println("Id: " + rst.getInt(1));
                System.out.println("Name: " + rst.getString(2));
                System.out.println("Description:" + rst.getString(3));
                System.out.println("Calories_per_100: " + rst.getInt(4));
                System.out.println("Expiration_date: " + rst.getDate(5));

            }

            //display all foods :D
            // Food: 1.ciocolata - ciocolata de casa - 550kcal per 100g - expira la 2024-10-12
            // 2.alune - punga de 500g de alune prajite - 600kcal per 100g - expira la 2024-10-12

           /* rs.next();
            System.out.println(rs.getInt(1));
            System.out.println(rs.getString(2));
            System.out.println(rs.getString(3));

            rs.next();
            System.out.println(rs.getInt(1));
            System.out.println(rs.getString(2));
            System.out.println(rs.getString(3));
            System.out.println(rs.next());*/


            statement.execute("drop table animals");
            animalDao.dropTable();
            statement.execute("drop table food");
            foodDao.dropTable();



        } catch (SQLException sqlException) {
            //LOGGER.log(Level.SEVERE, "Error when connection to database " + dbName
            //     + " from " + dbLocation
            //    + "with user " + dbUser);

            LOGGER.log(Level.SEVERE, sqlException.getMessage()); //pt print cu object-ul sqlException
            sqlException.printStackTrace();
        }

    }
}