package com.alexanr.demin.weatherapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CitiesDao {

    @Query("SELECT * FROM city")
    List<City> getAll();

    @Query("SELECT * FROM city WHERE id = :id")
    City getById(long id);

    @Query("SELECT * FROM city WHERE name = :name")
    City getByName(String name);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(City city);

    @Update
    void update(City city);

    @Delete
    void delete(City city);
}
