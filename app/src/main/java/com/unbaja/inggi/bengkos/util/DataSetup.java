package com.unbaja.inggi.bengkos.util;

import com.unbaja.inggi.bengkos.database.entity.Customer;
import com.unbaja.inggi.bengkos.database.entity.Kategori;
import com.unbaja.inggi.bengkos.database.entity.Layanan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sigit on 28/06/2018.
 */

public class DataSetup {

    public static List<Kategori> getDaftarKategori() {
        List<Kategori> list = new ArrayList<>();
        list.add(new Kategori(1, Kategori.KATEGORI_PERAWATAN_BERKALA));
        list.add(new Kategori(2, Kategori.KATEGORI_BAN_DAN_VELG));
        list.add(new Kategori(3, Kategori.KATEGORI_SALON_CUCI));
        return list;
    }

    public static List<Layanan> getDaftarLayanan() {
        List<Layanan> list = new ArrayList<>();

        // input layanan perawatan berkala
        list.add(new Layanan(1, "Ganti Oli Mobil"));
        list.add(new Layanan(1, "Ganti Oli Motor"));
        list.add(new Layanan(1, "Service Mobil"));
        list.add(new Layanan(1, "Service Motor"));

        // input layanan perawatan ban dan velg
        list.add(new Layanan(2, "Ganti Ban Dalam Mobil"));
        list.add(new Layanan(2, "Ganti Ban Luar Mobil"));
        list.add(new Layanan(2, "Ganti Ban Dalam Motor"));
        list.add(new Layanan(2, "Ganti Ban Luar Motor"));
        list.add(new Layanan(2, "Tambal Ban Mobil"));
        list.add(new Layanan(2, "Tambal Ban Motor"));
        list.add(new Layanan(2, "Tambal Ban Tubles Motor"));

        // input layanan salon cuci
        list.add(new Layanan(3, "Steam Mobil"));
        list.add(new Layanan(3, "Steam Motor"));
        return list;
    }

    public static List<Customer> getDaftarCustomer() {
        List<Customer> list = new ArrayList<>();
        list.add(new Customer(1, Customer.MOBIL_CS));
        list.add(new Customer(2, Customer.MOTOR_CS));
        list.add(new Customer(3, Customer.SEMUA_CS));
        return list;
    }
}
