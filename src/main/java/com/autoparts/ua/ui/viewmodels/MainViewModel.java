package com.autoparts.ua.ui.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

import com.autoparts.ua.database.entities.Vehicle;
import com.autoparts.ua.repository.VehicleRepository;

@HiltViewModel
public class MainViewModel extends AndroidViewModel {

    private VehicleRepository vehicleRepository;
    private LiveData<List<Vehicle>> allVehicles;
    private LiveData<List<String>> allMakes;
    private MutableLiveData<String> searchQuery = new MutableLiveData<>();

    @Inject
    public MainViewModel(Application application, VehicleRepository vehicleRepository) {
        super(application);
        this.vehicleRepository = vehicleRepository;
        this.allVehicles = vehicleRepository.getAllVehicles();
        this.allMakes = vehicleRepository.getAllMakes();
    }

    public LiveData<List<Vehicle>> getAllVehicles() {
        return allVehicles;
    }

    public LiveData<List<String>> getAllMakes() {
        return allMakes;
    }

    public LiveData<List<Vehicle>> searchVehicles(String query) {
        return vehicleRepository.searchVehicles(query);
    }

    public LiveData<List<Vehicle>> getVehiclesByMake(String make) {
        return vehicleRepository.getVehiclesByMake(make);
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
    }

    public LiveData<String> getSearchQuery() {
        return searchQuery;
    }
}