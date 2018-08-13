package com.unbaja.inggi.bengkos.view.dialog;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.unbaja.inggi.bengkos.R;
import com.unbaja.inggi.bengkos.database.entity.Kategori;
import com.unbaja.inggi.bengkos.database.entity.Layanan;
import com.unbaja.inggi.bengkos.model.LayananEvent;
import com.unbaja.inggi.bengkos.viewmodel.InputLayananDialogViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by sigit on 01/07/2018.
 */

public class InputLayananDialog extends DialogFragment {

    private static final String ARG_KATEGORI_ID = "com.unbaja.inggi.bengkos.arg.kategoriid";
    public static final String TAN_LAYANAN_DIALOG = "com.unbaja.inggi.bengkos.tag.layanandialog";

    public static DialogFragment newInstance(long kategoriId) {
        DialogFragment dialog = new InputLayananDialog();
        Bundle arg = new Bundle();
        arg.putLong(ARG_KATEGORI_ID, kategoriId);
        dialog.setArguments(arg);
        return dialog;
    }

    private long kategoriId;
    private RecyclerView mRecyclerView;
    private InputLayananDialogViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(InputLayananDialogViewModel.class);
        kategoriId = getArguments().getLong(ARG_KATEGORI_ID);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.dialog_input_layanan, null);
        mRecyclerView = v.findViewById(R.id.dialog_input_layanan_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(kategoriId == 1) {
            mViewModel.getAllLayananPerawatanBerkala().observe(this, list -> {
                mRecyclerView.setAdapter(new LayananDialogAdapter(list));
            });
        }else if(kategoriId == 2) {
            mViewModel.getAllLayananBanDanVelg().observe(this, list -> {
                mRecyclerView.setAdapter(new LayananDialogAdapter(list));
            });
        }else if(kategoriId == 3) {
            mViewModel.getAllLayananSalonCuci().observe(this, list -> {
                mRecyclerView.setAdapter(new LayananDialogAdapter(list));
            });
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Layanan").setMessage("Pilih Daftar Layanan");
        builder.setView(v).setNegativeButton("Batal", (dialog, which) -> dismiss());
        return builder.create();
    }


    class LayananDialogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView;
        private Layanan mLayanan;

        public LayananDialogViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_layanan_layanan_text);
            itemView.setOnClickListener(this);
        }

        public void bindItem(Layanan layanan) {
            mLayanan = layanan;
            textView.setText(mLayanan.getNamaLayanan());
            loadGambarLayanan(layanan);
        }

        private void loadGambarLayanan(Layanan layanan) {
            if(layanan.getKategoriId() == 1) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_perawatan_berkala, 0, 0, 0);
            }else if(layanan.getKategoriId() == 2) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_ban_dan_velg, 0, 0, 0);
            }else if(layanan.getKategoriId() == 3) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_salon_cuci, 0, 0, 0);
            }
        }

        @Override
        public void onClick(View v) {
            EventBus.getDefault().post(new LayananEvent(mLayanan));
            dismiss();
        }
    }

    class LayananDialogAdapter extends RecyclerView.Adapter<LayananDialogViewHolder> {

        private List<Layanan> list;


        public LayananDialogAdapter(List<Layanan> list) {
            this.list = list;
        }

        @Override
        public LayananDialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View v = inflater.inflate(R.layout.item_layanan, null);
            LayananDialogViewHolder holder = new LayananDialogViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(LayananDialogViewHolder holder, int position) {
            holder.bindItem(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}
