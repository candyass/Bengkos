package com.unbaja.inggi.bengkos.model;

import com.unbaja.inggi.bengkos.database.entity.Bengkel;

/**
 * Created by sigit on 26/06/2018.
 */

public class LoginEvent {

    public static int EVENT_PENDAFTARAN_AKUN = 1;
    public static int EVENT_PENDAFTARAN_BENGKEL = 2;
    public static int EVENT_PENDAFTARAN_SUKSES = 3;


    private int eventType;
    private String email;
    private String kataSandi;
    private String namaBengkel;
    private String noTelepon;
    private long bengkelId;
    private boolean masukTerdaftar;


    public LoginEvent(int eventType) {
        this.setEventType(eventType);
        this.setBengkelId(-1);
    }


    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKataSandi() {
        return kataSandi;
    }

    public void setKataSandi(String kataSandi) {
        this.kataSandi = kataSandi;
    }

    public String getNamaBengkel() {
        return namaBengkel;
    }

    public void setNamaBengkel(String namaBengkel) {
        this.namaBengkel = namaBengkel;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public long getBengkelId() {
        return bengkelId;
    }

    public void setBengkelId(long bengkelId) {
        this.bengkelId = bengkelId;
    }

    public boolean isMasukTerdaftar() {
        return masukTerdaftar;
    }

    public void setMasukTerdaftar(boolean masukTerdaftar) {
        this.masukTerdaftar = masukTerdaftar;
    }
}
