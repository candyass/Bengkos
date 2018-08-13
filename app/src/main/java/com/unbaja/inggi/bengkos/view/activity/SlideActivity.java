package com.unbaja.inggi.bengkos.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;
import com.unbaja.inggi.bengkos.R;

/**
 * Created by sigit on 07/07/2018.
 */

public class SlideActivity extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SliderPage sliderPage1 = new SliderPage();
        sliderPage1.setTitle(getString(R.string.slide_1_judul));
        sliderPage1.setDescription(getString(R.string.slide_1_deskripsi));
        sliderPage1.setImageDrawable(R.drawable.slide_1);
        sliderPage1.setBgColor(getResources().getColor(R.color.colorSlide1));
        addSlide(AppIntroFragment.newInstance(sliderPage1));

        SliderPage sliderPage2 = new SliderPage();
        sliderPage2.setTitle(getString(R.string.slide_2_judul));
        sliderPage2.setDescription(getString(R.string.slide_2_deskripsi));
        sliderPage2.setImageDrawable(R.drawable.slide_2);
        sliderPage2.setBgColor(getResources().getColor(R.color.colorAccent));
        addSlide(AppIntroFragment.newInstance(sliderPage2));

        SliderPage sliderPage3 = new SliderPage();
        sliderPage3.setTitle(getString(R.string.slide_3_judul));
        sliderPage3.setDescription(getString(R.string.slide_3_deskripsi));
        sliderPage3.setImageDrawable(R.drawable.slide_3);
        sliderPage3.setBgColor(getResources().getColor(R.color.colorPrimary));
        addSlide(AppIntroFragment.newInstance(sliderPage3));
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent = LoginActivity.newIntent(getBaseContext());
        startActivity(intent);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = LoginActivity.newIntent(getBaseContext());
        startActivity(intent);
        finish();
    }

}
