package com.autoparts.ua.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.autoparts.ua.database.AppDatabase;
import com.autoparts.ua.database.dao.VehicleDao;
import com.autoparts.ua.database.entities.Vehicle;

@Singleton
public class VehicleRepository {

    private VehicleDao vehicleDao;
    private LiveData<List<Vehicle>> allVehicles;
    private ExecutorService executor;

    @Inject
    public VehicleRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        vehicleDao = database.vehicleDao();
        allVehicles = vehicleDao.getAllVehicles();
        executor = Executors.newFixedThreadPool(4);
    }

    public LiveData<List<Vehicle>> getAllVehicles() {
        return allVehicles;
    }

    public LiveData<List<String>> getAllMakes() {
        return vehicleDao.getAllMakes();
    }

    public LiveData<List<Vehicle>> getVehiclesByMake(String make) {
        return vehicleDao.getVehiclesByMake(make);
    }

    public LiveData<List<Vehicle>> searchVehicles(String query) {
        return vehicleDao.searchVehicles(query);
    }

    public LiveData<Vehicle> getVehicleById(int vehicleId) {
        return vehicleDao.getVehicleById(vehicleId);
    }

    public void insertVehicle(Vehicle vehicle) {
        executor.execute(() -> vehicleDao.insertVehicle(vehicle));
    }
}