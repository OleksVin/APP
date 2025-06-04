// ===== 📁 database/DatabaseHelper.java =====
package com.autoparts.ua.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "autoparts.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Створення таблиць
        createTables(db);
        // Наповнення даними
        insertInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS parts");
        db.execSQL("DROP TABLE IF EXISTS vehicles");
        onCreate(db);
    }

    private void createTables(SQLiteDatabase db) {
        // Створення таблиці vehicles
        db.execSQL("CREATE TABLE vehicles (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "make TEXT NOT NULL," +
                "model TEXT NOT NULL," +
                "year_start INTEGER NOT NULL," +
                "year_end INTEGER NOT NULL," +
                "generation TEXT NOT NULL," +
                "body_type TEXT NOT NULL," +
                "engine_type TEXT NOT NULL," +
                "parts_count INTEGER NOT NULL DEFAULT 0" +
                ")");

        // Створення таблиці parts
        db.execSQL("CREATE TABLE parts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "vehicle_id INTEGER NOT NULL," +
                "name TEXT NOT NULL," +
                "category TEXT NOT NULL," +
                "part_number TEXT," +
                "description TEXT," +
                "specifications TEXT," +
                "replacement_interval TEXT," +
                "is_consumable INTEGER NOT NULL DEFAULT 0," +
                "image_url TEXT," +
                "FOREIGN KEY (vehicle_id) REFERENCES vehicles (id) ON DELETE CASCADE" +
                ")");

        // Створення індексів
        db.execSQL("CREATE INDEX idx_vehicles_make ON vehicles(make)");
        db.execSQL("CREATE INDEX idx_vehicles_model ON vehicles(model)");
        db.execSQL("CREATE INDEX idx_vehicles_make_model ON vehicles(make, model)");
        db.execSQL("CREATE INDEX idx_parts_vehicle_id ON parts(vehicle_id)");
        db.execSQL("CREATE INDEX idx_parts_category ON parts(category)");
        db.execSQL("CREATE INDEX idx_parts_vehicle_category ON parts(vehicle_id, category)");
    }

    private void insertInitialData(SQLiteDatabase db) {
        // Вставка автомобілів
        insertVehicles(db);
        // Вставка запчастин
        insertParts(db);
        // Оновлення лічильників
        updatePartsCounts(db);
    }

    private void insertVehicles(SQLiteDatabase db) {
        // Німецькі марки
        db.execSQL("INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES " +
                "('BMW', '3 Series', 2005, 2012, 'E90/E91/E92', 'Sedan/Wagon/Coupe', '2.0i, 3.0i, 2.0d', 142)");

        db.execSQL("INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES " +
                "('BMW', '5 Series', 2003, 2010, 'E60/E61', 'Sedan/Wagon', '2.5i, 3.0i, 3.0d', 156)");

        db.execSQL("INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES " +
                "('BMW', 'X5', 2006, 2013, 'E70', 'SUV', '3.0i, 4.8i, 3.0d', 134)");

        db.execSQL("INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES " +
                "('Mercedes-Benz', 'C-Class', 2007, 2014, 'W204', 'Sedan/Wagon', '1.8, 2.5, 3.0, 2.2d', 148)");

        db.execSQL("INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES " +
                "('Mercedes-Benz', 'E-Class', 2002, 2009, 'W211', 'Sedan/Wagon', '2.6, 3.2, 5.0, 3.2d', 162)");

        db.execSQL("INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES " +
                "('Audi', 'A4', 2008, 2016, 'B8', 'Sedan/Wagon', '1.8T, 2.0T, 3.0, 2.0d', 145)");

        db.execSQL("INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES " +
                "('Audi', 'A6', 2004, 2011, 'C6', 'Sedan/Wagon', '2.4, 3.2, 4.2, 3.0d', 158)");

        db.execSQL("INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES " +
                "('Volkswagen', 'Passat', 2005, 2015, 'B6/B7', 'Sedan/Wagon', '1.8T, 2.0T, 3.6, 2.0d', 128)");

        db.execSQL("INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES " +
                "('Volkswagen', 'Golf', 2008, 2013, 'Mk6', 'Hatchback', '1.4T, 1.6, 2.0T, 1.6d', 115)");

        db.execSQL("INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES " +
                "('Volkswagen', 'Tiguan', 2007, 2016, '5N', 'SUV', '1.4T, 2.0T, 2.0d', 108)");

        // Японські марки
        db.execSQL("INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES " +
                "('Toyota', 'Camry', 2006, 2011, 'XV40', 'Sedan', '2.4, 3.5', 95)");

        db.execSQL("INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES " +
                "('Toyota', 'Corolla', 2006, 2013, 'E140/E150', 'Sedan/Hatchback', '1.4, 1.6, 1.8', 87)");

        db.execSQL("INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES " +
                "('Toyota', 'RAV4', 2005, 2012, 'XA30', 'SUV', '2.0, 2.4', 89)");

        db.execSQL("INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES " +
                "('Honda', 'Civic', 2005, 2011, '8th Gen', 'Sedan/Hatchback', '1.3, 1.8, 2.0', 92)");

        db.execSQL("INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES " +
                "('Honda', 'Accord', 2008, 2012, '8th Gen', 'Sedan', '2.0, 2.4, 3.5', 96)");

        // Додайте решту автомобілів за аналогією...
        // Для економії місця показую лише перші 15
    }

    private void insertParts(SQLiteDatabase db) {
        // Додаємо основні запчастини для кожного автомобіля

        // Двигун - Моторна олива
        for (int vehicleId = 1; vehicleId <= 15; vehicleId++) {
            db.execSQL("INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url) VALUES " +
                    "(" + vehicleId + ", 'Моторна олива', 'Двигун', 'ENG-OIL-001', 'Синтетична моторна олива', '5W-30, 4-6 літрів', '10,000-15,000 км', 1, 'https://example.com/engine_oil.jpg')");
        }

        // Двигун - Повітряний фільтр
        for (int vehicleId = 1; vehicleId <= 15; vehicleId++) {
            db.execSQL("INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url) VALUES " +
                    "(" + vehicleId + ", 'Повітряний фільтр', 'Двигун', 'ENG-AIR-001', 'Фільтр повітря двигуна', 'Папір/синтетика', '15,000-20,000 км', 1, 'https://example.com/air_filter.jpg')");
        }

        // Двигун - Паливний фільтр
        for (int vehicleId = 1; vehicleId <= 15; vehicleId++) {
            db.execSQL("INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url) VALUES " +
                    "(" + vehicleId + ", 'Паливний фільтр', 'Двигун', 'ENG-FUEL-001', 'Фільтр очищення палива', 'Для бензину/дизелю', '20,000-30,000 км', 1, 'https://example.com/fuel_filter.jpg')");
        }

        // Двигун - Свічки запалювання
        for (int vehicleId = 1; vehicleId <= 15; vehicleId++) {
            db.execSQL("INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url) VALUES " +
                    "(" + vehicleId + ", 'Свічки запалювання', 'Двигун', 'ENG-SPARK-001', 'Запалювальні свічки', 'Іридієві/платинові', '30,000-60,000 км', 1, 'https://example.com/spark_plugs.jpg')");
        }

        // Гальма - Передні колодки
        for (int vehicleId = 1; vehicleId <= 15; vehicleId++) {
            db.execSQL("INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url) VALUES " +
                    "(" + vehicleId + ", 'Гальмівні колодки передні', 'Гальма', 'BRK-PAD-F001', 'Передні гальмівні колодки', 'Керамічні/напівметалічні', '30,000-50,000 км', 1, 'https://example.com/brake_pads_front.jpg')");
        }

        // Гальма - Задні колодки
        for (int vehicleId = 1; vehicleId <= 15; vehicleId++) {
            db.execSQL("INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url) VALUES " +
                    "(" + vehicleId + ", 'Гальмівні колодки задні', 'Гальма', 'BRK-PAD-R001', 'Задні гальмівні колодки', 'Керамічні/напівметалічні', '40,000-60,000 км', 1, 'https://example.com/brake_pads_rear.jpg')");
        }

        // Гальма - Гальмівна рідина
        for (int vehicleId = 1; vehicleId <= 15; vehicleId++) {
            db.execSQL("INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url) VALUES " +
                    "(" + vehicleId + ", 'Гальмівна рідина', 'Гальма', 'BRK-FLUID-001', 'Гідравлічна рідина DOT4', 'DOT4/DOT5.1', '24 місяці', 1, 'https://example.com/brake_fluid.jpg')");
        }

        // Електрика - Акумулятор
        for (int vehicleId = 1; vehicleId <= 15; vehicleId++) {
            db.execSQL("INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url) VALUES " +
                    "(" + vehicleId + ", 'Акумулятор', 'Електрика', 'ELC-BAT-001', 'Стартерна батарея', '12V 60-90Ah', '48-60 місяців', 0, 'https://example.com/battery.jpg')");
        }

        // Електрика - Лампа H7
        for (int vehicleId = 1; vehicleId <= 15; vehicleId++) {
            db.execSQL("INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url) VALUES " +
                    "(" + vehicleId + ", 'Лампа H7', 'Електрика', 'ELC-H7-001', 'Галогенова лампа фар', 'H7 55W 12V', '12-24 місяці', 1, 'https://example.com/h7_bulb.jpg')");
        }

        // Електрика - Запобіжники
        for (int vehicleId = 1; vehicleId <= 15; vehicleId++) {
            db.execSQL("INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url) VALUES " +
                    "(" + vehicleId + ", 'Запобіжники набір', 'Електрика', 'ELC-FUSE-001', 'Набір автомобільних запобіжників', '5A-30A різні типи', 'За потребою', 1, 'https://example.com/fuses_set.jpg')");
        }

        // Додайте інші категорії за потребою...
    }

    private void updatePartsCounts(SQLiteDatabase db) {
        db.execSQL("UPDATE vehicles SET parts_count = (" +
                "SELECT COUNT(*) FROM parts WHERE parts.vehicle_id = vehicles.id" +
                ")");
    }
}