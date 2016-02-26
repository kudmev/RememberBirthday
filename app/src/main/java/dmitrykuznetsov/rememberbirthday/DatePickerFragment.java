package dmitrykuznetsov.rememberbirthday;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ִלטענטי on 08.06.2015.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private DateDialogListener dateDialogListener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //long millis=savedInstanceState.getLong("millis");
        Calendar c = Calendar.getInstance();

        if (getArguments()!=null) {
            Long millis=(Long)getArguments().getLong("millis");
            c.setTimeInMillis(millis);
        }

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        dialog.getDatePicker().setMaxDate(new Date().getTime());
        //dialog.getDatePicker().se


        return dialog;
        // Create a new instance of DatePickerDialog and return it

    }

    public interface DateDialogListener {
        void onSetDate(Calendar calendar);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getActivity().getIntent().getBundleExtra("position");
        if (activity!=null)
            dateDialogListener = (DateDialogListener) activity;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        dateDialogListener.onSetDate(calendar);
    }
}