package com.unbaja.inggi.bengkos.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.unbaja.inggi.bengkos.BengkelRepository;
import com.unbaja.inggi.bengkos.database.entity.Bengkel;
import com.unbaja.inggi.bengkos.model.QueryLayanan;

import java.util.List;

/**
 * Created by sigit on 02/07/2018.
 */

public class InputLayananActivityViewModel extends AndroidViewModel {

    private BengkelRepository repository;

    public InputLayananActivityViewModel(@NonNull Application application) {
        super(application);
        repository = BengkelRepository.getInstance(application);
    }

    public LiveData<List<QueryLayanan>> getAllLayananPerawatan(long idBengkel) {
        return repository.getAllLayananPerawatanBerkala(idBengkel);
    }

    public LiveData<List<QueryLayanan>> getAllLayananBanDanVelg(long idBengkel) {
        return repository.getAllLayananBanDanVelg(idBengkel);
    }

    public LiveData<List<QueryLayanan>> getAllLayananSalonCuci(long idBengkel) {
        return repository.getAllLayananSalonCuci(idBengkel);
    }

    public void insertLayananBengkel(List<Bengkel.LayananBengkel> list) {
        repository.insertLayananBengkel(list);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository = null;
    }
}
