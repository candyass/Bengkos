package com.unbaja.inggi.bengkos.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.unbaja.inggi.bengkos.R;
import com.unbaja.inggi.bengkos.model.LoginEvent;
import com.unbaja.inggi.bengkos.view.fragment.DaftarAkunFragment;
import com.unbaja.inggi.bengkos.view.fragment.LoginFragment;
import com.unbaja.inggi.bengkos.view.fragment.PendaftaranBengkelFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LoginActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.login_fragment_container);
        if(fragment == null) {
            fragment = LoginFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.login_fragment_container, fragment).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(getBaseContext());
        inflater.inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_tentang_saya:
                Intent intent = AboutActivity.newIntent(getBaseContext());
                startActivity(intent);
                return true;
            default:return false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        if(event != null) {
            if(event.getEventType() == LoginEvent.EVENT_PENDAFTARAN_AKUN) {
                changeFragment(DaftarAkunFragment.newInstance());
            }
            else if(event.getEventType() == LoginEvent.EVENT_PENDAFTARAN_BENGKEL) {
                Fragment fragment = PendaftaranBengkelFragment.newInstance(event.getEmail(),
                        event.getKataSandi(), event.getNamaBengkel(), event.getNoTelepon());
                getSupportFragmentManager().beginTransaction().replace(R.id.login_fragment_container, fragment).addToBackStack(null).commit();
            }
            else if(event.getEventType() == LoginEvent.EVENT_PENDAFTARAN_SUKSES) {
                if(event.getBengkelId()  != -1) {
                    startMainActivity(event.getBengkelId(), true);
                }else{
                    startMainActivity(event.getBengkelId(), false);
                }

            }
        }
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.login_fragment_container, fragment)
                .addToBackStack(null).commit();
    }

    private void startMainActivity(long bengkelId, boolean masukTerdaftar) {
        Intent intent = MainActivity.newIntent(getBaseContext(), bengkelId, masukTerdaftar);
        startActivity(intent);
        finish();
    }
}
