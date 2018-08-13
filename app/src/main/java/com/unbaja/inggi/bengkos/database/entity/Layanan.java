package com.unbaja.inggi.bengkos.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by sigit on 25/06/2018.
 */
@Entity(foreignKeys = @ForeignKey(entity = Kategori.class, childColumns = "kategoriId", parentColumns = "idKategori"))
public class Layanan {

    @PrimaryKey(autoGenerate = true)
    private long idLayanan;
    private long kategoriId;
    private String namaLayanan;

    @Ignore
    public Layanan(long kategoriId, String namaLayanan) {
        this.setKategoriId(kategoriId);
        this.setNamaLayanan(namaLayanan);
    }

    public Layanan(long idLayanan, long kategoriId, String namaLayanan) {
        this.setIdLayanan(idLayanan);
        this.setKategoriId(kategoriId);
        this.setNamaLayanan(namaLayanan);
    }


    public long getIdLayanan() {
        return idLayanan;
    }

    public void setIdLayanan(long idLayanan) {
        this.idLayanan = idLayanan;
    }

    public long getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(long kategoriId) {
        this.kategoriId = kategoriId;
    }

    public String getNamaLayanan() {
        return namaLayanan;
    }

    public void setNamaLayanan(String namaLayanan) {
        this.namaLayanan = namaLayanan;
    }
}
