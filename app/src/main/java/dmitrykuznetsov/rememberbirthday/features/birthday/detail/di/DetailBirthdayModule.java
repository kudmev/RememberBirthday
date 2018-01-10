package dmitrykuznetsov.rememberbirthday.features.birthday.detail.di;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.DetailBirthdayActivity;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.DetailBirthdayActivityVM;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.input.InputDialog;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.input.InputDialogVM;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.interactor.DetailBirthdayInteractor;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.interactor.DetailBirthdayInteractorImpl;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.scope.ActivityScope;

/**
 * Created by Alena on 26.12.2017.
 */

@Module
public class DetailBirthdayModule {

//    @Provides
//    @ActivityScope
//    InputDialogBinding provideInputDialogBinding(Context context, InputDialogVM inputDialogVM) {
//        InputDialogBinding inputDialogBinding = DataBindingUtil
//                .inflate(LayoutInflater.from(context), R.layout.input_dialog, null, false);
//        inputDialogBinding.setViewModel(inputDialogVM);
//        return inputDialogBinding;
//    }

    @Provides
    @ActivityScope
    AlertDialog.Builder provideAlertDialog(DetailBirthdayActivity activity) {
        return new AlertDialog.Builder(activity, R.style.DialogStyle)
                .setTitle(R.string.dialog_title_confirm_delete_user)
                .setMessage(R.string.dialog_message_confirm_delete_user)
                .setPositiveButton(R.string.yes, (dialog, which) -> activity.onClickConfirm())
                .setNegativeButton(R.string.cancel, null)
                .setOnCancelListener(null);
    }

    @Provides
    @ActivityScope
    InputDialogVM provideInputDialogVM() {
        return new InputDialogVM();
    }

    @Provides
    @ActivityScope
    InputDialog provideInputDialog(AlertDialog.Builder builder) {
        return new InputDialog(builder);
    }

    @Provides
    @ActivityScope
    DetailBirthdayInteractor provideDetailBirthdayInteractor(PersonRepo personRepo) {
        return new DetailBirthdayInteractorImpl(personRepo);
    }

    @Provides
    @ActivityScope
    DetailBirthdayActivityVM provideDetailBirthdayActivityVM(DetailBirthdayActivity activity,
                                                             DetailBirthdayInteractor interactor, InputDialog inputDialog) {
        Intent intent = activity.getIntent();
        if (intent != null) {
            PersonData personData = intent.getParcelableExtra(Constants.PERSON_DATA);
            return new DetailBirthdayActivityVM(activity, personData, interactor, inputDialog);
        } else {
            Toast.makeText(activity, R.string.error_person_not_found, Toast.LENGTH_LONG).show();
            activity.finish();
            return null;
        }
    }
}
