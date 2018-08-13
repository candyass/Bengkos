package com.unbaja.inggi.bengkos.view.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by sigit on 03/07/2018.
 */

public class KonfirmasiDialog extends DialogFragment {

    public static String TAG_KONFIRMASI_DIALOG = "com.unbaja.inggi.bengkos.tag.konfirmasidialog";

    public static DialogFragment newInstance(String pesan, DialogInterface.OnClickListener positiveAction,
                                             DialogInterface.OnClickListener negativeAction) {
        KonfirmasiDialog dialog = new KonfirmasiDialog();
        dialog.pesan = pesan;
        dialog.positiveAction = positiveAction;
        dialog.negativeAction = negativeAction;
        return dialog;
    }

    private String pesan;
    private DialogInterface.OnClickListener negativeAction;
    private DialogInterface.OnClickListener positiveAction;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder.setMessage(pesan).setNegativeButton("Batal",negativeAction)
                .setPositiveButton("OK", positiveAction).create();
    }



}
