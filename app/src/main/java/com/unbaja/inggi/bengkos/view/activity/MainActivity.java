package com.unbaja.inggi.bengkos.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.unbaja.inggi.bengkos.R;
import com.unbaja.inggi.bengkos.model.MarkerData;
import com.unbaja.inggi.bengkos.util.MyLogger;
import com.unbaja.inggi.bengkos.view.dialog.KonfirmasiDialog;
import com.unbaja.inggi.bengkos.view.fragment.DaftarBengkelFragment;
import com.unbaja.inggi.bengkos.view.fragment.DetailBengkelFragment;
import com.unbaja.inggi.bengkos.view.fragment.PetaBengkelFragment;

public class MainActivity extends AppCompatActivity {


    private static final String KEY_BENGKEL_ID = "com.unbaja.inggi.bengkos.key.bengkelid";
    private static final String KEY_MASUK_TERDAFTAR = "com.unbaja.inggi.bengkos.key.masukterdaftar";

    private CardView mContainer;
    private TextView mTextTutup;
    private TextView mTextNamaBengkel;
    private TextView mTextHariBukaHariTutup;
    private TextView mTextJamBukaJamTutup;

    private boolean isMasukTerdaftar;
    private long mBengkelId;


    public static Intent newIntent(Context context, long bengkelId, boolean masukTerdaftar) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(KEY_BENGKEL_ID, bengkelId);
        intent.putExtra(KEY_MASUK_TERDAFTAR, masukTerdaftar);
        return intent;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_peta_bengkel:
                    changeFragment(PetaBengkelFragment.newInstance());
                    return true;
                case R.id.nav_daftar_bengkel:
                    changeFragment(DaftarBengkelFragment.newInstance());
                    return true;
                case R.id.nav_bengkel_saya:
                    changeFragment(DetailBengkelFragment.newInstance());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainer = findViewById(R.id.item_marker_container);
        mTextNamaBengkel = findViewById(R.id.item_marker_nama_bengkel);
        mTextHariBukaHariTutup = findViewById(R.id.item_marker_hari_buka_hari_tutup);
        mTextJamBukaJamTutup = findViewById(R.id.item_marker_jam_buka_jam_tutup);
        mTextTutup = findViewById(R.id.item_marker_tutup_text);
        mTextTutup.setOnClickListener(v -> {
            showMarkerDetail(false);
        });

        isMasukTerdaftar = getIntent().getBooleanExtra(KEY_MASUK_TERDAFTAR, false);
        mBengkelId = getIntent().getLongExtra(KEY_BENGKEL_ID, -1);



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if(fragment == null) {
            fragment = PetaBengkelFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
            navigation.setSelectedItemId(R.id.nav_peta_bengkel);
        }
    }



    @Override
    public void onBackPressed() {
        DialogFragment dialog = KonfirmasiDialog.newInstance("Yakin Ingin Keluar ?", (dialog1, which) -> {
            super.onBackPressed();
        }, null);
        dialog.show(getSupportFragmentManager(), KonfirmasiDialog.TAG_KONFIRMASI_DIALOG);

    }

    public boolean isMasukTerdaftar() {
        return isMasukTerdaftar;
    }

    public long getBengkelId() {
        return mBengkelId;
    }

    public void showMarkerDetail(boolean value) {
        if(value) {
            mContainer.setVisibility(View.VISIBLE);
        }else {
            mContainer.setVisibility(View.GONE);
        }
    }

    public void updateMarkerDetail(MarkerData data) {
        mTextNamaBengkel.setText(data.getNamaBengkel());
        mTextHariBukaHariTutup.setText(data.getHariBuka() + " s.d. " + data.getHariTutup());
        mTextJamBukaJamTutup.setText(data.getJamBuka() + " s.d. " + data.getJamTutup());
        mContainer.setOnClickListener(v -> {
            Intent intent = DetailBengkelActivity.newIntent(getBaseContext(), data.getIdBengkel());
            startActivity(intent);
        });
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }


}
