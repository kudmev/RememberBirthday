package dmitrykuznetsov.rememberbirthday.common.data;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

public class RememberContentProvider extends ContentProvider {

    public static final String AUTHORITY = "dmitrykuznetsov.rememberbirthday.provider";
    public static final String PATH_BIRTHDAYS = "listpeople";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String UID = "_id";
    public static final String NAME = "peoplename";
    public static final String DATE_BIRTHDAY = "date_birthday";
    public static final String DATE_BIRTHDAY_IN_SECONDS = "date_birthday_seconds";
    public static final String AGE_PERSON = "age_person";
    public static final String NOTE = "note";
    public static final String PATH_IMAGE = "pathimage";
    public static final String PHONE_NUMBER = "phonenumber";

    private static final HashMap<String, String> SEARCH_PROJECTION_MAP;

    public static final int ALL_BIRTHDAYS = 1;
    public static final int SINGLE_BIRTHDAY = 2;
    public static final int SEARCH = 3;

    private static final UriMatcher uriMatcher;

    static {
        SEARCH_PROJECTION_MAP = new HashMap<>();
        SEARCH_PROJECTION_MAP.put(SearchManager.SUGGEST_COLUMN_TEXT_1, NAME + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);
        SEARCH_PROJECTION_MAP.put("_id", UID + " AS " + "_id");

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, PATH_BIRTHDAYS, ALL_BIRTHDAYS);
        uriMatcher.addURI(AUTHORITY, PATH_BIRTHDAYS + "/#", SINGLE_BIRTHDAY);

        uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH);
        uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SEARCH);
        uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_SHORTCUT, SEARCH);
        uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_SHORTCUT + "/*", SEARCH);
    }

    private FriendsBirthdayHelper mDBHelper;
//    private SQLiteDatabase mDB;


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        return db.delete(FriendsBirthdayHelper.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ALL_BIRTHDAYS:
                return "vnd.android.cursor.dir/vnd.dm.listpeople";
            case SINGLE_BIRTHDAY:
                return "vnd.android.cursor.item/vnd.dm.listpeople";
            case SEARCH:
                return SearchManager.SUGGEST_MIME_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI" + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        long id = db.insert(FriendsBirthdayHelper.TABLE_NAME,
                null, values);
        Uri insertedId = null;
        if (id > 0) {
            insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
        }
        return insertedId;
    }

    @Override
    public boolean onCreate() {
        mDBHelper = new FriendsBirthdayHelper(getContext(), FriendsBirthdayHelper.DATABASE_NAME, null, FriendsBirthdayHelper.DATABASE_VERSION);
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        if (sortOrder == null) {
            sortOrder = RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS + " ASC";
        }

        if (selection != null) {
            if (selection.equals("")) {
                selection = null;
            }
        }
        return db.query(FriendsBirthdayHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case SINGLE_BIRTHDAY:
                String rowID = uri.getLastPathSegment();
                selection = UID + "=" + rowID
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
            default:
                break;
        }

        return db.update(FriendsBirthdayHelper.TABLE_NAME,
                values, selection, selectionArgs);
    }

    private static class FriendsBirthdayHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "database_remember_birthday";
        private static final int DATABASE_VERSION = 9;
        private static final String TABLE_NAME = "table_name";

        private static final String CREATE_DB = "CREATE TABLE " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255), " + DATE_BIRTHDAY + " VARCHAR(255), " + DATE_BIRTHDAY_IN_SECONDS + " LONG, " + AGE_PERSON + " INTEGER, " + NOTE + " VARCHAR(255), " + PATH_IMAGE + " VARCHAR(255), " + PHONE_NUMBER + " VARCHAR(255));";
        private static final String UPGRADE_DB = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + PHONE_NUMBER + " VARCHAR(255);";

        public FriendsBirthdayHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion == 8 && newVersion == 9) {
                db.beginTransaction();
                try {
                    db.execSQL(UPGRADE_DB);
                    db.setTransactionSuccessful();
                    Log.d("setTransactional", "Successful");
                } finally {
                    db.endTransaction();
                }

            }
        }
    }
}
