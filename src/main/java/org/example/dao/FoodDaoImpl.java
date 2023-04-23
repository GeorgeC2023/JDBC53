package org.example.dao;

import org.example.model.Animal;
import org.example.model.Food;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FoodDaoImpl implements FoodDao{

    private final Connection connection;

    public FoodDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("Create table if not exists food(id integer auto_increment, " +
                "name varchar(100)," +
                "description varchar(100)," +
                "calories_per_100 integer," +
                "expiration_date date," +
                "primary key (id))");
    }

    @Override
    public List<Food> read() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(" SELECT * FROM food ");

        List<Food> foods = new ArrayList<>();
        while(rs.next()==true){

            Food food = new Food();

            food.setId(rs.getInt(1));
            food.setName(rs.getString(2));
            food.setDescription(rs.getString(3));
            food.setCaloriesPer100(rs.getInt(4));
            food.setExpirationDate(rs.getDate(5));

            foods.add(food);
        }
        return foods;

    }

    @Override
    public void dropTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("drop table food");
    }

}
