package com.unbaja.inggi.bengkos.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.unbaja.inggi.bengkos.database.dao.BengkelDAO;
import com.unbaja.inggi.bengkos.database.dao.KategoriDAO;
import com.unbaja.inggi.bengkos.database.dao.LayananDAO;
import com.unbaja.inggi.bengkos.database.entity.Bengkel;
import com.unbaja.inggi.bengkos.database.entity.BitmapConverter;
import com.unbaja.inggi.bengkos.database.entity.Customer;
import com.unbaja.inggi.bengkos.database.entity.Kategori;
import com.unbaja.inggi.bengkos.database.entity.Layanan;

/**
 * Created by sigit on 25/06/2018.
 */
@Database(entities = {Bengkel.class, Customer.class, Kategori.class,
        Layanan.class,  Bengkel.LayananBengkel.class}, version = 1, exportSchema = false)
@TypeConverters(BitmapConverter.class)
public abstract class LocalDatabase extends RoomDatabase {

    public abstract BengkelDAO getBengkelDAO();
    public abstract LayananDAO getLsyananDAO();
    public abstract KategoriDAO getKategoriDAO();
}
