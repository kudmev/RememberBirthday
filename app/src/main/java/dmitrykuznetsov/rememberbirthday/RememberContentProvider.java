package dmitrykuznetsov.rememberbirthday;

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
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

public class RememberContentProvider extends ContentProvider {
    public static final Uri CONTENT_URI = Uri.parse("content://dmitrykuznetsov.rememberbirthday.provider");
    public static final String UID = "_id";
    public static final String NAME = "peoplename";
    public static final String DATE_BIRTHDAY = "date_birthday";
    public static final String DATE_BIRTHDAY_IN_SECONDS = "date_birthday_seconds";
    public static final String AGE_PERSON = "age_person";
    public static final String NOTE = "note";
    public static final String PATHIMAGE = "pathimage";
    public static final String PHONE_NUMBER = "phonenumber";

    private static final HashMap<String, String> SEARCH_PROJECTION_MAP;

    private static final int ALLROWS = 1;
    private static final int SINGLE_ROW = 2;
    private static final int SEARCH = 3;

    private static final UriMatcher uriMatcher;

    static {
        SEARCH_PROJECTION_MAP = new HashMap<>();
        SEARCH_PROJECTION_MAP.put(SearchManager.SUGGEST_COLUMN_TEXT_1, NAME + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);
        SEARCH_PROJECTION_MAP.put("_id", UID + " AS " + "_id");

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("dmitrykuznetsov.rememberbirthday.provider", "listpeople", ALLROWS);
        uriMatcher.addURI("dmitrykuznetsov.rememberbirthday.provider", "listpeople/#", SINGLE_ROW);

        uriMatcher.addURI("dmitrykuznetsov.rememberbirthday.provider", SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH);
        uriMatcher.addURI("dmitrykuznetsov.rememberbirthday.provider", SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SEARCH);
        uriMatcher.addURI("dmitrykuznetsov.rememberbirthday.provider", SearchManager.SUGGEST_URI_PATH_SHORTCUT, SEARCH);
        uriMatcher.addURI("dmitrykuznetsov.rememberbirthday.provider", SearchManager.SUGGEST_URI_PATH_SHORTCUT + "/*", SEARCH);
    }

    private FriendsBirthdayHelper mDBHelper;
    private SQLiteDatabase mDB;


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW:
                String rowID = uri.getPathSegments().get(1);
                selection = UID + "=" + rowID
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
            default:
                break;
        }

        if (selection == null)
            selection = "1";

        int deleteCount = db.delete(FriendsBirthdayHelper.TABLE_NAME, selection, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);

        return deleteCount;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ALLROWS:
                return "vnd.android.cursor.dir/vnd.dm.listpeople";
            case SINGLE_ROW:
                return "vnd.android.cursor.item/vnd.dm.listpeople";
            case SEARCH:
                return SearchManager.SUGGEST_MIME_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String nullColumnHack = null;

        long id = db.insert(FriendsBirthdayHelper.TABLE_NAME,
                nullColumnHack, values);

        if (id > 0) {
            Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(insertedId, null);

            return insertedId;
        } else {
            return null;
        }
    }

    @Override
    public boolean onCreate() {
        mDBHelper = new FriendsBirthdayHelper(getContext(), mDBHelper.DATABASE_NAME, null, mDBHelper.DATABASE_VERSION);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        String groupBy = null;
        String having = null;

        if (sortOrder == null) {
            sortOrder = RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS + " ASC";
        }


        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(FriendsBirthdayHelper.TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW:
                String rowId = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(UID + "=" + rowId);
                break;
            case SEARCH:
                queryBuilder.appendWhere(NAME + " LIKE \"%" + uri.getPathSegments().get(1) + "%\"");
                queryBuilder.setProjectionMap(SEARCH_PROJECTION_MAP);
                break;
            default:
                break;
        }

        //Cursor cursor=queryBuilder.query(db, projection, selection, selectionArgs, groupBy, having, sortOrder);
        Cursor cursor = db.query(mDBHelper.TABLE_NAME, projection, selection, selectionArgs, groupBy, having, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW:
                String rowID = uri.getPathSegments().get(1);
                selection = UID + "=" + rowID
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
            default:
                break;
        }

        int updateCount = db.update(FriendsBirthdayHelper.TABLE_NAME,
                values, selection, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);

        return updateCount;
    }

    private static class FriendsBirthdayHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "database_remember_birthday";
        private static final int DATABASE_VERSION = 9;
        public static final String TABLE_NAME = "table_name";

        public static final String CREATE_DB = "CREATE TABLE " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255), " + DATE_BIRTHDAY + " VARCHAR(255), " + DATE_BIRTHDAY_IN_SECONDS + " LONG, " + AGE_PERSON + " INTEGER, " + NOTE + " VARCHAR(255), " + PATHIMAGE + " VARCHAR(255), " + PHONE_NUMBER + " VARCHAR(255));";
        public static final String DELETE_DB = "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String UPGRADE_DB = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + PHONE_NUMBER + " VARCHAR(255);";

        public FriendsBirthdayHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("LOG_TAG", "���������� ���� ������ � " + oldVersion + " �� ������ " + newVersion + ", ������� ������ ������ ���� ������");
            //db.execSQL(DELETE_DB);
            //onCreate(db);
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
