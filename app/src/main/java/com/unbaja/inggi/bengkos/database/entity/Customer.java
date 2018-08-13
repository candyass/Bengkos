package com.unbaja.inggi.bengkos.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by sigit on 25/06/2018.
 */

@Entity
public class Customer {

    public static final String MOBIL_CS = "Mobil";
    public static final String MOTOR_CS = "Motor";
    public static final String SEMUA_CS = "Mobil Dan Motor";

    @PrimaryKey
    private long idCustomer;
    private String namaCustomer;


    public Customer(long idCustomer, String namaCustomer) {
        this.setIdCustomer(idCustomer);
        this.setNamaCustomer(namaCustomer);
    }


    public long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getNamaCustomer() {
        return namaCustomer;
    }

    public void setNamaCustomer(String namaCustomer) {
        this.namaCustomer = namaCustomer;
    }
}
