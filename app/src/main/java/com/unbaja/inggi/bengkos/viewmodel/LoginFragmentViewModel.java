package com.unbaja.inggi.bengkos.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.unbaja.inggi.bengkos.BengkelRepository;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by sigit on 29/06/2018.
 */

public class LoginFragmentViewModel extends AndroidViewModel {

    private BengkelRepository repository;

    public LoginFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = BengkelRepository.getInstance(application);
    }


    public long isValidLogin(String email, String katasandi) throws ExecutionException, InterruptedException {
        return repository.getValidBengkelId(email, katasandi);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository = null;
    }
}
