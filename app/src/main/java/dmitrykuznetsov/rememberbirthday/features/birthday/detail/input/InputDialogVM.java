package dmitrykuznetsov.rememberbirthday.features.birthday.detail.input;

import android.databinding.ObservableField;

public class InputDialogVM {

    public ObservableField<Boolean> select = new ObservableField<Boolean>(){
        @Override
        public Boolean get() {
            return super.get();
        }

        @Override
        public void set(Boolean value) {
            super.set(value);
        }
    };

    public InputDialogVM() {

    }

}
