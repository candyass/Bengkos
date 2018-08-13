package com.unbaja.inggi.bengkos.view.fragment;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unbaja.inggi.bengkos.R;
import com.unbaja.inggi.bengkos.model.EditBengkelEvent;
import com.unbaja.inggi.bengkos.util.MyLogger;
import com.unbaja.inggi.bengkos.view.activity.InputLayananActivity;
import com.unbaja.inggi.bengkos.view.activity.MainActivity;
import com.unbaja.inggi.bengkos.view.dialog.EditBengkelDialog;
import com.unbaja.inggi.bengkos.viewmodel.DetailBengkelFragmentViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sigit on 26/06/2018.
 */

public class DetailBengkelFragment extends Fragment implements OnMapReadyCallback {

    private ViewSwitcher mSwitcher;
    private TextView mTextUbahHari;
    private TextView mTextUbahJam;
    private TextView mNamaBengkelText;
    private TextView mEmailText;
    private TextView mNoTeleponText;
    private TextView mAlamatText;
    private TextView mHariBukaText;
    private TextView mHariTutupText;
    private TextView mJamBukaText;
    private TextView mJamTutupText;
    private CircleImageView mFoto;
    private SupportMapFragment mLokasiFragment;
    private Button mLayananButton;
    private CheckBox mCheckPerbaikan;

    private DetailBengkelFragmentViewModel mViewModel;

    private long mBengkelId;
    private boolean isMasukTerdaftar;

    private LatLng mLatLong;

    public static Fragment newInstance() {
        Fragment fragment = new DetailBengkelFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        isMasukTerdaftar = activity.isMasukTerdaftar();
        mBengkelId = activity.getBengkelId();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DetailBengkelFragmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_bengkel, container, false);
        initViews(view);

        if(isMasukTerdaftar) {
            mViewModel.getBengkel(mBengkelId).observe(this, bengkel -> {
                if(bengkel != null) {
                    tampilkanDetail();
                    aktifkanModeEdit();
                    mCheckPerbaikan.setChecked(bengkel.isPanggilanPerbaikan());
                    mFoto.setImageBitmap(bengkel.getFoto());
                    mNamaBengkelText.setText(bengkel.getNamaBengkel());
                    mEmailText.setText(bengkel.getEmail());
                    mNoTeleponText.setText(bengkel.getNoTelepon());
                    mJamBukaText.setText(bengkel.getJamBuka());
                    mJamTutupText.setText(bengkel.getJamTutup());
                    mHariTutupText.setText(bengkel.getHariTutup());
                    mHariBukaText.setText(bengkel.getHariBuka());
                    mAlamatText.setText(bengkel.getLokasi().getNamaLokasi());
                    mLatLong = new LatLng(bengkel.getLokasi().getLatitude(), bengkel.getLokasi().getLongtitude());
                    mLokasiFragment.getMapAsync(this);
                }
            });
        }
        return view;
    }

    private void aktifkanModeEdit() {
        mTextUbahJam.setVisibility(View.VISIBLE);
        mTextUbahHari.setVisibility(View.VISIBLE);

        mTextUbahJam.setOnClickListener(v -> {
            DialogFragment dilogJam = EditBengkelDialog.createEditJamDialog();
            dilogJam.show(getChildFragmentManager(), EditBengkelDialog.DIALOG_TAG);
        });

        mTextUbahHari.setOnClickListener(v -> {
            DialogFragment dialogHari = EditBengkelDialog.createEditHariDialog();
            dialogHari.show(getChildFragmentManager(), EditBengkelDialog.DIALOG_TAG);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EditBengkelEvent event) {
        if(event != null) {
            if (event.getEvent() == EditBengkelEvent.EVENT_HARI) {
                mHariBukaText.setText(event.getText1());
                mHariTutupText.setText(event.getText2());
                mViewModel.updateHariBukaDanTutup(mBengkelId, event.getText1(),event.getText2());
            } else if (event.getEvent() == EditBengkelEvent.EVENT_JAM) {
                mJamBukaText.setText(event.getText1());
                mJamTutupText.setText(event.getText2());
                mViewModel.updateJamBukaDanTutup(mBengkelId, event.getText1(), event.getText2());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initViews(View view) {
        mSwitcher = view.findViewById(R.id.view_switcher);
        mNamaBengkelText = view.findViewById(R.id.detail_bengkel_nama_bengkel);
        mEmailText = view.findViewById(R.id.detail_bengkel_email);
        mNoTeleponText = view.findViewById(R.id.detail_bengkel_no_telepon);
        mAlamatText = view.findViewById(R.id.detail_bengkel_alamat);
        mFoto = view.findViewById(R.id.detail_bengkel_foto);
        mHariBukaText = view.findViewById(R.id.detail_bengkel_hari_buka);
        mHariTutupText = view.findViewById(R.id.detail_bengkel_hari_tutup);
        mJamBukaText = view.findViewById(R.id.detail_bengkel_jam_buka);
        mJamTutupText = view.findViewById(R.id.detail_bengkel_jam_tutup);
        mTextUbahHari = view.findViewById(R.id.detail_bengkel_ubah_hari);
        mTextUbahJam = view.findViewById(R.id.detail_bengkel_ubah_jam);
        mCheckPerbaikan = view.findViewById(R.id.detail_bengkel_check_perbaikan);
        mLokasiFragment = (SupportMapFragment) getChildFragmentManager().
                findFragmentById(R.id.detail_bengkel_lokasi_fragment);
        mLayananButton = view.findViewById(R.id.detail_bengkel_layanan_button);

        mLayananButton.setOnClickListener(v -> {
            Intent intent = InputLayananActivity.newIntent(getContext(), mBengkelId, true);
            startActivity(intent);
        });
    }

    private void tampilkanDetail() {
        if(mSwitcher.getNextView().getId() == R.id.detail_bengkel_detail_layout) {
            mSwitcher.showNext();
        }
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
