package dmitrykuznetsov.rememberbirthday;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Dmitry Kuznetsov on 09.01.2016.
 */
public class RememberSearchResults extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static String[] from = new String[]{RememberContentProvider.NAME, RememberContentProvider.DATE_BIRTHDAY, RememberContentProvider.AGE_PERSON};
    private static int[] to = new int[]{R.id.text_name, R.id.text_date, R.id.text_age};
    private SimpleCursorAdapter adapter;
    private static String QUERY_EXTRA_KEY = "QUERY_EXTRA_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new MySimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, from, to, 0);
        setListAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);
        parseIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        parseIntent(getIntent());
    }

    private void parseIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String searchQuery = intent.getStringExtra(SearchManager.QUERY);

            Bundle args = new Bundle();
            args.putString(QUERY_EXTRA_KEY, searchQuery);

            getLoaderManager().restartLoader(0, args, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String query = "0";

        if (args != null) {
            query = args.getString(QUERY_EXTRA_KEY);
        }
        String[] from = new String[]{RememberContentProvider.UID, RememberContentProvider.NAME, RememberContentProvider.DATE_BIRTHDAY, RememberContentProvider.AGE_PERSON, RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS, RememberContentProvider.PATHIMAGE};

        String where = RememberContentProvider.NAME + " LIKE \"%" + query + "%\"";
        String sortOrder = RememberContentProvider.NAME + " COLLATE LOCALIZED ASC";

        return new CursorLoader(this, RememberContentProvider.CONTENT_URI, from, where, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
