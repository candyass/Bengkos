package com.unbaja.inggi.bengkos;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.unbaja.inggi.bengkos.database.LocalDatabase;
import com.unbaja.inggi.bengkos.database.entity.Bengkel;
import com.unbaja.inggi.bengkos.database.entity.Customer;
import com.unbaja.inggi.bengkos.database.entity.Kategori;
import com.unbaja.inggi.bengkos.database.entity.Layanan;
import com.unbaja.inggi.bengkos.model.QueryLayanan;
import com.unbaja.inggi.bengkos.util.DataSetup;
import com.unbaja.inggi.bengkos.util.PreferencesUtils;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by sigit on 28/06/2018.
 */

public class BengkelRepository {


    private static BengkelRepository sSingleton;

    public static synchronized BengkelRepository getInstance(Context context) {
        if(sSingleton == null) {
            sSingleton = new BengkelRepository(context);
            if(!PreferencesUtils.getInstance(context).getBoolean()) {
                sSingleton.saveAllCustomer(DataSetup.getDaftarCustomer());
                sSingleton.saveAllKategori(DataSetup.getDaftarKategori());
                sSingleton.saveAllLayanan(DataSetup.getDaftarLayanan());
                PreferencesUtils.getInstance(context).saveBoolean(true);
            }
        }
        return sSingleton;
    }


    private LocalDatabase mDatabase;
    private Executor mExecutor;


    private BengkelRepository(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        mDatabase = application.getLocalDatabase();
        mExecutor = Executors.newSingleThreadExecutor();


    }



    public long insertBengkel(Bengkel bengkel) throws ExecutionException, InterruptedException {
        Callable<Long> callable =() -> mDatabase.getBengkelDAO().insertBengkel(bengkel);
        FutureTask<Long> task = new FutureTask<Long>(callable);
        mExecutor.execute(task);
        return task.get();
    }

    public LiveData<Bengkel> getBengkel(long idBengkel) {
        return mDatabase.getBengkelDAO().getBengkel(idBengkel);
    }

    public long getValidBengkelId(String email, String password) throws ExecutionException, InterruptedException {
        Callable<Long> callable = () -> mDatabase.getBengkelDAO().getValidBengkelId(email, password);
        FutureTask<Long> task = new FutureTask<Long>(callable);
        mExecutor.execute(task);
        return task.get();
    }

    public LiveData<List<Bengkel>> getAllBengkel() {
        return mDatabase.getBengkelDAO().getAllBengkel();
    }

    public LiveData<List<Layanan>> getAllLayananPerawatanBerkala(){
        return mDatabase.getLsyananDAO().getAllLayananPerawatanBerkala();
    }

    public LiveData<List<Layanan>> getAllLayananBanDanVelg() {
        return mDatabase.getLsyananDAO().getAllLayananBanDanVelg();
    }

    public LiveData<List<Layanan>> getAllLayananSalonCuci() {
        return mDatabase.getLsyananDAO().getAllLayananSalonCuci();
    }

    public LiveData<List<QueryLayanan>> getAllLayananPerawatanBerkala(long idBengkel) {
        return mDatabase.getLsyananDAO().getAllLayananPerawatanBerkala(idBengkel);
    }

    public LiveData<List<QueryLayanan>> getAllLayananBanDanVelg(long idBengkel) {
        return mDatabase.getLsyananDAO().getAllLayananBanDanVelg(idBengkel);
    }

    public LiveData<List<QueryLayanan>> getAllLayananSalonCuci(long idBengkel) {
        return mDatabase.getLsyananDAO().getAllLayananCuci(idBengkel);
    }

    public LiveData<List<Bengkel>> getAllBengkel(long melayani) {
        return mDatabase.getBengkelDAO().getAllBengkel(melayani);
    }



    public void insertLayananBengkel(List<Bengkel.LayananBengkel> list) {
        Runnable r = () -> {
            mDatabase.getLsyananDAO().insertLayananBengkel(list);
        };
        mExecutor.execute(r);
    }


    // PRIVATE METHODS

    private void saveAllCustomer(List<Customer> listCustomer) {
        Runnable r = () -> {
            mDatabase.getLsyananDAO().insertAllCustomer(listCustomer);
        };
        mExecutor.execute(r);
    }

    private void saveAllKategori(List<Kategori> listKategori) {
        Runnable r = () -> {
          mDatabase.getKategoriDAO().insertKategori(listKategori);
        };
        mExecutor.execute(r);
    }

    private void saveAllLayanan(List<Layanan> listLayanan) {
        Runnable r = () -> {
            mDatabase.getLsyananDAO().insertAllLayanan(listLayanan);
        };
        mExecutor.execute(r);
    }

    public void updateHariBukaDanTutup(long idBengkel, String hBuka, String hTutup) {
        Runnable r = () -> {
            mDatabase.getBengkelDAO().updateHariBukaDanTutup(idBengkel, hBuka,hTutup);
        };
        mExecutor.execute(r);
    }

    public void updateJamBukaDanTutup(long idBengkel, String jBuka, String jTutup) {
        Runnable r = () -> {
            mDatabase.getBengkelDAO().updateJamBukaDanTutup(idBengkel, jBuka, jTutup);
        };
        mExecutor.execute(r);
    }
}
