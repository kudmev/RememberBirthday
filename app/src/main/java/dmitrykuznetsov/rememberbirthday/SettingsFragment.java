package dmitrykuznetsov.rememberbirthday;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String timePrefA_Key = "timePrefA_Key";
    public static final String pref_sync = "pref_sync";
    public static final String pref_vibrate = "pref_vibrate";
    public static final String pref_ringtone = "pref_ringtone";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getActionBar().setDisplayUseLogoEnabled(false);
        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        onSharedPreferenceChanged(sharedPreferences, timePrefA_Key);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference connectionPref;
        switch (key) {
            case timePrefA_Key:
                connectionPref = findPreference(key);
                connectionPref.setTitle(R.string.pref_title_ringtonetime);
                connectionPref.setSummary(sharedPreferences.getString(key, "10:00:00.000").substring(0, 5));
                break;

        }


    }

}
