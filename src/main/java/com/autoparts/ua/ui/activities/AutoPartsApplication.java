package com.autoparts.ua;

import android.app.Application;
import com.google.android.gms.ads.MobileAds;
import dagger.hilt.android.HiltAndroidApp;
import com.autoparts.ua.utils.DatabaseSeeder;

@HiltAndroidApp
public class AutoPartsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Ініціалізація AdMob
        MobileAds.initialize(this, initializationStatus -> {
            // Callback для успішної ініціалізації
        });

        // Ініціалізація тестових даних
        DatabaseSeeder.seedDatabase(this);
    }
}