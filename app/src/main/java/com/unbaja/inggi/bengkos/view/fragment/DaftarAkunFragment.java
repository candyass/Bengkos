package com.unbaja.inggi.bengkos.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.unbaja.inggi.bengkos.R;
import com.unbaja.inggi.bengkos.database.entity.Bengkel;
import com.unbaja.inggi.bengkos.model.LoginEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by sigit on 26/06/2018.
 */

public class DaftarAkunFragment extends Fragment {


    private TextInputLayout mLabelEmail;
    private TextInputLayout mLabelKatasandi;
    private TextInputLayout mLabelNoTelepon;
    private TextInputLayout mLabelNamaBengkel;

    private EditText mEmailText;
    private EditText mKatasandi;
    private EditText mNoTelepon;
    private EditText mNamaBengkel;


    private Button mLanjutkanButton;

    public static Fragment newInstance() {
        Fragment fragment = new DaftarAkunFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_akun_daftar, container, false);
        initViews(view);

        mLanjutkanButton.setOnClickListener(v -> {
            if(isTextKosong()) {
                validasiInput();
            }else {
                String email = mEmailText.getText().toString();
                String kataSandi = mKatasandi.getText().toString();
                String noTelepon = mNoTelepon.getText().toString();
                String namaBengkel = mNamaBengkel.getText().toString();
                LoginEvent event = new LoginEvent(LoginEvent.EVENT_PENDAFTARAN_BENGKEL);
                event.setEmail(email);
                event.setKataSandi(kataSandi);
                event.setNamaBengkel(namaBengkel);
                event.setNoTelepon(noTelepon);
                EventBus.getDefault().post(event);
            }

        });
        return view;
    }

    private void initViews(View view) {
        mLabelEmail = view.findViewById(R.id.fragment_akun_email_label);
        mLabelKatasandi = view.findViewById(R.id.fragment_akun_kata_sandi_label);
        mLabelNoTelepon = view.findViewById(R.id.fragment_akun_no_telepon_label);
        mLabelNamaBengkel = view.findViewById(R.id.fragment_akun_nama_bengkel_label);
        mEmailText = view.findViewById(R.id.fragment_akun_email_text);
        mKatasandi = view.findViewById(R.id.fragment_akun_kata_sandi_text);
        mNoTelepon = view.findViewById(R.id.fragment_akun_no_telepon);
        mNamaBengkel = view.findViewById(R.id.fragment_akun_nama_bengkel_text);
        mLanjutkanButton = view.findViewById(R.id.fragment_akun_lanjutkan_button);
    }

    private void tampilkanError(TextInputLayout label, String pesan) {
        label.setError(pesan);
    }

    private void validasiInput() {
        if(mEmailText.getText().toString().isEmpty()) {
            tampilkanError(mLabelEmail, "Email Belum Diisi");
        }else {
            tampilkanError(mLabelEmail, null);
        }
        if(mKatasandi.getText().toString().isEmpty()) {
            tampilkanError(mLabelKatasandi, "Kata Sandi Belum Diisi");
        }else {
            tampilkanError(mLabelKatasandi, null);
        }
        if(mNoTelepon.getText().toString().isEmpty()) {
            tampilkanError(mLabelNoTelepon, "No Telepon Belum Diisi");
        }else {
            tampilkanError(mLabelNoTelepon, null);
        }
        if(mNamaBengkel.getText().toString().isEmpty()) {
            tampilkanError(mLabelNamaBengkel, "Nama Bengkel Belum Diisi");
        }else {
            tampilkanError(mLabelNamaBengkel, null);
        }
    }

    private boolean isTextKosong() {
        return mEmailText.getText().toString().isEmpty() || mKatasandi.getText().toString().isEmpty() || mNoTelepon.getText()
                .toString().isEmpty() || mNamaBengkel.getText().toString().isEmpty();
    }
}
