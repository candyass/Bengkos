package com.unbaja.inggi.bengkos.model;

import com.unbaja.inggi.bengkos.database.entity.Layanan;

/**
 * Created by sigit on 02/07/2018.
 */

public class LayananEvent {
    private Layanan mLayanan;

    public LayananEvent(Layanan layanan) {
        this.mLayanan = layanan;
    }

    public Layanan getmLayanan() {
        return mLayanan;
    }
}
