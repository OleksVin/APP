// ===== 📁 database/entities/Part.java (ВИПРАВЛЕНА ВЕРСІЯ) =====
package com.autoparts.ua.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.ForeignKey;

@Entity(tableName = "parts",
        foreignKeys = @ForeignKey(entity = Vehicle.class,
                parentColumns = "id",
                childColumns = "vehicle_id",
                onDelete = ForeignKey.CASCADE))
public class Part {
    @PrimaryKey(autoGenerate = true) // ⭐ ДОДАНО autoGenerate = true
    public int id;

    @ColumnInfo(name = "vehicle_id")
    public int vehicleId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "part_number")
    public String partNumber;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "specifications")
    public String specifications;

    @ColumnInfo(name = "replacement_interval")
    public String replacementInterval;

    @ColumnInfo(name = "is_consumable")
    public boolean isConsumable;

    @ColumnInfo(name = "image_url")
    public String imageUrl;

    // Конструктори
    public Part() {}

    // Конструктор БЕЗ ID (id буде автоматично згенерований)
    public Part(int vehicleId, String name, String category, String partNumber,
                String description, String specifications, String replacementInterval,
                boolean isConsumable, String imageUrl) {
        this.vehicleId = vehicleId;
        this.name = name;
        this.category = category;
        this.partNumber = partNumber;
        this.description = description;
        this.specifications = specifications;
        this.replacementInterval = replacementInterval;
        this.isConsumable = isConsumable;
        this.imageUrl = imageUrl;
    }
}