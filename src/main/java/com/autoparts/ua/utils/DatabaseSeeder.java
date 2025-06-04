package com.autoparts.ua.utils;

import android.content.Context;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.autoparts.ua.database.AppDatabase;
import com.autoparts.ua.database.entities.Vehicle;

public class DatabaseSeeder {

    public static void seedDatabase(Context context) {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        executor.execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(context);

            // Перевіряємо чи є дані в базі
            if (db.vehicleDao().getAllVehicles().getValue() == null) {
                // Додаємо тестові автомобілі
                Vehicle[] vehicles = {
                        new Vehicle("Toyota", "Camry", 2015, 2023, "XV70", "Седан", "2.5L", 150),
                        new Vehicle("Honda", "Civic", 2016, 2024, "10th Gen", "Седан", "1.5L Turbo", 120),
                        new Vehicle("BMW", "X5", 2019, 2024, "G05", "SUV", "3.0L", 200),
                        new Vehicle("Mercedes-Benz", "C-Class", 2018, 2024, "W205", "Седан", "2.0L Turbo", 180),
                        new Vehicle("Audi", "A4", 2017, 2023, "B9", "Седан", "2.0L TFSI", 160)
                };

                for (Vehicle vehicle : vehicles) {
                    db.vehicleDao().insertVehicle(vehicle);
                }
            }
        });
    }
}