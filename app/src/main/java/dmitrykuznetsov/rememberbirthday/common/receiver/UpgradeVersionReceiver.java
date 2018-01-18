package dmitrykuznetsov.rememberbirthday.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import dmitrykuznetsov.rememberbirthday.common.receiver.interactor.AlarmInteractor;
import dmitrykuznetsov.rememberbirthday.common.support.Config;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;

/**
 * Created by Dmitry Kuznetsov on 23.07.2015.
 * This is receiver upgrade wrong old version to new correct version
 */
public class UpgradeVersionReceiver extends BroadcastReceiver {

    public static final String ACTION_PACKAGE_REPLACED = "android.intent.action.MY_PACKAGE_REPLACED";
    public static final String UPGRADE_AGE = "upgrade_age";

    @Inject
    PersonRepo personRepo;

    @Inject
    Config config;

    @Override
    public void onReceive(Context context, Intent intent) {
        AndroidInjection.inject(this, context);
        Log.d("UpgradeVersionReceiver", "onReceive");

        boolean isUpgradeAge = config.getAsBoolean(UPGRADE_AGE);

        if (ACTION_PACKAGE_REPLACED.equals(intent.getAction())) {
            Log.d("UpgradeVersionReceiver", "isUpgradeAge false");
            personRepo.upgradePersonsAge()
                    .subscribe(this::onComplete, this::onError);
        }
    }

    private void onComplete() {
        Log.d("UpgradeVersionReceiver", "success");
        config.set(UPGRADE_AGE, true);
    }

    private void onError(Throwable throwable) {
        Log.d("UpgradeVersionReceiver", "error");
    }

}
