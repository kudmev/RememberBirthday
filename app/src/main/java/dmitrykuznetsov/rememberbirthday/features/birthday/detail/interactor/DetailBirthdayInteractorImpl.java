package dmitrykuznetsov.rememberbirthday.features.birthday.detail.interactor;

import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DetailBirthdayInteractorImpl implements DetailBirthdayInteractor {

    private PersonRepo personRepo;

    public DetailBirthdayInteractorImpl(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public Completable deletePerson(int personId) {
        return personRepo.deletePerson(personId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<PersonData> getPerson(int personId) {
        return personRepo.getPerson(personId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
