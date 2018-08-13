package com.unbaja.inggi.bengkos.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by sigit on 28/06/2018.
 */

public class PesanDialog extends DialogFragment {


    public static String TAG_PESAN_DIALOG = "com.unbaja.inggi.bengkos.tag.pesandialog";

    public static PesanDialog newInstance(String pesan) {
        PesanDialog dialog = new PesanDialog();
        dialog.pesan = pesan;
        return dialog;
    }

    private String pesan;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(pesan);
        builder.setPositiveButton("OK", (dialog, which) -> {
           dismiss();
        });
        return builder.create();
    }
}
