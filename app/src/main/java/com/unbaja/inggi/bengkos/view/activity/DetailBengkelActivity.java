package com.unbaja.inggi.bengkos.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unbaja.inggi.bengkos.R;
import com.unbaja.inggi.bengkos.viewmodel.DetailBengkelActivityViewModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailBengkelActivity extends AppCompatActivity implements OnMapReadyCallback {


    private static final String KEY_BENGKEL_ID = "com.unbaja.inggi.bengkos.key.bengkelid";

    private CircleImageView mFoto;
    private TextView mTextNamaBengkel;
    private TextView mTextEmail;
    private TextView mTextNoTelepon;
    private TextView mTextAlamat;
    private TextView mTextHariBuka;
    private TextView mTextHariTutup;
    private TextView mTextJamBuka;
    private TextView mTextJamTutup;
    private CheckBox mCheckPanggilan;
    private Button mButtonLihatLayanan;
    private SupportMapFragment mLokasiFragment;

    private LatLng mLatLong;
    private DetailBengkelActivityViewModel mViewModel;
    private long mIdBengkel;


    public static Intent newIntent(Context context, long bengkelId) {
        Intent intent = new Intent(context, DetailBengkelActivity.class);
        intent.putExtra(KEY_BENGKEL_ID, bengkelId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_bengkel);
        initViews();
        mViewModel = ViewModelProviders.of(this).get(DetailBengkelActivityViewModel.class);

        mIdBengkel = getIntent().getLongExtra(KEY_BENGKEL_ID, -1);
        if(mIdBengkel != -1) {
            mViewModel.getBengkel(mIdBengkel).observe(this, bengkel -> {
                mFoto.setImageBitmap(bengkel.getFoto());
                mCheckPanggilan.setChecked(bengkel.isPanggilanPerbaikan());
                mTextNamaBengkel.setText(bengkel.getNamaBengkel());
                mTextEmail.setText(bengkel.getEmail());
                mTextNoTelepon.setText(bengkel.getNoTelepon());
                mTextHariBuka.setText(bengkel.getHariBuka());
                mTextHariTutup.setText(bengkel.getHariTutup());
                mTextJamBuka.setText(bengkel.getJamBuka());
                mTextJamTutup.setText(bengkel.getJamTutup());
                mTextAlamat.setText(bengkel.getLokasi().getNamaLokasi());
                mLatLong = new LatLng(bengkel.getLokasi().getLatitude(), bengkel.getLokasi().getLongtitude());
                mLokasiFragment.getMapAsync(this);

                mTextNoTelepon.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + mTextNoTelepon.getText()));
                    startActivity(intent);
                });
            });
        }




    }

    private void initViews() {
        mFoto = findViewById(R.id.detail_bengkel_foto);
        mTextNamaBengkel = findViewById(R.id.detail_bengkel_nama_bengkel);
        mTextEmail = findViewById(R.id.detail_bengkel_email);
        mTextNoTelepon = findViewById(R.id.detail_bengkel_no_telepon);
        mTextAlamat = findViewById(R.id.detail_bengkel_alamat);
        mTextHariBuka = findViewById(R.id.detail_bengkel_hari_buka);
        mTextHariTutup = findViewById(R.id.detail_bengkel_hari_tutup);
        mTextJamBuka = findViewById(R.id.detail_bengkel_jam_buka);
        mTextJamTutup = findViewById(R.id.detail_bengkel_jam_tutup);
        mButtonLihatLayanan = findViewById(R.id.detail_bengkel_layanan_button);
        mCheckPanggilan = findViewById(R.id.detail_bengkel_check_perbaikan);
        mLokasiFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_bengkel_lokasi_fragment);

        mButtonLihatLayanan.setOnClickListener(v -> {
            Intent intent = InputLayananActivity.newIntent(getBaseContext(), mIdBengkel, false);
            startActivity(intent);
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        CameraUpdate centerLocation = CameraUpdateFactory.newLatLng(mLatLong);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);
        googleMap.addMarker(new MarkerOptions().position(mLatLong));
        googleMap.moveCamera(centerLocation);
        googleMap.moveCamera(zoom);
    }
}
