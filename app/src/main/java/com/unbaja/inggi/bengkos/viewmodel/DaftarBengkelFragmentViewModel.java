package com.unbaja.inggi.bengkos.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.unbaja.inggi.bengkos.BengkelRepository;
import com.unbaja.inggi.bengkos.database.entity.Bengkel;
import com.unbaja.inggi.bengkos.database.entity.Kategori;

import java.util.List;

/**
 * Created by sigit on 30/06/2018.
 */

public class DaftarBengkelFragmentViewModel extends AndroidViewModel {

    private BengkelRepository repository;

    public DaftarBengkelFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = BengkelRepository.getInstance(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository = null;
    }



    public LiveData<List<Bengkel>> getAllBengkel() {
        return repository.getAllBengkel();
    }

    public LiveData<List<Bengkel>> getAllBengkel(long melayani) {
        return repository.getAllBengkel(melayani);
    }
}
