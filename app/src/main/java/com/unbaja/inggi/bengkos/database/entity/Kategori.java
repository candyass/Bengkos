package com.unbaja.inggi.bengkos.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by sigit on 25/06/2018.
 */

@Entity
public class Kategori {

    public static final String KATEGORI_PERAWATAN_BERKALA = "Perawatan Berkala";
    public static final String KATEGORI_BAN_DAN_VELG = "Ban Dan Velg";
    public static final String KATEGORI_SALON_CUCI = "Salon Cuci";


    @PrimaryKey
    private long idKategori;
    private String namaKategori;


    public Kategori(long idKategori, String namaKategori) {
        this.setIdKategori(idKategori);
        this.setNamaKategori(namaKategori);
    }


    public long getIdKategori() {
        return idKategori;
    }

    public void setIdKategori(long idKategori) {
        this.idKategori = idKategori;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }
}
