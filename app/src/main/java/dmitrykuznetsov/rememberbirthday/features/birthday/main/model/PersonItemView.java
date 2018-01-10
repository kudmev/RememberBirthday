package dmitrykuznetsov.rememberbirthday.features.birthday.main.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;

/**
 * Created by dmitry on 1/10/18.
 */

public class PersonItemView extends BaseObservable implements Comparable<PersonItemView>{
    private int dayOfYear;
    private PersonData personData;

    public PersonItemView(int dayOfYear, PersonData personData) {
        this.dayOfYear = dayOfYear;
        this.personData = personData;
    }

    @Bindable
    public int getDayOfYear() {
        return dayOfYear;
    }

    @Bindable
    public PersonData getPersonData() {
        return personData;
    }

    @Override
    public int compareTo(@NonNull PersonItemView personItemView) {
        return dayOfYear - personItemView.getDayOfYear();
    }
}
