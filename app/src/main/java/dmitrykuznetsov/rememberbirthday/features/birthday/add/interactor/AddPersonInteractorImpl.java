package dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor;

import android.net.Uri;

import dmitrykuznetsov.rememberbirthday.data.PersonData;
import dmitrykuznetsov.rememberbirthday.model.Person;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.repo.AddPersonRepo;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.repo.AddPersonRepoImpl;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.repo.LastPersonIdRepo;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.repo.LastPersonIdRepoImpl;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.repo.PhoneRetriever;

/**
 * Created by dmitry on 25.05.17.
 */

public class AddPersonInteractorImpl implements AddPersonInteractor {

    private LastPersonIdRepo lastPersonIdRepo;
    private AddPersonRepo addPersonRepo;
    private PhoneRetriever phoneRetriever;

    public AddPersonInteractorImpl(LastPersonIdRepo lastPersonIdRepo, AddPersonRepo addPersonRepo, PhoneRetriever phoneRetriever) {
        this.lastPersonIdRepo = lastPersonIdRepo;
        this.addPersonRepo = addPersonRepo;
        this.phoneRetriever = phoneRetriever;
    }

    @Override
    public void addPersonData(Person p) {
        int id = lastPersonIdRepo.getNextId();
        PersonData personData = new PersonData(id, p.name.get(), p.note.get(), p.bindPhone.get(), p.pathImage.get(), p.dateInMillis.get(), null);
        addPersonRepo.addPerson(personData);
    }

    @Override
    public String getPhone(Uri uri) {
        return phoneRetriever.getPhone(uri);
    }
}
