package com.unbaja.inggi.bengkos.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.unbaja.inggi.bengkos.database.entity.Kategori;

import java.util.List;

/**
 * Created by sigit on 28/06/2018.
 */

@Dao
public interface KategoriDAO {

    @Insert
    public void insertKategori(List<Kategori> listKategori);

    @Query("SELECT * FROM Kategori")
    public LiveData<List<Kategori>> getAllKategori();
}
