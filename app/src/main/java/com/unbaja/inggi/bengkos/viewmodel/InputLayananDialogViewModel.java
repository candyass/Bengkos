package com.unbaja.inggi.bengkos.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.unbaja.inggi.bengkos.BengkelRepository;
import com.unbaja.inggi.bengkos.database.entity.Layanan;

import java.util.List;

/**
 * Created by sigit on 01/07/2018.
 */

public class InputLayananDialogViewModel extends AndroidViewModel {

    private BengkelRepository repository;

    public InputLayananDialogViewModel(@NonNull Application application) {
        super(application);
        repository = BengkelRepository.getInstance(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository = null;
    }


    public LiveData<List<Layanan>> getAllLayananPerawatanBerkala() {
        return repository.getAllLayananPerawatanBerkala();
    }

    public LiveData<List<Layanan>> getAllLayananBanDanVelg() {
        return repository.getAllLayananBanDanVelg();
    }

    public LiveData<List<Layanan>> getAllLayananSalonCuci() {
        return repository.getAllLayananSalonCuci();
    }
}
