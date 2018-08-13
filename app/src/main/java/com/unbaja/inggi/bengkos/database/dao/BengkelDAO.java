package com.unbaja.inggi.bengkos.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.unbaja.inggi.bengkos.database.entity.Bengkel;
import com.unbaja.inggi.bengkos.database.entity.Customer;

import java.util.List;

/**
 * Created by sigit on 26/06/2018.
 */
@Dao
public interface BengkelDAO {

    @Insert
    long insertBengkel(Bengkel bengkel);

    @Insert
    public void insertAllCustomer(List<Customer> listCustomer);

    @Query("SELECT * FROM Bengkel")
    public LiveData<List<Bengkel>> getAllBengkel();


    @Query("SELECT * FROM Bengkel WHERE id =:idBengkel")
    public LiveData<Bengkel> getBengkel(long idBengkel);

    @Query("SELECT id FROM Bengkel WHERE email =:email AND password =:katasandi")
    public long getValidBengkelId(String email, String katasandi);

    @Query("SELECT * FROM Bengkel WHERE customerId =:melayani")
    public LiveData<List<Bengkel>> getAllBengkel(long melayani);

    @Query("UPDATE Bengkel SET hariBuka = :hBuka, hariTutup =:hTutup WHERE id =:idBengkel")
    public void updateHariBukaDanTutup(long idBengkel, String hBuka, String hTutup);

    @Query("UPDATE Bengkel SET jamBuka =:jBuka, jamTutup =:jTutup WHERE id =:idBengkel")
    public void updateJamBukaDanTutup(long idBengkel, String jBuka, String jTutup);
}
