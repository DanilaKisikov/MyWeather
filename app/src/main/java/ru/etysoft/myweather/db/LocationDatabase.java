package ru.etysoft.myweather.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.etysoft.myweather.location.Location;

@Database(entities = {Location.class /*, AnotherEntityType.class, AThirdEntityType.class */}, version = 1)
public abstract class LocationDatabase extends RoomDatabase {
    public abstract LocationDao getLocationDao();

}
