// ===== 📁 database/entities/Vehicle.java (ВИПРАВЛЕНА ВЕРСІЯ) =====
package com.autoparts.ua.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "vehicles")
public class Vehicle {
    @PrimaryKey(autoGenerate = true) // ⭐ ДОДАНО autoGenerate = true
    public int id;

    @ColumnInfo(name = "make")
    public String make;

    @ColumnInfo(name = "model")
    public String model;

    @ColumnInfo(name = "year_start")
    public int yearStart;

    @ColumnInfo(name = "year_end")
    public int yearEnd;

    @ColumnInfo(name = "generation")
    public String generation;

    @ColumnInfo(name = "body_type")
    public String bodyType;

    @ColumnInfo(name = "engine_type")
    public String engineType;

    @ColumnInfo(name = "parts_count")
    public int partsCount;

    // Конструктори
    public Vehicle() {}

    // Конструктор БЕЗ ID (id буде автоматично згенерований)
    public Vehicle(String make, String model, int yearStart, int yearEnd,
                   String generation, String bodyType, String engineType, int partsCount) {
        this.make = make;
        this.model = model;
        this.yearStart = yearStart;
        this.yearEnd = yearEnd;
        this.generation = generation;
        this.bodyType = bodyType;
        this.engineType = engineType;
        this.partsCount = partsCount;
    }

    // Getters and Setters
    public String getDisplayName() {
        return make + " " + model + " (" + generation + ")";
    }

    public String getYearRange() {
        return yearStart + "-" + yearEnd;
    }
}