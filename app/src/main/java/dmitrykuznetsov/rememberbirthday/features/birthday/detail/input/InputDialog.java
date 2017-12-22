package dmitrykuznetsov.rememberbirthday.features.birthday.detail.input;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.databinding.InputDialogBinding;

public class InputDialog {

    public static void show(Context context, ObservableField<Boolean> select, int resTitle, int resMessage, int resPositiveButton, int resNegativeButton) {

        InputDialogBinding inputDialogBinding = DataBindingUtil
                .inflate(LayoutInflater.from(context), R.layout.input_dialog, null, false);
        inputDialogBinding.setViewModel(new InputDialogVM(select));

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Dialog_Alert);
        builder.setTitle(resTitle);
        builder.setMessage(resMessage);
        builder.setPositiveButton(resPositiveButton, (dialog, which) -> select.set(true));
        builder.setNegativeButton(resNegativeButton, (dialog, which) -> select.set(false));
        builder.setOnCancelListener(dialog -> select.set(false));

        builder.show();
    }
}
