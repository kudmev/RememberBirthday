package dmitrykuznetsov.rememberbirthday.common.support;

import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class AppSharedPreferences {

    private SharedPreferences sharedPreferences;
    private Utils utils;
    private final Map<String, Object> map = new HashMap<>();

    public AppSharedPreferences(SharedPreferences sharedPreferences, Utils utils) {
        this.sharedPreferences = sharedPreferences;
        this.utils = utils;
    }

    public Object get(String name) {
        return map.get(name);
    }

    public void set(String key, Object value) {
        map.put(key, value);
        save();
    }

    private void save() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
                editor.putString(entry.getKey(), utils.toStr(entry.getValue()));
        }
        editor.apply();
    }

    @Inject
    @SuppressWarnings("unchecked")
    public void load() {
        Map<String, Object> map = (Map<String, Object>) sharedPreferences.getAll();
        this.map.putAll(map);
    }


}
