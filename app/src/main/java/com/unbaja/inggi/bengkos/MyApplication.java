package com.unbaja.inggi.bengkos;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.unbaja.inggi.bengkos.database.LocalDatabase;

/**
 * Created by sigit on 28/06/2018.
 */

public class MyApplication extends Application {

    private LocalDatabase localDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        localDatabase = Room.databaseBuilder(this, LocalDatabase.class, "BengkelDB").build();
    }

    public LocalDatabase getLocalDatabase() {
        return localDatabase;
    }
}
