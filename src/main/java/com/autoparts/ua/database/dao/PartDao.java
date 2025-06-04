package com.autoparts.ua.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import com.autoparts.ua.database.entities.Part;

@Dao
public interface PartDao {

    @Query("SELECT * FROM parts WHERE vehicle_id = :vehicleId ORDER BY category, name")
    LiveData<List<Part>> getPartsByVehicleId(int vehicleId);

    @Query("SELECT * FROM parts WHERE vehicle_id = :vehicleId AND category = :category ORDER BY name")
    LiveData<List<Part>> getPartsByCategory(int vehicleId, String category);

    @Query("SELECT DISTINCT category FROM parts WHERE vehicle_id = :vehicleId ORDER BY category")
    LiveData<List<String>> getCategoriesByVehicleId(int vehicleId);

    @Query("SELECT * FROM parts WHERE id = :partId")
    LiveData<Part> getPartById(int partId);

    @Query("SELECT * FROM parts WHERE name LIKE '%' || :query || '%' OR part_number LIKE '%' || :query || '%'")
    LiveData<List<Part>> searchParts(String query);

    @Insert
    void insertPart(Part part);

    @Insert
    void insertParts(List<Part> parts);
}