package dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor;

import android.net.Uri;

import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.data.model.Person;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PhoneRetriever;

/**
 * Created by dmitry on 25.05.17.
 */

public class AddPersonInteractorImpl implements AddPersonInteractor {

    private PersonRepo personRepo;
    private PhoneRetriever phoneRetriever;

    public AddPersonInteractorImpl(PersonRepo personRepo, PhoneRetriever phoneRetriever) {
        this.personRepo = personRepo;
        this.phoneRetriever = phoneRetriever;
    }

    @Override
    public void addPersonData(Person p) {
        int id = personRepo.getPersonNextId();
        PersonData personData = new PersonData(id, p.name.get(), p.note.get(), p.bindPhone.get(), p.pathImage.get(), p.dateInMillis.get(), null);
        personRepo.addPerson(personData);
    }

    @Override
    public String getPhone(Uri uri) {
        return phoneRetriever.getPhone(uri);
    }
}
