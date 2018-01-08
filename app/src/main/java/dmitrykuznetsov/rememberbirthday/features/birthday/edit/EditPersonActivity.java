package dmitrykuznetsov.rememberbirthday.features.birthday.edit;

import android.app.Activity;
import android.content.Intent;

import javax.inject.Inject;

import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.AddPersonActivity;

/**
 * Created by dmitry on 18.03.17.
 */

public class EditPersonActivity extends AddPersonActivity {

    @Inject
    EditPersonActivityVM editPersonActivityVM;

    public static void open(Activity activity, PersonData personData, int resultCode) {
        Intent intent = new Intent(activity, EditPersonActivity.class);
        intent.putExtra(Constants.PERSON_DATA, personData);
        activity.startActivityForResult(intent, resultCode);
    }

    @Override
    public EditPersonActivityVM onCreate() {
        return editPersonActivityVM;
    }


}