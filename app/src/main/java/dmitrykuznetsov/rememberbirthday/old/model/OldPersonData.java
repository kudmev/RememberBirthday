package dmitrykuznetsov.rememberbirthday.old.model;

import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;

/**
 * Created by dmitry on 09.05.17.
 */

public class OldPersonData {

    public final PersonData personData;
    public final int age;

    public OldPersonData(PersonData personData, int age) {
        this.personData = personData;
        this.age = age;
    }


}
