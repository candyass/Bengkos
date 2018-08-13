package com.unbaja.inggi.bengkos.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.squareup.picasso.Picasso;
import com.unbaja.inggi.bengkos.BengkelRepository;
import com.unbaja.inggi.bengkos.database.entity.Bengkel;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by sigit on 28/06/2018.
 */

public class PendaftaranBengkelFragmentViewModel extends AndroidViewModel {

    private BengkelRepository repository;
    private Picasso picasso;
    private Bitmap uploadedFoto;

    public PendaftaranBengkelFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = BengkelRepository.getInstance(application);
        picasso = Picasso.with(application.getApplicationContext());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository = null;
        picasso = null;
    }

    public void simpanFoto(Uri uri) {
        Callable<Bitmap> callable = () -> {
            uploadedFoto = picasso.load(uri).resize(100,100).get();
            return uploadedFoto;
        };
        FutureTask<Bitmap> task = new FutureTask<Bitmap>(callable);
        new Thread(task).start();
    }

    public long insertBengkel(Bengkel bengkel) throws ExecutionException, InterruptedException {
        bengkel.setFoto(uploadedFoto);
        return repository.insertBengkel(bengkel);
    }
}
