package com.unbaja.inggi.bengkos.view.fragment;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.squareup.picasso.Picasso;
import com.unbaja.inggi.bengkos.R;
import com.unbaja.inggi.bengkos.database.entity.Bengkel;
import com.unbaja.inggi.bengkos.database.entity.Lokasi;
import com.unbaja.inggi.bengkos.model.LoginEvent;
import com.unbaja.inggi.bengkos.util.MyLogger;
import com.unbaja.inggi.bengkos.view.dialog.PesanDialog;
import com.unbaja.inggi.bengkos.viewmodel.PendaftaranBengkelFragmentViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sigit on 26/06/2018.
 */

public class PendaftaranBengkelFragment extends Fragment {

    private static final int REQUST_PHOTO = 100;
    private static int REQUEST_LOKASI = 101;

    private static String TAG_DIALOG_PESAN = "com.unbaja.inggi.bengkos.tag.dialog.pesan";

    private static final String ARG_EMAIL = "com.unbaja.inggi.bengkos.arg.email";
    private static final String ARG_KATA_SANDI = "com.unbaja.inggi.bengkos.arg.katasandi";
    private static final String ARG_NAMA_BENGKEL = "com.unbaja.inggi.bengkos.arg.namabengkel";
    private static final String ARG_NO_TELEPON = "com.unbaja.inggi.bengkos.arg.notelepon";

    private CircleImageView mFoto;
    private TextView mTambahLokasiText;
    private TextView mLokasiText;
    private TextView mJamBukaText;
    private TextView mJamTutupText;
    private Button mDaftarkanButton;
    private Button mUploadButton;
    private Spinner mHariBukaSpinner;
    private Spinner mHariTutupSpinner;
    private RadioButton mMobilRadio;
    private RadioButton mMotorRadio;
    private RadioButton mSemuaRadio;
    private CheckBox mCheckPanggilanPerbaikan;

    private PendaftaranBengkelFragmentViewModel mViewModel;

    private Bengkel mBengkel;
    private boolean isLokasiSelected = false;
    private boolean isPhotoSelected = false;
    private String mAlamat;
    private double mLatitude;
    private double mLongtitude;


