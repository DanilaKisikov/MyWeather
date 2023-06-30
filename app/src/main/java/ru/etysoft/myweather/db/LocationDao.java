package ru.etysoft.myweather.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.etysoft.myweather.location.Location;

@Dao
public interface LocationDao {

    @Insert
    void insertAll(Location... location);

    @Delete
    void delete(Location location);

    @Update
    void update(Location location);

    @Query("SELECT * FROM location")
    List<Location> getAllLocations();

    @Query("SELECT * FROM location WHERE isCurrentLocation = 1")
    List<Location> getCurrentLocation();
}
