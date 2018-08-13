package com.unbaja.inggi.bengkos.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.unbaja.inggi.bengkos.R;
import com.unbaja.inggi.bengkos.database.entity.Bengkel;
import com.unbaja.inggi.bengkos.util.MyLogger;
import com.unbaja.inggi.bengkos.view.activity.DetailBengkelActivity;
import com.unbaja.inggi.bengkos.viewmodel.DaftarBengkelFragmentViewModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sigit on 26/06/2018.
 */

public class DaftarBengkelFragment extends Fragment {


    private ViewSwitcher mViewSwitcher;
    private RadioButton mRadioMobil;
    private RadioButton mRadioMotor;
    private RadioButton mRadioSemua;
    private RadioGroup mRadioGroup;
    private Button mButtonCari;
    private RecyclerView mRecyclerView;
    private Switch mSwitchFilter;
    private ConstraintLayout mConstraint;

    private List<Bengkel> mListBengkel;

    private DaftarBengkelFragmentViewModel mViewModel;


    public static Fragment newInstance() {
        Fragment fragment = new DaftarBengkelFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DaftarBengkelFragmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daftar_bengkel, container, false);
        mViewSwitcher = view.findViewById(R.id.fragment_daftar_bengkel_viewSwitcher);
        mRecyclerView = view.findViewById(R.id.fragment_daftar_bengkel_recyclerView);
        mRadioMobil = view.findViewById(R.id.pencarian_stub_rMobil);
        mRadioMotor = view.findViewById(R.id.pencarian_stub_rMotor);
        mRadioSemua = view.findViewById(R.id.pencarian_stub_rSemua);
        mButtonCari = view.findViewById(R.id.pencarian_stub_button_cari);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwitchFilter = view.findViewById(R.id.pencarian_stub_switch_filter);
        mConstraint = view.findViewById(R.id.pencarian_stub_constraint);
        mRadioGroup = view.findViewById(R.id.pencarian_stub_radioGroup);

        mViewModel.getAllBengkel().observe(this, bengkels -> {
            if(bengkels != null) {
                if(bengkels.size() > 0) {
                    tampilkanList(true);
                    mRecyclerView.setAdapter(new BengkelAdapter(bengkels));
                }
            }
        });





        mSwitchFilter.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                mConstraint.setVisibility(View.VISIBLE);
            }else {
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
            int id = mRadioGroup.getCheckedRadioButtonId();
            switch (id) {
                case R.id.pencarian_stub_rMobil:
                    mViewModel.getAllBengkel(1).observe(DaftarBengkelFragment.this, bengkels -> {
                        if(bengkels.size() > 0) {
                            tampilkanList(true);
                            mRecyclerView.setAdapter(new BengkelAdapter(bengkels));
                        }else {
                            tampilkanList(false);
                        }
                    });
                    break;
                case R.id.pencarian_stub_rMotor:
                    mViewModel.getAllBengkel(2).observe(DaftarBengkelFragment.this, bengkels -> {
                        if (bengkels.size() > 0) {
                            tampilkanList(true);
                            mRecyclerView.setAdapter(new BengkelAdapter(bengkels));
                        }else {
                            tampilkanList(false);
                        }
                    });
                    break;
                case R.id.pencarian_stub_rSemua:
                    mViewModel.getAllBengkel(3).observe(DaftarBengkelFragment.this, bengkels -> {
                        if(bengkels.size() > 0) {
                            tampilkanList(true);
                            mRecyclerView.setAdapter(new BengkelAdapter(bengkels));
                        }else {
                            tampilkanList(false);
                        }
                    });
                    break;
            }
            mRadioGroup.clearCheck();
            mButtonCari.setEnabled(false);
        });


        return view;
    }


    private void tampilkanList(boolean value) {
        if(value) {
            if (mViewSwitcher.getNextView().getId() == R.id.fragment_daftar_bengkel_recyclerView) {
                mViewSwitcher.showNext();
            }
        }else {
            if(mViewSwitcher.getNextView().getId() == R.id.fragment_daftar_bengkel_relative_layout) {
                mViewSwitcher.showNext();
            }
        }
    }


    class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    class BengkelHolder extends HeaderHolder implements View.OnClickListener {

        private CircleImageView mFoto;
        private TextView mTextNamaBengkel;
        private TextView mTextMelayani;
        private TextView mTextAlamat;
        private Bengkel mBengkel;

        public BengkelHolder(View itemView) {
            super(itemView);
            mFoto = itemView.findViewById(R.id.list_bengkel_foto);
            mTextNamaBengkel = itemView.findViewById(R.id.list_bengkel_nama_text);
            mTextMelayani = itemView.findViewById(R.id.list_bengkel_melayani_text);
            mTextAlamat = itemView.findViewById(R.id.list_bengkel_alamat_text);
            itemView.setOnClickListener(this);
        }



        public void bindItem(Bengkel bengkel) {
            mBengkel = bengkel;
            mFoto.setImageBitmap(mBengkel.getFoto());
            mTextNamaBengkel.setText(mBengkel.getNamaBengkel());
            mTextAlamat.setText(mBengkel.getLokasi().getNamaLokasi());
            long customer = mBengkel.getCustomerId();
            if (customer == 1) {
                mTextMelayani.setText("Mobil");
                mTextMelayani.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mobil_48dp, 0, 0, 0);
            }else if (customer == 2) {
                mTextMelayani.setText("Motor");
                mTextMelayani.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_motor_48dp, 0, 0, 0);
            }else {
                mTextMelayani.setText("Mobil Dan Motor");
                mTextMelayani.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_semua_48dp, 0, 0, 0);
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent = DetailBengkelActivity.newIntent(getContext(), mBengkel.getId());
            startActivity(intent);
        }
    }

    class BengkelAdapter extends RecyclerView.Adapter<HeaderHolder> {

        private List<Bengkel> list;


        public BengkelAdapter(List<Bengkel> listBengkel) {
            list = listBengkel;
        }

        @Override
        public HeaderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            if(viewType == R.id.header) {
                View v = inflater.inflate(R.layout.list_header, parent, false);
                HeaderHolder headerHolder = new HeaderHolder(v);
                return headerHolder;
            }
            View view = inflater.inflate(R.layout.list_bengkel, parent, false);
            BengkelHolder holder = new BengkelHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(HeaderHolder holder, int position) {
            if(holder instanceof BengkelHolder) {
                 BengkelHolder bHolder = (BengkelHolder) holder;
                 bHolder.bindItem(list.get(--position));
            }
        }

        @Override
        public int getItemViewType(int position) {
            if(position == 0
                    ) {
                return R.id.header;
            }
            return R.id.detail;
        }

        @Override
        public int getItemCount() {
            return 1 + list.size();
        }
    }





}
