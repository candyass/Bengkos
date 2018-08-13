package com.unbaja.inggi.bengkos.database.entity;

/**
 * Created by sigit on 25/06/2018.
 */

public class Lokasi {

    private String namaLokasi;
    private double latitude;
    private double longtitude;

    public Lokasi(String namaLokasi, double latitude, double longtitude) {
        this.setNamaLokasi(namaLokasi);
        this.setLatitude(latitude);
        this.setLongtitude(longtitude);
    }


    public String getNamaLokasi() {
        return namaLokasi;
    }

    public void setNamaLokasi(String namaLokasi) {
        this.namaLokasi = namaLokasi;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }
}
