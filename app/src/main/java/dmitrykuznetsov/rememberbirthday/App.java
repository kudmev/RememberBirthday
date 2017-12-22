package dmitrykuznetsov.rememberbirthday;

import dmitrykuznetsov.rememberbirthday.migration.MigrationFromSqLiteToRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends android.app.Application {

    private static App instance;

    public static App getAppContext() {
        return instance;
    }

    public void onCreate() {
        super.onCreate();
        instance = this;

        instance = this;
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("com.opendev.step.realm")
                .build();

        Realm.setDefaultConfiguration(config);
        MigrationFromSqLiteToRealm.migration(this);
    }
}