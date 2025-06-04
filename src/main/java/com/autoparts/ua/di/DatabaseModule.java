package com.autoparts.ua.di;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

import com.autoparts.ua.database.AppDatabase;
import com.autoparts.ua.database.dao.VehicleDao;
import com.autoparts.ua.database.dao.PartDao;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public AppDatabase provideAppDatabase(@ApplicationContext Context context) {
        return AppDatabase.getDatabase(context);
    }

    @Provides
    public VehicleDao provideVehicleDao(AppDatabase database) {
        return database.vehicleDao();
    }

    @Provides
    public PartDao providePartDao(AppDatabase database) {
        return database.partDao();
    }
}