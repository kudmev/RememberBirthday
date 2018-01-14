package dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor;

import android.content.Intent;
import android.net.Uri;

import com.theartofdev.edmodo.cropper.CropImage;

import dmitrykuznetsov.rememberbirthday.common.alarm.AlarmRepo;
import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PhoneRetriever;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    public Completable addPersonData(PersonData personData) {
        return personRepo.addPerson(personData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable updatePersonData(PersonData personData) {
        return personRepo.updatePerson(personData);
    }

    @Override
    public String getPhone(Intent data) {
        Uri uri = data.getData();
        return phoneRetriever.getPhone(uri);
    }

    @Override
    public String getPathImage(Intent data) {
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        Uri resultUri = result.getUri();
        return resultUri.getPath();
    }

}
