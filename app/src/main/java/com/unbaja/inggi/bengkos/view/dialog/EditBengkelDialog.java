package com.unbaja.inggi.bengkos.view.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.unbaja.inggi.bengkos.R;
import com.unbaja.inggi.bengkos.model.EditBengkelEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by sigit on 11/08/2018.
 */

public class EditBengkelDialog extends DialogFragment {

    public static final String DIALOG_TAG = "com.unbaja.bengkos.dialog.editbengkeldialog";
    private static final String ARG_JENIS_DIALOG = "com.unbaja.bengkos.arg.editbengkeldialog";

    private TextView mTextJudul;
    private TextView mTextDeskripsi;
    private Button mButtonBatal;
    private Button mButtonSimpan;
    private Spinner mSpinner1;
    private Spinner mSpinner2;

    private String mJenis;


    public static DialogFragment createEditHariDialog() {
        DialogFragment dialog = new EditBengkelDialog();
        Bundle arg = new Bundle();
        arg.putString(ARG_JENIS_DIALOG, "Hari");
        dialog.setArguments(arg);
        return dialog;
    }

    public static DialogFragment createEditJamDialog() {
        DialogFragment dialog = new EditBengkelDialog();
        Bundle arg = new Bundle();
        arg.putString(ARG_JENIS_DIALOG, "Jam");
        dialog.setArguments(arg);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_edit_bengkel, container, false);

        mTextJudul = view.findViewById(R.id.edit_dialog_text_judul);
        mTextDeskripsi = view.findViewById(R.id.edit_dialog_text_deskripsi);
        mSpinner1 = view.findViewById(R.id.edit_dialog_spinner1);
        mSpinner2 = view.findViewById(R.id.edit_dialog_spinner2);
        mButtonBatal = view.findViewById(R.id.edit_dialog_button_batal);
        mButtonSimpan = view.findViewById(R.id.edit_dialog_button_simpan);

        mJenis = getArguments().getString(ARG_JENIS_DIALOG);
        if (mJenis.equalsIgnoreCase("Hari")) {
            String[] strings = getResources().getStringArray(R.array.list_hari);
            mSpinner1.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, strings));
            mSpinner2.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, strings));

            mTextJudul.setText("Edit Hari");
            mTextDeskripsi.setText("Pilih Hari Buka Dan Hari Tutup Untuk Dirubah");

        } else if (mJenis.equalsIgnoreCase("Jam")) {
            String[] strings = getResources().getStringArray(R.array.list_jam);
            mSpinner1.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, strings));
            mSpinner2.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, strings));

            mTextJudul.setText("Edit Jam");
            mTextDeskripsi.setText("Pilih Jam Buka Dan Jam Tutup Untuk Dirubah");
        }


        mSpinner1.setSelection(AdapterView.INVALID_POSITION);
        mSpinner2.setSelection(AdapterView.INVALID_POSITION);


        mButtonBatal.setOnClickListener(v -> {
            dismiss();
        });

        mButtonSimpan.setOnClickListener(v -> {
            String text1 = (String) mSpinner1.getSelectedItem();
            String text2 = (String) mSpinner2.getSelectedItem();
            EditBengkelEvent event = null;
            if(mJenis.equalsIgnoreCase("Hari")) {
                event = EditBengkelEvent.createEventHari(text1, text2);
            } else if (mJenis.equalsIgnoreCase("Jam")) {
                event = EditBengkelEvent.createEventJam(text1,text2);
            }
            EventBus.getDefault().post(event);
            dismiss();
        });

        return view;
    }
}
