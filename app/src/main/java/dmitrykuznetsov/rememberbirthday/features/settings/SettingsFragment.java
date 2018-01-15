package dmitrykuznetsov.rememberbirthday.features.settings;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;

import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.alarm.AlarmRepo;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String timePrefA_Key = "timePrefA_Key";
    public static final String pref_sync = "pref_sync";
    public static final String pref_vibrate = "pref_vibrate";
    public static final String pref_ringtone = "pref_ringtone";

    @Inject
    AlarmRepo alarmRepo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
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
                connectionPref.setTitle(R.string.pref_title_ringtone_time);
                String time = sharedPreferences.getString(key, getString(R.string.default_alarm_time));

                int hour = Integer.parseInt(time.substring(0, 2));
                int minute = Integer.parseInt(time.substring(3, 5));
                connectionPref.setSummary(time.substring(0, 5));

                alarmRepo.setAlarmTime(true, hour, minute)
                        .subscribe();
                break;
        }

    }

}
