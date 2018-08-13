package com.unbaja.inggi.bengkos.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.unbaja.inggi.bengkos.database.entity.Bengkel;
import com.unbaja.inggi.bengkos.database.entity.Customer;
import com.unbaja.inggi.bengkos.database.entity.Kategori;
import com.unbaja.inggi.bengkos.database.entity.Layanan;
import com.unbaja.inggi.bengkos.model.QueryLayanan;

import java.util.List;

/**
 * Created by sigit on 26/06/2018.
 */

@Dao
public interface LayananDAO {

    @Insert
    public void insertAllCustomer(List<Customer> listCustomer);

    @Insert
    public void insertAllLayanan(List<Layanan> listLayanan);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertLayananBengkel(List<Bengkel.LayananBengkel> list);

    @Query("SELECT * FROM Layanan WHERE kategoriId = 1")
    public LiveData<List<Layanan>> getAllLayananPerawatanBerkala();

    @Query("SELECT * FROM Layanan WHERE kategoriId = 2")
    public LiveData<List<Layanan>> getAllLayananBanDanVelg();

    @Query("SELECT * FROM Layanan WHERE kategoriId = 3")
    public LiveData<List<Layanan>> getAllLayananSalonCuci();


    @Query("SELECT idLayanan, namaLayanan, harga FROM Layanan JOIN LayananBengkel " +
            "ON Layanan.idLayanan = LayananBengkel.layananId " +
            "WHERE LayananBengkel.bengkelId =:idBengkel AND Layanan.kategoriId = 1")
    public LiveData<List<QueryLayanan>> getAllLayananPerawatanBerkala(long idBengkel);

    @Query("SELECT idLayanan, namaLayanan, harga FROM Layanan JOIN LayananBengkel " +
            "ON Layanan.idLayanan = LayananBengkel.layananId " +
            "WHERE LayananBengkel.bengkelId =:idBengkel AND Layanan.kategoriId = 2")
    public LiveData<List<QueryLayanan>> getAllLayananBanDanVelg(long idBengkel);

    @Query("SELECT idLayanan, namaLayanan, harga FROM Layanan JOIN LayananBengkel " +
            "ON Layanan.idLayanan = LayananBengkel.layananId " +
            "WHERE LayananBengkel.bengkelId =:idBengkel AND Layanan.kategoriId = 3")
    public LiveData<List<QueryLayanan>> getAllLayananCuci(long idBengkel);
}
