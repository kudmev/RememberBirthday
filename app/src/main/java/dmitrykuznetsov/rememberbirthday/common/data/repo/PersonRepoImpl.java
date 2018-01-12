package dmitrykuznetsov.rememberbirthday.common.data.repo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.data.model.SimplePerson;
import dmitrykuznetsov.rememberbirthday.old.RememberContentProvider;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
//import io.realm.Realm;
//import io.realm.RealmResults;
//import io.realm.Sort;

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
        searchText = "%" + searchText + "%";
        String where = RememberContentProvider.NAME + " LIKE ?";
        String[] whereArgs = new String[]{searchText};

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
    public Observable<SimplePerson> addPerson(PersonData personData) {
        ContentValues cv = new ContentValues();
        cv.put(RememberContentProvider.NAME, personData.getName());
        cv.put(RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS, personData.getDateInMillis());
        cv.put(RememberContentProvider.NOTE, personData.getNote());
        cv.put(RememberContentProvider.PATHIMAGE, personData.getPathImage());
        cv.put(RememberContentProvider.PHONE_NUMBER, personData.getBindPhone());
        Uri uri = contentResolver.insert(RememberContentProvider.CONTENT_URI, cv);

        int personId = 0;
        switch (uriMatcher.match(uri)) {
            case RememberContentProvider.SINGLE_ROW:
                if (uri != null) {
                    String rowID = uri.getLastPathSegment();
                    personId = Integer.parseInt(rowID);
                }
                break;
        }
        SimplePerson simplePerson = new SimplePerson(personId, personData.getDateInMillis());
        return Observable.just(simplePerson);
    }

    @Override
    public void updatePerson(PersonData personData) {
        ContentValues cv = new ContentValues();
//        cv.put(RememberContentProvider.UID, personData.getId());
        cv.put(RememberContentProvider.NAME, personData.getName());
        cv.put(RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS, personData.getDateInMillis());
        cv.put(RememberContentProvider.NOTE, personData.getNote());
        cv.put(RememberContentProvider.PATHIMAGE, personData.getPathImage());
        cv.put(RememberContentProvider.PHONE_NUMBER, personData.getBindPhone());

        String where = RememberContentProvider.UID + " = " + personData.getId();
        contentResolver.update(RememberContentProvider.CONTENT_URI, cv, where, null);
    }

    @Override
    public int getLastPersonId() {
        String[] projection = {RememberContentProvider.UID};
        String sortOrder = RememberContentProvider.UID + " DESC";
        Cursor c = contentResolver.query(RememberContentProvider.CONTENT_URI, projection, null, null, sortOrder);
        int lastId = 0;
        if (c != null && c.getCount() != 0) {
            c.moveToFirst();
            lastId = c.getInt(c.getColumnIndex(RememberContentProvider.UID));
            c.close();
        }
        return lastId;

    }

    @Override
    public Observable<Boolean> deletePerson(int personId) {
        return Observable.just(personId)
                .flatMap(this::deleteAndFilter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Boolean> deleteAndFilter(Integer id) {
        String where = RememberContentProvider.UID + "=" + id;
        int numberRows = contentResolver.delete(RememberContentProvider.CONTENT_URI, where, null);
        boolean result = numberRows > 0;
        return Observable.just(result);
    }

    @Override
    public Observable<PersonData> getPerson(int personId) {
        return Observable.just(personId)
                .flatMap(this::queryPerson)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<PersonData> queryPerson(Integer personId) {
        final String where = RememberContentProvider.UID + "=" + personId;
        Cursor c = contentResolver.query(RememberContentProvider.CONTENT_URI, null, where, null, null);
        if (c != null) {
            c.moveToFirst();
            String name = c.getString(c.getColumnIndex(RememberContentProvider.NAME));
            String note = c.getString(c.getColumnIndex(RememberContentProvider.NOTE));
            String pathImage = c.getString(c.getColumnIndex(RememberContentProvider.PATHIMAGE));
            long dateInMillis = c.getLong(c.getColumnIndex(RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS));
            String bindPhone = c.getString(c.getColumnIndex(RememberContentProvider.PHONE_NUMBER));
            PersonData personData = new PersonData(personId, name, note, bindPhone, pathImage, dateInMillis/*, null*/);
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
        String pathImage = c.getString(c.getColumnIndex(RememberContentProvider.PATHIMAGE));
        long dateInMillis = c.getLong(c.getColumnIndex(RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS));
        String bindPhone = c.getString(c.getColumnIndex(RememberContentProvider.PHONE_NUMBER));
        PersonData personData = new PersonData(id, name, note, bindPhone, pathImage, dateInMillis/*, null*/);
        return personData;
    }
}
