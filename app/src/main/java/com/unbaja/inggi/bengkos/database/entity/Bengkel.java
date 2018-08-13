package com.unbaja.inggi.bengkos.database.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

/**
 * Created by sigit on 25/06/2018.
 */
@Entity(foreignKeys = @ForeignKey(entity = Customer.class, parentColumns = "idCustomer", childColumns = "customerId"))
public class Bengkel {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long customerId;
    @Embedded
    private Lokasi lokasi;
    private String namaBengkel;
    private String email;
    private String password;
    private String noTelepon;
    private String hariBuka;
    private String hariTutup;
    private String jamBuka;
    private String jamTutup;
    private Bitmap foto;
    private boolean panggilanPerbaikan;

    @Ignore
    public Bengkel(String email, String password, String namaBengkel, String noTelepon) {
        this.setEmail(email);
        this.setPassword(password);
        this.setNamaBengkel(namaBengkel);
        this.setNoTelepon(noTelepon);
    }

    @Ignore
    public Bengkel (Lokasi lokasi, Bitmap foto, String hariBuka,
                    String hariTutup, String jamBuka, String jamTutup) {
        this.setLokasi(lokasi);
        this.setFoto(foto);
        this.setHariBuka(hariBuka);
        this.setHariTutup(hariTutup);
        this.setJamBuka(jamBuka);
        this.setJamTutup(jamTutup);
    }

    public Bengkel(long id, long customerId, Lokasi lokasi, String namaBengkel, String email, String password,
                   String noTelepon, String hariBuka, String hariTutup,
                   String jamBuka, String jamTutup, Bitmap foto, boolean panggilanPerbaikan) {
        this.setId(id);
        this.setCustomerId(customerId);
        this.setLokasi(lokasi);
        this.setNamaBengkel(namaBengkel);
        this.setEmail(email);
        this.setPassword(password);
        this.setNoTelepon(noTelepon);
        this.setHariBuka(hariBuka);
        this.setHariTutup(hariTutup);
        this.setJamBuka(jamBuka);
        this.setJamTutup(jamTutup);
        this.setFoto(foto);
        this.setPanggilanPerbaikan(panggilanPerbaikan);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public Lokasi getLokasi() {
        return lokasi;
    }

    public void setLokasi(Lokasi lokasi) {
        this.lokasi = lokasi;
    }

    public String getNamaBengkel() {
        return namaBengkel;
    }

    public void setNamaBengkel(String namaBengkel) {
        this.namaBengkel = namaBengkel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getHariBuka() {
        return hariBuka;
    }

    public void setHariBuka(String hariBuka) {
        this.hariBuka = hariBuka;
    }

    public String getHariTutup() {
        return hariTutup;
    }

    public void setHariTutup(String hariTutup) {
        this.hariTutup = hariTutup;
    }

    public String getJamBuka() {
        return jamBuka;
    }

    public void setJamBuka(String jamBuka) {
        this.jamBuka = jamBuka;
    }

    public String getJamTutup() {
        return jamTutup;
    }

    public void setJamTutup(String jamTutup) {
        this.jamTutup = jamTutup;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public void setPanggilanPerbaikan(boolean panggilanPerbaikan) {
        this.panggilanPerbaikan = panggilanPerbaikan;
    }

    public boolean isPanggilanPerbaikan() {
        return panggilanPerbaikan;
    }




    @Entity(foreignKeys ={
                          @ForeignKey(entity = Bengkel.class, parentColumns = "id", childColumns = "bengkelId"),
                          @ForeignKey(entity = Layanan.class, parentColumns = "idLayanan", childColumns = "layananId")},
            primaryKeys = {"bengkelId", "layananId"})
    public static class LayananBengkel {
        private long bengkelId;
        private long layananId;
        private int harga;

        public LayananBengkel(long bengkelId, long layananId, int harga) {
            this.setBengkelId(bengkelId);
            this.setLayananId(layananId);
            this.setHarga(harga);
        }


        public long getBengkelId() {
            return bengkelId;
        }

        public void setBengkelId(long bengkelId) {
            this.bengkelId = bengkelId;
        }

        public long getLayananId() {
            return layananId;
        }

        public void setLayananId(long layananId) {
            this.layananId = layananId;
        }

        public int getHarga() {
            return harga;
        }

        public void setHarga(int harga) {
            this.harga = harga;
        }
    }

}