    public static Fragment newInstance(String email, String kataSandi, String namaBengkel, String noTelepon) {
        Fragment fragment = new PendaftaranBengkelFragment();
        Bundle arg = new Bundle();
        arg.putString(ARG_EMAIL, email);
        arg.putString(ARG_KATA_SANDI, kataSandi);
        arg.putString(ARG_NAMA_BENGKEL, namaBengkel);
        arg.putString(ARG_NO_TELEPON, noTelepon);
        fragment.setArguments(arg);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(PendaftaranBengkelFragmentViewModel.class);

        String email = getArguments().getString(ARG_EMAIL);
        String katasandi = getArguments().getString(ARG_KATA_SANDI);
        String namaBengkel = getArguments().getString(ARG_NAMA_BENGKEL);
        String noTelepon = getArguments().getString(ARG_NO_TELEPON);
        mBengkel = new Bengkel(email, katasandi, namaBengkel, noTelepon);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pendaftaran_bengkel, container, false);
        initViews(view);
        initListeners();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == REQUEST_LOKASI) {
               Place place =  PlacePicker.getPlace(getContext(), data);
               mAlamat = place.getAddress().toString();
               mLatitude = place.getLatLng().latitude;
               mLongtitude = place.getLatLng().longitude;
               mLokasiText.setText(mAlamat);
               isLokasiSelected = true;
            }
            else if(requestCode == REQUST_PHOTO) {
                Uri photoUri = data.getData();
                if(photoUri != null) {
                    MyLogger.logPesan("photoUri is not null");
                    Picasso.with(getContext()).load(photoUri).resize(80,80).into(mFoto);
                    mViewModel.simpanFoto(photoUri);
                    isPhotoSelected = true;
                }else {
                    MyLogger.logPesan("photoUri is null");
                }
            }
        }
    }

    private void initViews(View view) {
        mFoto = view.findViewById(R.id.fragment_pendaftaran_foto);
        mTambahLokasiText = view.findViewById(R.id.fragment_pendaftaran_tambah_lokasi_text);
        mLokasiText = view.findViewById(R.id.fragment_pendaftaran_lokasi_text);
        mJamBukaText = view.findViewById(R.id.fragment_pendaftaran_jam_buka_text);
        mJamTutupText = view.findViewById(R.id.fragment_pendaftaran_jam_tutup_text);
        mDaftarkanButton = view.findViewById(R.id.fragment_pendaftaran_daftar_button);
        mUploadButton = view.findViewById(R.id.fragment_pendaftaran_upload_button);
        mHariBukaSpinner = view.findViewById(R.id.fragment_pendaftaran_hari_buka_spinner);
        mHariTutupSpinner = view.findViewById(R.id.fragment_pendaftaran_hari_tutup_spinner);
        mMobilRadio = view.findViewById(R.id.fragment_pendaftaran_mobil_radio);
        mMotorRadio = view.findViewById(R.id.fragment_pendaftaran_motor_radio);
        mSemuaRadio = view.findViewById(R.id.fragment_pendaftaran_semua_radio);
        mCheckPanggilanPerbaikan = view.findViewById(R.id.fragment_pendaftaran_panggilan_perbaikan_check);


        String[] daftarHari = getResources().getStringArray(R.array.list_hari);
        mHariBukaSpinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,daftarHari));
        mHariTutupSpinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,daftarHari));
    }

    private void initListeners() {
        mUploadButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            Intent choser = Intent.createChooser(intent, "Upload File Dari");
            startActivityForResult(choser, REQUST_PHOTO);
        });

        mJamBukaText.setOnClickListener(v -> {
            TimePickerDialog dialog = new TimePickerDialog(getContext(), (view1, hourOfDay, minute) -> {
                mJamBukaText.setText(String.valueOf(hourOfDay)+":"+String.valueOf(minute));
            }, 0, 0, true);
            dialog.show();
        });

        mJamTutupText.setOnClickListener(v -> {
            TimePickerDialog dialog = new TimePickerDialog(getContext(), (view1, hourOfDay, minute) -> {
                mJamTutupText.setText(String.valueOf(hourOfDay)+":"+String.valueOf(minute));
            }, 0, 0, true);
            dialog.show();
        });


        mTambahLokasiText.setOnClickListener(v -> {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            try {
                startActivityForResult(intentBuilder.build(getActivity()), REQUEST_LOKASI);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        });

        mDaftarkanButton.setOnClickListener(v -> {
            if (!isPhotoSelected ) {
                PesanDialog dialog = PesanDialog.newInstance("Photo Belum Dipilih");
                dialog.show(getChildFragmentManager(), TAG_DIALOG_PESAN);
                return;
            }
            if (!isLokasiSelected) {
                PesanDialog dialog = PesanDialog.newInstance("Lokasi Belum Ditentukan");
                dialog.show(getChildFragmentManager(), TAG_DIALOG_PESAN);
                return;
            }
            if (mLokasiText.getText().toString().isEmpty()) {
                PesanDialog dialog = PesanDialog.newInstance("Lokasi Belum Ditentukan");
                dialog.show(getChildFragmentManager(), TAG_DIALOG_PESAN);
                return;
            }
            if (!isRadioSelected()) {
                PesanDialog dialog = PesanDialog.newInstance("Layanan Belum Dipilih");
                dialog.show(getChildFragmentManager(), TAG_DIALOG_PESAN);
                return;
            }
            int pilihan = getSelectedRadio();
            String hariBuka =(String) mHariBukaSpinner.getSelectedItem();
            String hariTutup = (String) mHariTutupSpinner.getSelectedItem();
            String jamBuka = mJamBukaText.getText().toString();
            String jamTutup = mJamTutupText.getText().toString();
            mBengkel.setLokasi(new Lokasi(mAlamat, mLatitude, mLongtitude));
            mBengkel.setCustomerId(pilihan);
            mBengkel.setHariBuka(hariBuka);
            mBengkel.setHariTutup(hariTutup);
            mBengkel.setJamBuka(jamBuka);
            mBengkel.setJamTutup(jamTutup);
            mBengkel.setPanggilanPerbaikan(mCheckPanggilanPerbaikan.isChecked());
            long id = 0;
            try {
               id =  mViewModel.insertBengkel(mBengkel);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LoginEvent event = new LoginEvent(LoginEvent.EVENT_PENDAFTARAN_SUKSES);
            event.setBengkelId(id);
            EventBus.getDefault().post(event);
        });
    }

    private int getSelectedRadio() {
        if (mMobilRadio.isChecked()) {
            return 1;
        }
        else if (mMotorRadio.isChecked()) {
            return 2;
        }
        return 3;
    }

    private boolean isRadioSelected() {
        return mMobilRadio.isChecked() || mMotorRadio.isChecked() || mSemuaRadio.isChecked();
    }

}
