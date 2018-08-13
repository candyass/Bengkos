package com.unbaja.inggi.bengkos.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.unbaja.inggi.bengkos.BengkelRepository;
import com.unbaja.inggi.bengkos.database.entity.Bengkel;

import java.util.List;

/**
 * Created by sigit on 05/07/2018.
 */

public class PetaBengkelFragmentViewModel extends AndroidViewModel {

    private BengkelRepository repository;

    public PetaBengkelFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = BengkelRepository.getInstance(application);
    }

    public LiveData<List<Bengkel>> getAllBengkel() {
        return repository.getAllBengkel();
    }

    public LiveData<List<Bengkel>> getAllBengkel(long melayani) {
        return repository.getAllBengkel(melayani);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository = null;
    }
}
