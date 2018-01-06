package dmitrykuznetsov.rememberbirthday.features.birthday.detail.input;

import android.support.v7.app.AlertDialog;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.databinding.InputDialogBinding;

public class InputDialog {

    private AlertDialog.Builder builder;

    public InputDialog(AlertDialog.Builder builder) {
        this.builder = builder;
    }

    public void show() {
        builder.show();
    }

    public interface OnClickDialogButton {
        void confirmDeletePerson();

        void rejectDeletePerson();
    }
}
