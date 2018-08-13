package com.unbaja.inggi.bengkos.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.unbaja.inggi.bengkos.BengkelRepository;
import com.unbaja.inggi.bengkos.database.entity.Bengkel;

/**
 * Created by sigit on 01/07/2018.
 */

public class DetailBengkelActivityViewModel extends AndroidViewModel {

    private BengkelRepository repository;

    public DetailBengkelActivityViewModel(@NonNull Application application) {
        super(application);
        repository = BengkelRepository.getInstance(application);
    }


    public LiveData<Bengkel> getBengkel(long idBengkel) {
        return repository.getBengkel(idBengkel);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository = null;
    }
}
