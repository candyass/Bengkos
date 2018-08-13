package com.unbaja.inggi.bengkos.model;

import android.app.DownloadManager;

/**
 * Created by sigit on 02/07/2018.
 */

public class QueryLayanan {

    private long idLayanan;
    private String namaLayanan;
    private int harga;

    public QueryLayanan(long idLayanan, String namaLayanan, int harga) {
        this.setIdLayanan(idLayanan);
        this.setNamaLayanan(namaLayanan);
        this.setHarga(harga);
    }




    public String getNamaLayanan() {
        return namaLayanan;
    }

    public void setNamaLayanan(String namaLayanan) {
        this.namaLayanan = namaLayanan;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public long getIdLayanan() {
        return idLayanan;
    }

    public void setIdLayanan(long idLayanan) {
        this.idLayanan = idLayanan;
    }
}
