package com.unbaja.inggi.bengkos.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unbaja.inggi.bengkos.R;
import com.unbaja.inggi.bengkos.database.entity.Bengkel;
import com.unbaja.inggi.bengkos.model.MarkerData;
import com.unbaja.inggi.bengkos.util.MyLogger;
import com.unbaja.inggi.bengkos.view.activity.MainActivity;
import com.unbaja.inggi.bengkos.viewmodel.PetaBengkelFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sigit on 26/06/2018.
 */

public class PetaBengkelFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private Switch mSwitchFilter;
    private ConstraintLayout mConstraint;
    private RadioButton mRadioMobil;
    private RadioButton mRadioMotor;
    private RadioButton mRadioSemua;
    private RadioGroup mRadioGroup;
    private Button mButtonCari;
    private SupportMapFragment mMapFragment;

    private PetaBengkelFragmentViewModel mViewModel;
    private FusedLocationProviderClient mLocationClient;
    private LatLng mPosisi;
    private List<Marker> listMarker;
    private List<Bengkel> mListBengkel;
    private GoogleMap mGoogleMap;

    public static Fragment newInstance() {
        Fragment fragment = new PetaBengkelFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PetaBengkelFragmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peta_bengkel, container, false);
        initViews(view);
        return view;
    }


    private void initViews(View view) {
        mSwitchFilter = view.findViewById(R.id.pencarian_stub_switch_filter);
        mButtonCari = view.findViewById(R.id.pencarian_stub_button_cari);
        mConstraint = view.findViewById(R.id.pencarian_stub_constraint);
        mRadioMobil = view.findViewById(R.id.pencarian_stub_rMobil);
        mRadioMotor = view.findViewById(R.id.pencarian_stub_rMotor);
        mRadioSemua = view.findViewById(R.id.pencarian_stub_rSemua);
        mRadioGroup = view.findViewById(R.id.pencarian_stub_radioGroup);
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_peta_bengkel_map);

        mViewModel.getAllBengkel().observe(this, bengkels -> {
            if (bengkels != null && bengkels.size() > 0) {
                clearAllMarker();
                mListBengkel = bengkels;
            }
            mMapFragment.getMapAsync(PetaBengkelFragment.this);
        });


        mSwitchFilter.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mConstraint.setVisibility(View.VISIBLE);
            } else {
                mConstraint.setVisibility(View.GONE);
            }
        });

        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.pencarian_stub_rMobil:
                    mButtonCari.setEnabled(true);
                    break;
                case R.id.pencarian_stub_rMotor:
                    mButtonCari.setEnabled(true);
                    break;
                case R.id.pencarian_stub_rSemua:
                    mButtonCari.setEnabled(true);
                    break;
            }
        });

        mButtonCari.setOnClickListener(v -> {
            MainActivity activity = (MainActivity) getActivity();
            activity.showMarkerDetail(false);
            if (mViewModel.getAllBengkel().hasObservers()) {
                MyLogger.logPesan("getAllBengkel has observers");
                mViewModel.getAllBengkel().removeObservers(PetaBengkelFragment.this);
            }
            int id = mRadioGroup.getCheckedRadioButtonId();
            switch (id) {
                case R.id.pencarian_stub_rMobil:
                    mViewModel.getAllBengkel(1).observe(PetaBengkelFragment.this, bengkels -> {
                        MyLogger.logPesan("getAllBengkel Mobil");
                        clearAllMarker();
                        mListBengkel = bengkels;
                        listMarker = tambahMarker(mGoogleMap, mListBengkel);
                    });
                    break;
                case R.id.pencarian_stub_rMotor:
                    mViewModel.getAllBengkel(2).observe(PetaBengkelFragment.this, bengkels -> {
                        MyLogger.logPesan("getAllBengkel Motor");
                        clearAllMarker();
                        mListBengkel = bengkels;
                        listMarker = tambahMarker(mGoogleMap, mListBengkel);
                    });
                    break;
                case R.id.pencarian_stub_rSemua:
                    mViewModel.getAllBengkel(3).observe(PetaBengkelFragment.this, bengkels -> {
                        MyLogger.logPesan("getAllBengkel Semua");
                        clearAllMarker();
                        mListBengkel = bengkels;
                        listMarker = tambahMarker(mGoogleMap, mListBengkel);
                    });
                    break;
            }
            mRadioGroup.clearCheck();
            mButtonCari.setEnabled(false);
        });
    }

    @SuppressLint("MissingPermission")
    private void updateLokasi(GoogleMap map) {
        if (mLocationClient == null) {
            mLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        }
        mLocationClient.getLastLocation().addOnSuccessListener(command -> {
            if (command != null) {
                mPosisi = new LatLng(command.getLatitude(), command.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mPosisi, 17);
                MarkerOptions options = new MarkerOptions();
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_lokasi_saya2));
                options.position(mPosisi);
                options.flat(false);
                Marker marker = map.addMarker(options);
                marker.setTag(new MarkerData(false));
                map.animateCamera(cameraUpdate);
            }
            if (mListBengkel != null) {
                if (mListBengkel.size() > 0) {
                    listMarker = tambahMarker(map, mListBengkel);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MyLogger.logPesan("onRequestPermissionResult");
        MyLogger.logPesan("grantResults size : " + String.valueOf(grantResults.length));
        if (requestCode == 1) {
            if(grantResults.length == 2) {
                int result1 = grantResults[0];
                int result2 = grantResults[1];
                if (result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED) {
                    updateLokasi(mGoogleMap);
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       googleMap.setOnMarkerClickListener(this);
       googleMap.setOnMapClickListener(this);
       mGoogleMap = googleMap;
       if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
               == PackageManager.PERMISSION_DENIED
               || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
               == PackageManager.PERMISSION_DENIED) {
           requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                   Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
       }else {
           updateLokasi(mGoogleMap);
       }
    }

    private List<Marker> tambahMarker(GoogleMap map, List<Bengkel> list) {
        List<Marker> markers = new ArrayList<>();
        for(Bengkel b : list) {
            Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(b.getLokasi().getLatitude(),
                    b.getLokasi().getLongtitude())).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_lokasi_bengkel)));
            marker.setTag(new MarkerData(true, b.getId(), b.getNamaBengkel(),
                    b.getHariBuka(), b.getHariTutup(), b.getJamBuka(), b.getJamTutup()));
            markers.add(marker);
        }
        return markers;
    }

    private void clearAllMarker() {
        if(listMarker != null) {
            if(listMarker.size() > 0) {
                for(Marker m : listMarker) {
                    m.remove();
                }
                listMarker.clear();
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        MarkerData data = (MarkerData) marker.getTag();
        if (data.isBengkel()) {
            MainActivity activity = (MainActivity) getActivity();
            activity.showMarkerDetail(true);
            activity.updateMarkerDetail(data);
            return true;
        }
        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        MainActivity activity = (MainActivity) getActivity();
        activity.showMarkerDetail(false);
    }
}
