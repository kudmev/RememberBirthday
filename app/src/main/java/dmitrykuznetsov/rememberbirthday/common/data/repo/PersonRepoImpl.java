package dmitrykuznetsov.rememberbirthday.common.data.repo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.util.Log;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import dmitrykuznetsov.rememberbirthday.common.data.RememberContentProvider;
import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.old.model.OldPersonData;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dmitry on 14.05.17.
 */
public class PersonRepoImpl implements PersonRepo {

    private ContentResolver contentResolver;
    private UriMatcher uriMatcher;

    public PersonRepoImpl(ContentResolver contentResolver, UriMatcher uriMatcher) {
        this.contentResolver = contentResolver;
        this.uriMatcher = uriMatcher;
    }

    @Override
    public Observable<List<PersonData>> getPersons(String searchText) {

        String where = null;
        String[] whereArgs = null;
        if (searchText != null) {
            searchText = "%" + searchText + "%";
            where = RememberContentProvider.NAME + " LIKE ?";
            whereArgs = new String[]{searchText};
        }

        List<PersonData> persons = new ArrayList<>();
        Cursor c = contentResolver.query(RememberContentProvider.CONTENT_URI, null, where, whereArgs, null);
        if (c != null) {
            while (c.moveToNext()) {
                PersonData personData = readColumnInDatabase(c);
                persons.add(personData);
            }
            c.close();
        }
        return Observable.just(persons)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable addPerson(PersonData personData) {
        ContentValues cv = new ContentValues();
        cv.put(RememberContentProvider.NAME, personData.getName());
        cv.put(RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS, personData.getDateInMillis());
        cv.put(RememberContentProvider.NOTE, personData.getNote());
        cv.put(RememberContentProvider.PATH_IMAGE, personData.getPathImage());
        cv.put(RememberContentProvider.PHONE_NUMBER, personData.getBindPhone());
        contentResolver.insert(RememberContentProvider.CONTENT_URI, cv);
        return Completable.complete();
    }

    @Override
    public Observable<Integer> updatePerson(PersonData personData) {
        int index = updateById(personData);
        return Observable.just(index);
    }

    private int updateById(PersonData personData) {
        ContentValues cv = new ContentValues();
        cv.put(RememberContentProvider.NAME, personData.getName());
        cv.put(RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS, personData.getDateInMillis());
        cv.put(RememberContentProvider.NOTE, personData.getNote());
        cv.put(RememberContentProvider.PATH_IMAGE, personData.getPathImage());
        cv.put(RememberContentProvider.PHONE_NUMBER, personData.getBindPhone());

        String where = RememberContentProvider.UID + " = " + personData.getId();
        return contentResolver.update(RememberContentProvider.CONTENT_URI, cv, where, null);
    }

    @Override
    public Completable deletePerson(int personId) {
        return Completable.fromObservable(Observable.just(personId)
                .flatMap(id -> (deleteById(id))));
    }

    private Observable<Integer> deleteById(Integer id) {
        String where = RememberContentProvider.UID + "=" + id;
        int numberRows = contentResolver.delete(RememberContentProvider.CONTENT_URI, where, null);
        if (numberRows > 0) {
            return Observable.just(numberRows);
        } else {
            return Observable.error(new NoSuchElementException());
        }
    }

    @Override
    public Observable<PersonData> getPerson(int personId) {
        return Observable.just(personId)
                .flatMap(this::queryPerson);
    }

    private Observable<PersonData> queryPerson(Integer personId) {
        final String where = RememberContentProvider.UID + "=" + personId;
        Cursor c = contentResolver.query(RememberContentProvider.CONTENT_URI, null, where, null, null);
        if (c != null) {
            c.moveToFirst();
            PersonData personData = readColumnInDatabase(c);
            c.close();
            return Observable.just(personData);
        } else {
            return Observable.error(new Exception());
        }
    }

    private PersonData readColumnInDatabase(Cursor c) {
        int id = c.getInt(c.getColumnIndex(RememberContentProvider.UID));
        String name = c.getString(c.getColumnIndex(RememberContentProvider.NAME));
        String note = c.getString(c.getColumnIndex(RememberContentProvider.NOTE));
        String pathImage = c.getString(c.getColumnIndex(RememberContentProvider.PATH_IMAGE));
        long dateInMillis = c.getLong(c.getColumnIndex(RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS));
        String bindPhone = c.getString(c.getColumnIndex(RememberContentProvider.PHONE_NUMBER));
        return new PersonData(id, name, note, bindPhone, pathImage, dateInMillis);
    }

    @Override
    public Completable upgradePersonsAge() {
        return getOldPersons()
                .flatMap(this::rewriteDateInMillisByAge)
                .flatMap(this::saveData)
                .ignoreElements()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<List<OldPersonData>> getOldPersons() {
        List<OldPersonData> oldPersons = new ArrayList<>();
        Cursor c = contentResolver.query(RememberContentProvider.CONTENT_URI, null, null, null, null);
        if (c != null) {
            while (c.moveToNext()) {
                PersonData personData = readColumnInDatabase(c);
                int age = c.getInt(c.getColumnIndex(RememberContentProvider.AGE_PERSON));
                OldPersonData oldPersonData = new OldPersonData(personData, age);
                oldPersons.add(oldPersonData);
            }
            c.close();
        }
        return Observable.just(oldPersons);
    }

    private Observable<List<OldPersonData>> rewriteDateInMillisByAge(List<OldPersonData> persons) {
//        List<OldPersonData> convert = new ArrayList<>();
        for (OldPersonData oldPersonData : persons) {
            PersonData personData = oldPersonData.personData;
            DateTime dateTime = new DateTime(personData.getDateInMillis());
            dateTime.minusYears(oldPersonData.age);
            personData.setDateInMillis(dateTime.getMillis());
        }
        return Observable.just(persons);
    }

    private Observable<Integer> saveData(List<OldPersonData> list) {
        int count = 0;
        for (OldPersonData oldPersonData: list) {
            PersonData personData = oldPersonData.personData;
            int index = updateById(personData);
            count = count + index;
        }
        Log.d("PersonRepoImpl: ", "count rewrite person: " + count);
        return Observable.just(count);
    }
}
