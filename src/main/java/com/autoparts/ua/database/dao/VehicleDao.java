package com.autoparts.ua.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import com.autoparts.ua.database.entities.Vehicle;

@Dao
public interface VehicleDao {

    @Query("SELECT * FROM vehicles ORDER BY make, model")
    LiveData<List<Vehicle>> getAllVehicles();

    @Query("SELECT DISTINCT make FROM vehicles ORDER BY make")
    LiveData<List<String>> getAllMakes();

    @Query("SELECT * FROM vehicles WHERE make = :make ORDER BY model")
    LiveData<List<Vehicle>> getVehiclesByMake(String make);

    @Query("SELECT * FROM vehicles WHERE make LIKE '%' || :query || '%' OR model LIKE '%' || :query || '%'")
    LiveData<List<Vehicle>> searchVehicles(String query);

    @Query("SELECT * FROM vehicles WHERE id = :vehicleId")
    LiveData<Vehicle> getVehicleById(int vehicleId);

    @Insert
    void insertVehicle(Vehicle vehicle);

    @Insert
    void insertVehicles(List<Vehicle> vehicles);

    List<Vehicle> getAllVehiclesSync();
}