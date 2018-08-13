package com.unbaja.inggi.bengkos.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.unbaja.inggi.bengkos.BengkelRepository;
import com.unbaja.inggi.bengkos.database.entity.Bengkel;

/**
 * Created by sigit on 29/06/2018.
 */

public class DetailBengkelFragmentViewModel extends AndroidViewModel {

    private BengkelRepository repository;

    public DetailBengkelFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = BengkelRepository.getInstance(application);
    }


    public LiveData<Bengkel> getBengkel(long idBengkel) {
        return repository.getBengkel(idBengkel);
    }

    public void updateHariBukaDanTutup(long idBengkel, String hBuka, String hTutup) {
        repository.updateHariBukaDanTutup(idBengkel, hBuka, hTutup);
    }

    public void updateJamBukaDanTutup(long idBengkel, String jBuka, String jTutup) {
        repository.updateJamBukaDanTutup(idBengkel, jBuka, jTutup);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        repository = null;
    }
}
