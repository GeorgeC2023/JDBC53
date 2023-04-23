package org.example.dao;
// Animal Data Access Object - clasa pt a accesa date din "animals"
// manipulare structura baza de date (creare si stergere tabel)
// manipulare date CRUD

import org.example.model.Animal;

import java.sql.SQLDataException;
import java.sql.SQLException;

public interface AnimalDao {

    // creat tabel
    void createTable() throws SQLException;
    void create(Animal animal) throws SQLException;


    //adaugare date  operatiune numita CREATE animals
    //gasire date                       READ animals
    //modificare date                   UPDATE animals
    //stergere date                     DELETE animals

    // sters tabel
    void dropTable() throws SQLException;
}
