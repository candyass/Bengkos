package com.unbaja.inggi.bengkos.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.unbaja.inggi.bengkos.R;
import com.unbaja.inggi.bengkos.database.entity.Bengkel;
import com.unbaja.inggi.bengkos.database.entity.Layanan;
import com.unbaja.inggi.bengkos.model.LayananEvent;
import com.unbaja.inggi.bengkos.model.QueryLayanan;
import com.unbaja.inggi.bengkos.util.MyLogger;
import com.unbaja.inggi.bengkos.view.dialog.InputLayananDialog;
import com.unbaja.inggi.bengkos.view.dialog.PesanDialog;
import com.unbaja.inggi.bengkos.viewmodel.InputLayananActivityViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class InputLayananActivity extends AppCompatActivity {

    public static final String EXTRA_BENGKEL_ID = "com.unbaja.inggi.bengkos.extra.bengkelid";
    public static final String EXTRA_EDITABLE = "com.unbaja.inggi.bengkos.extra.editable";

    public static Intent newIntent(Context context, long bengkelId, boolean editable) {
        Intent intent = new Intent(context, InputLayananActivity.class);
        intent.putExtra(EXTRA_BENGKEL_ID, bengkelId);
        intent.putExtra(EXTRA_EDITABLE, editable);
        return intent;
    }


    private ImageView mTambahPelayananPerawatan;
    private ImageView mTambahPelayananBan;
    private ImageView mTambahPelayananCuci;
    private ImageView mArrowPelayananPerawatan;
    private ImageView mArrowPelayananBan;
    private ImageView mArrowPelayananCuci;
    private RecyclerView mRecyclerViewPerawatan;
    private RecyclerView mRecyclerViewBan;
    private RecyclerView mRecyclerViewCuci;
    private Button mSimpanButton;

    private long mBengkelId;
    private boolean isEditable;
    private InputLayananActivityViewModel mViewModel;

    private LayananAdapter mLayananPerawatanAdapter;
    private LayananAdapter mLayananBanAdapter;
    private LayananAdapter mLayananCuciAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_layanan);
        initViews();
        initListeners();
        mBengkelId = getIntent().getLongExtra(EXTRA_BENGKEL_ID, -1);
        isEditable = getIntent().getBooleanExtra(EXTRA_EDITABLE, false);
        mViewModel = ViewModelProviders.of(this).get(InputLayananActivityViewModel.class);

        if(isEditable) {
            mTambahPelayananPerawatan.setVisibility(View.VISIBLE);
            mTambahPelayananBan.setVisibility(View.VISIBLE);
            mTambahPelayananCuci.setVisibility(View.VISIBLE);
            mSimpanButton.setVisibility(View.VISIBLE);
            MyLogger.logPesan("Editable : " + String.valueOf(isEditable));
        }else {
            mTambahPelayananPerawatan.setVisibility(View.INVISIBLE);
            mTambahPelayananBan.setVisibility(View.INVISIBLE);
            mTambahPelayananCuci.setVisibility(View.INVISIBLE);
            mSimpanButton.setVisibility(View.INVISIBLE);
            MyLogger.logPesan("Editable : " + String.valueOf(isEditable));
        }


        mViewModel.getAllLayananPerawatan(mBengkelId).observe(this, list -> {
            if (list != null) {
                MyLogger.logPesan("Perawatan Size : " + String.valueOf(list.size()));
                if(list.size() > 0) {
                    mLayananPerawatanAdapter = new LayananAdapter(list);
                    mRecyclerViewPerawatan.setAdapter(mLayananPerawatanAdapter);
                    tampilkanRecyclerViewPerawatan(true);
                }
            }
        });

        mViewModel.getAllLayananBanDanVelg(mBengkelId).observe(this, list -> {
            if (list != null) {
                MyLogger.logPesan("Ban Size : " + String.valueOf(list.size()));
                if(list.size() > 0) {
                    mLayananBanAdapter = new LayananAdapter(list);
                    mRecyclerViewBan.setAdapter(mLayananBanAdapter);
                    tampilkanRecyclerViewBan(true);
                }
            }
        });

        mViewModel.getAllLayananSalonCuci(mBengkelId).observe(this, list -> {
            if (list != null) {
                MyLogger.logPesan("Salon Size : " + String.valueOf(list.size()));
                if(list.size() > 0) {
                    mLayananCuciAdapter = new LayananAdapter(list);
                    mRecyclerViewCuci.setAdapter(mLayananCuciAdapter);
                    tampilkanRecyclerViewCuci(true);
                }
            }
        });


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
    public void onEvent(LayananEvent event) {
        MyLogger.logPesan("Layanan Id : " + String.valueOf(event.getmLayanan().getIdLayanan()));
        MyLogger.logPesan("Kategori Layanan : " + String.valueOf(event.getmLayanan().getKategoriId()));
        MyLogger.logPesan("Nama Layanan : " + event.getmLayanan().getNamaLayanan());
        if(event != null) {
            if(event.getmLayanan().getKategoriId() == 1) {
                if (mLayananPerawatanAdapter == null) {
                    mLayananPerawatanAdapter = new LayananAdapter();
                    mRecyclerViewPerawatan.setAdapter(mLayananPerawatanAdapter);
                }
                tampilkanRecyclerViewPerawatan(true);
                mLayananPerawatanAdapter.tambahItem(new QueryLayanan(event.getmLayanan().getIdLayanan()
                        , event.getmLayanan().getNamaLayanan(), 0));
            }else if (event.getmLayanan().getKategoriId() == 2) {
                if (mLayananBanAdapter == null) {
                    mLayananBanAdapter = new LayananAdapter();
                    mRecyclerViewBan.setAdapter(mLayananBanAdapter);
                }
                tampilkanRecyclerViewBan(true);
                mLayananBanAdapter.tambahItem(new QueryLayanan(event.getmLayanan().getIdLayanan()
                        , event.getmLayanan().getNamaLayanan(), 0));

            }else if (event.getmLayanan().getKategoriId() == 3) {
                if (mLayananCuciAdapter == null) {
                    mLayananCuciAdapter = new LayananAdapter();
                    mRecyclerViewCuci.setAdapter(mLayananCuciAdapter);
                }
                tampilkanRecyclerViewCuci(true);
                mLayananCuciAdapter.tambahItem(new QueryLayanan(event.getmLayanan().getIdLayanan()
                        , event.getmLayanan().getNamaLayanan(), 0));
            }
        }
    }

    private void initListeners() {
        mArrowPelayananPerawatan.setOnClickListener(v -> {
            tampilkanRecyclerViewPerawatan(!RecyclerViewPerawatanBuka());
            if(RecyclerViewPerawatanBuka()) {
                MyLogger.logPesan("Perawatan Buka");
            }else {
                MyLogger.logPesan("Perawatan Tutup");
            }
        });

        mArrowPelayananBan.setOnClickListener(v -> {
            tampilkanRecyclerViewBan(!RecyclerViewBanBuka());
            if (RecyclerViewBanBuka()) {
                MyLogger.logPesan("Ban Buka");
            }else {
                MyLogger.logPesan("Ban Tutup");
            }
        });

        mArrowPelayananCuci.setOnClickListener(v -> {
            tampilkanRecyclerViewCuci(!RecyclerViewCuciBuka());
            if(RecyclerViewCuciBuka()) {
                MyLogger.logPesan("Cuci Buka");
            }else {
                MyLogger.logPesan("Cuci Tutup");
            }
        });

        mTambahPelayananPerawatan.setOnClickListener(v -> {
            DialogFragment dialog = InputLayananDialog.newInstance(1);
            dialog.show(getSupportFragmentManager(), InputLayananDialog.TAN_LAYANAN_DIALOG);
        });

        mTambahPelayananBan.setOnClickListener(v -> {
            DialogFragment dialog = InputLayananDialog.newInstance(2);
            dialog.show(getSupportFragmentManager(), InputLayananDialog.TAN_LAYANAN_DIALOG);
        });

        mTambahPelayananCuci.setOnClickListener(v -> {
            DialogFragment dialogFragment = InputLayananDialog.newInstance(3);
            dialogFragment.show(getSupportFragmentManager(), InputLayananDialog.TAN_LAYANAN_DIALOG);
        });

        mSimpanButton.setOnClickListener(v -> {
            List<Bengkel.LayananBengkel> list = getAllLayananBengkel();
            if(list.size() > 0) {
                mViewModel.insertLayananBengkel(list);
                Toast.makeText(getApplicationContext(), "Layanan Disimpan", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                PesanDialog dialog = PesanDialog.newInstance("Penyimpanan Gagal !\nDaftar Layanan Kosong");
                dialog.show(getSupportFragmentManager(), PesanDialog.TAG_PESAN_DIALOG);
            }
        });
    }

    private void initViews() {
        mTambahPelayananPerawatan = findViewById(R.id.activity_input_tambah_perawatan);
        mTambahPelayananBan = findViewById(R.id.activity_input_tambah_ban);
        mTambahPelayananCuci = findViewById(R.id.activity_input_tambah_cuci);
        mArrowPelayananPerawatan = findViewById(R.id.activity_input_arrow_perawatan);
        mArrowPelayananBan = findViewById(R.id.activity_input_arrow_ban);
        mArrowPelayananCuci = findViewById(R.id.activity_input_arrow_cuci);
        mRecyclerViewPerawatan = findViewById(R.id.activity_input_perawatan_recyclerView);
        mRecyclerViewBan = findViewById(R.id.activity_input_ban_recyclerView);
        mRecyclerViewCuci = findViewById(R.id.activity_input_cuci_recyclerView);
        mSimpanButton = findViewById(R.id.activity_input_simpan_button);

        mRecyclerViewPerawatan.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mRecyclerViewBan.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mRecyclerViewCuci.setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }

    private List<Bengkel.LayananBengkel> getAllLayananBengkel() {
        List<Bengkel.LayananBengkel> list = new ArrayList<>();
        LayananAdapter mPerawatan = (LayananAdapter) mRecyclerViewPerawatan.getAdapter();
        LayananAdapter mBan = (LayananAdapter) mRecyclerViewBan.getAdapter();
        LayananAdapter mCuci = (LayananAdapter) mRecyclerViewCuci.getAdapter();

        if(mPerawatan != null) {
            if(mPerawatan.getItemCount() > 0) {
                for (QueryLayanan q : mPerawatan.mList) {
                    list.add(new Bengkel.LayananBengkel(mBengkelId, q.getIdLayanan(), q.getHarga()));
                }
            }
        }
        if(mBan != null ) {
            if (mBan.getItemCount() > 0) {
                for (QueryLayanan q : mBan.mList) {
                    list.add(new Bengkel.LayananBengkel(mBengkelId, q.getIdLayanan(), q.getHarga()));
                }
            }
        }
        if(mCuci != null ) {
            if (mCuci.getItemCount() > 0) {
                for (QueryLayanan q : mCuci.mList) {
                    list.add(new Bengkel.LayananBengkel(mBengkelId, q.getIdLayanan(), q.getHarga()));
                }
            }
        }
        return list;
    }

    private void tampilkanRecyclerViewPerawatan(boolean value) {
        if (value) {
            mRecyclerViewPerawatan.setVisibility(View.VISIBLE);
        }else {
            mRecyclerViewPerawatan.setVisibility(View.GONE);
        }
    }

    private void tampilkanRecyclerViewBan(boolean value) {
        if (value) {
            mRecyclerViewBan.setVisibility(View.VISIBLE);
        }else {
            mRecyclerViewBan.setVisibility(View.GONE);
        }
    }

    private void tampilkanRecyclerViewCuci(boolean value) {
        if (value) {
            mRecyclerViewCuci.setVisibility(View.VISIBLE);
        }else {
            mRecyclerViewCuci.setVisibility(View.GONE);
        }
    }

    private boolean RecyclerViewPerawatanBuka() {
        if(mRecyclerViewPerawatan.getVisibility() == View.VISIBLE) {
            return true;
        }
        return false;
    }

    private boolean RecyclerViewBanBuka() {
        if(mRecyclerViewBan.getVisibility() == View.VISIBLE) {
            return true;
        }
        return false;
    }

    private boolean RecyclerViewCuciBuka() {
        if(mRecyclerViewCuci.getVisibility() == View.VISIBLE) {
            return true;
        }
        return false;
    }


    class LayananHolder extends RecyclerView.ViewHolder {

        private TextView mNamaLayananText;
        private EditText mHargaText;
        private Button mSimpanButton;
        private Button mUbahButton;

        private QueryLayanan queryLayanan;

        public LayananHolder(View itemView) {
            super(itemView);
            mNamaLayananText = itemView.findViewById(R.id.list_layanan_nama_layanan);
            mHargaText = itemView.findViewById(R.id.list_layanan_harga_text);
            mSimpanButton = itemView.findViewById(R.id.list_layanan_simpan_button);
            mUbahButton = itemView.findViewById(R.id.list_layanan_ubah_button);

            mSimpanButton.setOnClickListener(v -> {
                queryLayanan.setHarga(Integer.parseInt(mHargaText.getText().toString()));
                mUbahButton.setEnabled(true);
                mHargaText.setEnabled(false);
                mSimpanButton.setEnabled(false);
            });

            mUbahButton.setOnClickListener(v -> {
                mSimpanButton.setEnabled(true);
                mHargaText.setEnabled(true);
                mUbahButton.setEnabled(false);
            });

            if (isEditable) {
                enableEdit();
            }else {
                disableEdit();
            }
        }

        public void bindItem(QueryLayanan queryLayanan) {
            this.queryLayanan = queryLayanan;
            mNamaLayananText.setText(this.queryLayanan.getNamaLayanan());
            mHargaText.setText(String.valueOf(this.queryLayanan.getHarga()));
            mHargaText.setEnabled(false);
            mSimpanButton.setEnabled(false);
            mNamaLayananText.requestFocus();
        }

        private void disableEdit() {
            mSimpanButton.setVisibility(View.GONE);
            mUbahButton.setVisibility(View.GONE);
        }

        private void enableEdit() {
            mSimpanButton.setVisibility(View.VISIBLE);
            mUbahButton.setVisibility(View.VISIBLE);
        }
    }

    class LayananAdapter extends RecyclerView.Adapter<LayananHolder> {

        private List<QueryLayanan> mList;

        public LayananAdapter() {
            mList = new ArrayList<>();
        }

        public LayananAdapter(List<QueryLayanan> list) {
            mList = list;
        }

        @Override
        public LayananHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getBaseContext());
            View v = inflater.inflate(R.layout.list_layanan, parent, false);
            LayananHolder holder = new LayananHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(LayananHolder holder, int position) {
            holder.bindItem(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public void tambahItem(QueryLayanan item) {
            mList.add(item);
            notifyItemInserted(mList.indexOf(item));
        }
    }
}
