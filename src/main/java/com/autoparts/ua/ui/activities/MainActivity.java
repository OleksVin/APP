// ===== 📁 ui/activities/MainActivity.java (ПОВНОЦІННА ВЕРСІЯ) =====
package com.autoparts.ua.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import dagger.hilt.android.AndroidEntryPoint;

import com.autoparts.ua.R;
import com.autoparts.ua.database.AppDatabase;
import com.autoparts.ua.database.entities.Vehicle;
import com.autoparts.ua.ui.adapters.VehicleAdapter;

import java.util.ArrayList;
import java.util.List;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private RecyclerView recyclerView;
    private VehicleAdapter adapter;
    private AdView adView;
    private AppDatabase database;
    private List<Vehicle> allVehicles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_full);

        initViews();
        setupRecyclerView();
        setupSearch();
        setupAdMob();
        loadVehicles();
    }

    private void initViews() {
        searchEditText = findViewById(R.id.searchEditText);
        recyclerView = findViewById(R.id.recyclerView);
        adView = findViewById(R.id.adView);
        database = AppDatabase.getDatabase(this);
    }

    private void setupRecyclerView() {
        adapter = new VehicleAdapter(new VehicleAdapter.OnVehicleClickListener() {
            @Override
            public void onVehicleClick(Vehicle vehicle) {
                // Відкрити екран з запчастинами
                Intent intent = new Intent(MainActivity.this, PartsActivity.class);
                intent.putExtra("vehicleId", vehicle.id);
                intent.putExtra("vehicleName", vehicle.getDisplayName());
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterVehicles(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupAdMob() {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void loadVehicles() {
        new Thread(() -> {
            try {
                // Дати час на ініціалізацію бази
                Thread.sleep(2000);

                // Завантажити всі автомобілі
                List<Vehicle> vehicles = database.vehicleDao().getAllVehiclesSync();
                allVehicles = vehicles != null ? vehicles : new ArrayList<>();

                runOnUiThread(() -> {
                    if (allVehicles.size() > 0) {
                        adapter.setVehicles(allVehicles);
                        Toast.makeText(this, "Завантажено " + allVehicles.size() + " автомобілів",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "База даних порожня. Перезапустіть додаток.",
                                Toast.LENGTH_LONG).show();
                    }
                });

            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Помилка завантаження: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
                e.printStackTrace();
            }
        }).start();
    }

    private void filterVehicles(String query) {
        if (query.isEmpty()) {
            adapter.setVehicles(allVehicles);
            return;
        }

        List<Vehicle> filteredList = new ArrayList<>();
        for (Vehicle vehicle : allVehicles) {
            if (vehicle.make.toLowerCase().contains(query.toLowerCase()) ||
                    vehicle.model.toLowerCase().contains(query.toLowerCase()) ||
                    vehicle.getDisplayName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(vehicle);
            }
        }

        adapter.setVehicles(filteredList);
    }

    @Override
    protected void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}