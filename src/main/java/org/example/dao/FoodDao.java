package org.example.dao;

import java.sql.SQLException;

public interface FoodDao {

    void createTable() throws SQLException;
    void dropTable() throws SQLException;
}
