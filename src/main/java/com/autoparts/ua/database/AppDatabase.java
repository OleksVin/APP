// ===== 📁 database/AppDatabase.java (ОСТАТОЧНА ВЕРСІЯ) =====
package com.autoparts.ua.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import java.util.List;

import com.autoparts.ua.database.entities.Vehicle;
import com.autoparts.ua.database.entities.Part;
import com.autoparts.ua.database.dao.VehicleDao;
import com.autoparts.ua.database.dao.PartDao;

@Database(entities = {Vehicle.class, Part.class}, version = 2, exportSchema = false) // ⭐ Версія 2
public abstract class AppDatabase extends RoomDatabase {

    public abstract VehicleDao vehicleDao();
    public abstract PartDao partDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "autoparts_database")
                            .fallbackToDestructiveMigration() // ⭐ Видалить стару базу
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Callback для наповнення бази при першому створенні
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Наповнити базу даними у фоновому потоці
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    // AsyncTask для наповнення бази даними
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final VehicleDao vehicleDao;
        private final PartDao partDao;

        PopulateDbAsync(AppDatabase database) {
            vehicleDao = database.vehicleDao();
            partDao = database.partDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                // Наповнити автомобілями спочатку
                populateVehicles();

                // Невелика затримка
                Thread.sleep(1000);

                // Потім наповнити запчастинами
                populateParts();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        private void populateVehicles() {
            try {
                // Німецькі марки
                vehicleDao.insertVehicle(new Vehicle("BMW", "3 Series", 2005, 2012, "E90/E91/E92", "Sedan/Wagon/Coupe", "2.0i, 3.0i, 2.0d", 27));
                vehicleDao.insertVehicle(new Vehicle("BMW", "5 Series", 2003, 2010, "E60/E61", "Sedan/Wagon", "2.5i, 3.0i, 3.0d", 27));
                vehicleDao.insertVehicle(new Vehicle("BMW", "X5", 2006, 2013, "E70", "SUV", "3.0i, 4.8i, 3.0d", 27));
                vehicleDao.insertVehicle(new Vehicle("Mercedes-Benz", "C-Class", 2007, 2014, "W204", "Sedan/Wagon", "1.8, 2.5, 3.0, 2.2d", 27));
                vehicleDao.insertVehicle(new Vehicle("Mercedes-Benz", "E-Class", 2002, 2009, "W211", "Sedan/Wagon", "2.6, 3.2, 5.0, 3.2d", 27));
                vehicleDao.insertVehicle(new Vehicle("Audi", "A4", 2008, 2016, "B8", "Sedan/Wagon", "1.8T, 2.0T, 3.0, 2.0d", 27));
                vehicleDao.insertVehicle(new Vehicle("Audi", "A6", 2004, 2011, "C6", "Sedan/Wagon", "2.4, 3.2, 4.2, 3.0d", 27));
                vehicleDao.insertVehicle(new Vehicle("Volkswagen", "Passat", 2005, 2015, "B6/B7", "Sedan/Wagon", "1.8T, 2.0T, 3.6, 2.0d", 27));
                vehicleDao.insertVehicle(new Vehicle("Volkswagen", "Golf", 2008, 2013, "Mk6", "Hatchback", "1.4T, 1.6, 2.0T, 1.6d", 27));
                vehicleDao.insertVehicle(new Vehicle("Volkswagen", "Tiguan", 2007, 2016, "5N", "SUV", "1.4T, 2.0T, 2.0d", 27));

                // Японські марки
                vehicleDao.insertVehicle(new Vehicle("Toyota", "Camry", 2006, 2011, "XV40", "Sedan", "2.4, 3.5", 27));
                vehicleDao.insertVehicle(new Vehicle("Toyota", "Corolla", 2006, 2013, "E140/E150", "Sedan/Hatchback", "1.4, 1.6, 1.8", 27));
                vehicleDao.insertVehicle(new Vehicle("Toyota", "RAV4", 2005, 2012, "XA30", "SUV", "2.0, 2.4", 27));
                vehicleDao.insertVehicle(new Vehicle("Honda", "Civic", 2005, 2011, "8th Gen", "Sedan/Hatchback", "1.3, 1.8, 2.0", 27));
                vehicleDao.insertVehicle(new Vehicle("Honda", "Accord", 2008, 2012, "8th Gen", "Sedan", "2.0, 2.4, 3.5", 27));
                vehicleDao.insertVehicle(new Vehicle("Honda", "CR-V", 2006, 2012, "3rd Gen", "SUV", "2.0, 2.4", 27));
                vehicleDao.insertVehicle(new Vehicle("Mazda", "3", 2003, 2009, "BK/BL", "Hatchback/Sedan", "1.6, 2.0, 2.3", 27));
                vehicleDao.insertVehicle(new Vehicle("Mazda", "6", 2002, 2008, "GG/GY", "Sedan/Wagon", "1.8, 2.0, 2.3", 27));
                vehicleDao.insertVehicle(new Vehicle("Nissan", "X-Trail", 2007, 2014, "T31", "SUV", "2.0, 2.5, 2.0d", 27));
                vehicleDao.insertVehicle(new Vehicle("Nissan", "Qashqai", 2006, 2013, "J10", "SUV", "1.6, 2.0, 1.5d", 27));

                // Американські марки
                vehicleDao.insertVehicle(new Vehicle("Ford", "Focus", 2004, 2011, "2nd Gen", "Hatchback/Sedan", "1.4, 1.6, 1.8, 2.0", 27));
                vehicleDao.insertVehicle(new Vehicle("Ford", "Mondeo", 2007, 2014, "Mk4", "Sedan/Wagon", "1.6, 2.0, 2.5, 2.0d", 27));
                vehicleDao.insertVehicle(new Vehicle("Ford", "Kuga", 2008, 2012, "1st Gen", "SUV", "2.5T, 2.0d", 27));
                vehicleDao.insertVehicle(new Vehicle("Chevrolet", "Cruze", 2008, 2016, "J300", "Sedan/Hatchback", "1.6, 1.8, 1.4T", 27));
                vehicleDao.insertVehicle(new Vehicle("Chevrolet", "Captiva", 2006, 2017, "C100/C140", "SUV", "2.4, 3.2, 2.0d", 27));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void populateParts() {
            try {
                // Додаємо основні запчастини для кожного автомобіля (vehicle_id 1-25)
                // Тепер ID автоматично генеруються, тому використовуємо vehicle_id від 1 до 25
                for (int vehicleId = 1; vehicleId <= 25; vehicleId++) {

                    // Двигун (6 запчастин)
                    partDao.insertPart(new Part(vehicleId, "Моторна олива", "Двигун", "ENG-OIL-001",
                            "Синтетична моторна олива", "5W-30, 4-6 літрів", "10,000-15,000 км", true,
                            "https://example.com/engine_oil.jpg"));

                    partDao.insertPart(new Part(vehicleId, "Повітряний фільтр", "Двигун", "ENG-AIR-001",
                            "Фільтр повітря двигуна", "Папір/синтетика", "15,000-20,000 км", true,
                            "https://example.com/air_filter.jpg"));

                    partDao.insertPart(new Part(vehicleId, "Паливний фільтр", "Двигун", "ENG-FUEL-001",
                            "Фільтр очищення палива", "Для бензину/дизелю", "20,000-30,000 км", true,
                            "https://example.com/fuel_filter.jpg"));

                    partDao.insertPart(new Part(vehicleId, "Свічки запалювання", "Двигун", "ENG-SPARK-001",
                            "Запалювальні свічки", "Іридієві/платинові", "30,000-60,000 км", true,
                            "https://example.com/spark_plugs.jpg"));

                    partDao.insertPart(new Part(vehicleId, "Ремінь ГРМ", "Двигун", "ENG-BELT-001",
                            "Ремінь газорозподільного механізму", "Гумовий з кордом", "60,000-100,000 км", true,
                            "https://example.com/timing_belt.jpg"));

                    partDao.insertPart(new Part(vehicleId, "Антифриз", "Двигун", "ENG-COOL-001",
                            "Охолоджувальна рідина", "G12/G13, -40°C", "40,000-60,000 км", true,
                            "https://example.com/antifreeze.jpg"));

                    // Гальма (4 запчастини)
                    partDao.insertPart(new Part(vehicleId, "Гальмівні колодки передні", "Гальма", "BRK-PAD-F001",
                            "Передні гальмівні колодки", "Керамічні/напівметалічні", "30,000-50,000 км", true,
                            "https://example.com/brake_pads_front.jpg"));

                    partDao.insertPart(new Part(vehicleId, "Гальмівні колодки задні", "Гальма", "BRK-PAD-R001",
                            "Задні гальмівні колодки", "Керамічні/напівметалічні", "40,000-60,000 км", true,
                            "https://example.com/brake_pads_rear.jpg"));

                    partDao.insertPart(new Part(vehicleId, "Гальмівні диски передні", "Гальма", "BRK-DISC-F001",
                            "Передні гальмівні диски", "Вентильовані/суцільні", "60,000-80,000 км", false,
                            "https://example.com/brake_discs_front.jpg"));

                    partDao.insertPart(new Part(vehicleId, "Гальмівна рідина", "Гальма", "BRK-FLUID-001",
                            "Гідравлічна рідина DOT4", "DOT4/DOT5.1", "24 місяці", true,
                            "https://example.com/brake_fluid.jpg"));

                    // Електрика (5 запчастин)
                    partDao.insertPart(new Part(vehicleId, "Акумулятор", "Електрика", "ELC-BAT-001",
                            "Стартерна батарея", "12V 60-90Ah", "48-60 місяців", false,
                            "https://example.com/battery.jpg"));

                    partDao.insertPart(new Part(vehicleId, "Лампа H7", "Електрика", "ELC-H7-001",
                            "Галогенова лампа фар", "H7 55W 12V", "12-24 місяці", true,
                            "https://example.com/h7_bulb.jpg"));

                    partDao.insertPart(new Part(vehicleId, "Лампа H4", "Електрика", "ELC-H4-001",
                            "Галогенова лампа фар", "H4 60/55W 12V", "12-24 місяці", true,
                            "https://example.com/h4_bulb.jpg"));

                    partDao.insertPart(new Part(vehicleId, "Запобіжники набір", "Електрика", "ELC-FUSE-001",
                            "Набір автомобільних запобіжників", "5A-30A різні типи", "За потребою", true,
                            "https://example.com/fuses_set.jpg"));

                    partDao.insertPart(new Part(vehicleId, "Генератор", "Електрика", "ELC-ALT-001",
                            "Генератор змінного струму", "80-150A", "100,000-150,000 км", false,
                            "https://example.com/alternator.jpg"));

                    // Підвіска (4 запчастини)
                    partDao.insertPart(new Part(vehicleId, "Амортизатори передні", "Підвіска", "SUS-SHOCK-F001",
                            "Передні амортизатори", "Газові/олійні", "60,000-80,000 км", false,
                            "https://example.com/shock_absorbers_front.jpg"));

                    partDao.insertPart(new Part(vehicleId, "Амортизатори задні", "Підвіска", "SUS-SHOCK-R001",
                            "Задні амортизатори", "Газові/олійні", "60,000-80,000 км", false,
                            "https://example.com/shock_absorbers_rear.jpg"));

                    partDao.insertPart(new Part(vehicleId, "Пружини передні", "Підвіска", "SUS-SPR-F001",
                            "Передні спіральні пружини", "Сталеві пружини", "100,000-150,000 км", false,
                            "https://example.com/springs_front.jpg"));

                    partDao.insertPart(new Part(vehicleId, "Сайлентблоки", "Підвіска", "SUS-BUSH-001",
                            "Гумометалічні втулки", "Гума з металом", "50,000-80,000 км", false,
                            "https://example.com/bushings.jpg"));

                    // Кондиціонер (2 запчастини)
                    partDao.insertPart(new Part(vehicleId, "Фільтр салону", "Кондиціонер", "AC-CABIN-001",
                            "Фільтр очищення повітря салону", "Паперовий/вугільний", "15,000-20,000 км", true,
                            "https://example.com/cabin_filter.jpg"));

                    partDao.insertPart(new Part(vehicleId, "Фреон R134a", "Кондиціонер", "AC-FREON-001",
                            "Хладагент для кондиціонера", "R134a екологічний", "24-36 місяців", true,
                            "https://example.com/freon.jpg"));

                    // Кузов (2 запчастини)
                    partDao.insertPart(new Part(vehicleId, "Дзеркало бокове ліве", "Кузов", "BOD-MIR-L001",
                            "Ліве зовнішнє дзеркало", "З підігрівом/без", "За потребою", false,
                            "https://example.com/side_mirror_left.jpg"));

                    partDao.insertPart(new Part(vehicleId, "Дзеркало бокове праве", "Кузов", "BOD-MIR-R001",
                            "Праве зовнішнє дзеркало", "З підігрівом/без", "За потребою", false,
                            "https://example.com/side_mirror_right.jpg"));

                    // Трансмісія (2 запчастини)
                    partDao.insertPart(new Part(vehicleId, "Олива КПП", "Трансмісія", "TRN-OIL-001",
                            "Трансмісійна олива", "ATF/MTF різні типи", "40,000-80,000 км", true,
                            "https://example.com/transmission_oil.jpg"));

                    partDao.insertPart(new Part(vehicleId, "Зчеплення комплект", "Трансмісія", "TRN-CLUTCH-001",
                            "Комплект зчеплення", "Диск+кошик+підшипник", "80,000-120,000 км", false,
                            "https://example.com/clutch_kit.jpg"));

                    // Витратні матеріали (2 запчастини)
                    partDao.insertPart(new Part(vehicleId, "Омивач скла", "Витратні", "CON-WASH-001",
                            "Рідина для омивання скла", "Літня/зимова -20°C", "За потребою", true,
                            "https://example.com/windshield_washer.jpg"));

                    partDao.insertPart(new Part(vehicleId, "Щітки склоочисника", "Витратні", "CON-WIPER-001",
                            "Гумові щітки дворників", "Різні розміри 450-650мм", "12-24 місяці", true,
                            "https://example.com/wiper_blades.jpg"));

                    // Загалом 27 запчастин на автомобіль

                    // Невелика затримка між автомобілями для уникнення перевантаження
                    if (vehicleId % 5 == 0) {
                        Thread.sleep(100);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}