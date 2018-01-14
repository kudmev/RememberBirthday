package dmitrykuznetsov.rememberbirthday.features.settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Dmitry Kuznetsov on 06.01.2016.
 */
public class SettingsActivity extends PreferenceActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

    }


}