package dmitrykuznetsov.rememberbirthday.common.support;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import dmitrykuznetsov.rememberbirthday.App;

public class MySharedPreferences {

    private final Map<String, Object> map = new HashMap<>();
    private final String fileName;

    public MySharedPreferences(String fileName) {
        this.fileName = fileName;
    }

    public Object get(String name) {
        return map.get(name);
    }

    public void set(String key, Object value) {
        map.put(key, value);
    }

    public void save(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                editor.putString(entry.getKey(), Utils.toStr(entry.getValue()));
            } else {
                editor.remove(entry.getKey());
            }
        }
        editor.apply();
    }

    public void load() {
        SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Map<String, Object> map = (Map<String, Object>) sharedPreferences.getAll();
        this.map.putAll(map);
    }

}
