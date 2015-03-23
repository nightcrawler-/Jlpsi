package kiki.com.jlpsi.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import kiki.com.jlpsi.services.ActionIntentService;
import kiki.com.jlpsi.utils.DbHelper;

public class ConfirmationFragment extends DialogFragment {

    public ConfirmationFragment() {
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete");
        builder.setMessage("Proceed to delete " + getArguments().getString(DbHelper.C_NAME) + "?")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ActionIntentService.startActionDeleteStudent(getActivity(), getArguments().getString(DbHelper.C_ID));
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
