package com.unbaja.inggi.bengkos.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.unbaja.inggi.bengkos.R;
import com.unbaja.inggi.bengkos.model.LoginEvent;
import com.unbaja.inggi.bengkos.view.dialog.PesanDialog;
import com.unbaja.inggi.bengkos.viewmodel.LoginFragmentViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutionException;

/**
 * Created by sigit on 26/06/2018.
 */

public class LoginFragment extends Fragment {

    private EditText mEmailText;
    private EditText mPasswordText;
    private TextView mDaftarText;
    private Button mMasukButton;
    private Button mLewatiButton;

    private LoginFragmentViewModel mViewModel;

    public static Fragment newInstance() {
        Fragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoginFragmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mEmailText = view.findViewById(R.id.fragment_login_email_text);
        mPasswordText = view.findViewById(R.id.fragment_login_katasandi_text);
        mDaftarText = view.findViewById(R.id.fragment_login_daftar_text);
        mMasukButton = view.findViewById(R.id.fragment_login_masuk_button);
        mLewatiButton = view.findViewById(R.id.fragment_login_lewati_button);



        mDaftarText.setOnClickListener(v -> {
            EventBus.getDefault().post(new LoginEvent(LoginEvent.EVENT_PENDAFTARAN_AKUN));
        });

        mMasukButton.setOnClickListener(v -> {
            if (isTextKosong()) {
                tampilkanDialog("Login Gagal\nEmail Dan Password Tidak Boleh Kosong");
                return;
            }
            String email = mEmailText.getText().toString();
            String password = mPasswordText.getText().toString();
            try {
                long idBengkel = mViewModel.isValidLogin(email, password);
                if(idBengkel > 0) {
                    LoginEvent event = new LoginEvent(LoginEvent.EVENT_PENDAFTARAN_SUKSES);
                    event.setBengkelId(idBengkel);
                    EventBus.getDefault().post(event);
                }else {
                    tampilkanDialog("Login Gagal\n Emal Dan Password Salah");
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        mLewatiButton.setOnClickListener(v -> {
            EventBus.getDefault().post(new LoginEvent(LoginEvent.EVENT_PENDAFTARAN_SUKSES));
        });
        return view;
    }

    private void tampilkanDialog(String pesan) {
        PesanDialog.newInstance(pesan)
                .show(getChildFragmentManager(), PesanDialog.TAG_PESAN_DIALOG);
    }

    private boolean isTextKosong() {
        return mEmailText.getText().toString().isEmpty() || mPasswordText.getText().toString().isEmpty();
    }
}
