package dmitrykuznetsov.rememberbirthday.features.settings;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import org.joda.time.DateTime;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.alarm.AlarmRepo;
import dmitrykuznetsov.rememberbirthday.common.receiver.interactor.AlarmInteractor;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject
    AlarmInteractor alarmInteractor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        onSharedPreferenceChanged(sharedPreferences, Constants.timePrefA_Key);
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
            case Constants.timePrefA_Key:
                connectionPref = findPreference(key);
                connectionPref.setTitle(R.string.pref_title_ringtone_time);
                String time = sharedPreferences.getString(key, getString(R.string.default_alarm_time));
                connectionPref.setSummary(time.substring(0, 5));

                alarmInteractor.updateAlarm()
                        .subscribe();
                break;
        }

    }

}
