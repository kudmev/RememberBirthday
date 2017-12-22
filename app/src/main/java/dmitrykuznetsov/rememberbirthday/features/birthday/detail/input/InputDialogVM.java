package dmitrykuznetsov.rememberbirthday.features.birthday.detail.input;

import android.databinding.ObservableField;

public class InputDialogVM {

    public ObservableField<Boolean> select;

    public InputDialogVM(ObservableField<Boolean> select) {
        this.select = select;
    }

}
