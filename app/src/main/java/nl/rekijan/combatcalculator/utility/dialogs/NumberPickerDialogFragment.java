package nl.rekijan.combatcalculator.utility.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import nl.rekijan.combatcalculator.R;

import static nl.rekijan.combatcalculator.AppConstants.DIALOG_TITLE;
import static nl.rekijan.combatcalculator.AppConstants.MAX_VALUE_KEY;
import static nl.rekijan.combatcalculator.AppConstants.NUMBER_PICKER_VALUE;

/**
 * Standard dialog for number pickers
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 30-4-2017
 */

public class NumberPickerDialogFragment extends DialogFragment {
    /**
     * The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it.
     */
    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, int value);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.number_picker_dialog_layout, null);
        builder.setView(dialogView);
        final NumberPicker numberPicker = (NumberPicker) dialogView.findViewById(R.id.number_picker_field);
        numberPicker.setMinValue(0);
        Bundle bundle = getArguments();
        numberPicker.setMaxValue(bundle.getInt(MAX_VALUE_KEY));
        numberPicker.setValue(bundle.getInt(NUMBER_PICKER_VALUE));

        builder.setTitle(bundle.getString(DIALOG_TITLE));

        builder.setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
                        listener.onDialogPositiveClick(NumberPickerDialogFragment.this, numberPicker.getValue());
                    }
                })
                .setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        listener.onDialogNegativeClick(NumberPickerDialogFragment.this);
                    }
                });
        return builder.create();
    }
}