package dmitrykuznetsov.rememberbirthday.common.support;

import android.content.Context;

import com.google.gson.Gson;

public class Config {
    public static final String CONFIG_PREFERENCE_FILENAME = "config";
    private static final MySharedPreferences saver = new MySharedPreferences(CONFIG_PREFERENCE_FILENAME);

    private static Config instance;
    public static final Gson GSON = new Gson();

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public static String getAsString(String name) {
        return Utils.toStr(saver.get(name));
    }

    public static void load() {
        saver.load();
    }

    public static void save(Context context) {
        saver.save(context);
    }

    public static Object get(String name) {
        return saver.get(name);
    }

    public static <T> T getAsJson(String name, Class<T> clazz) {
        String s = (String) get(name);
        T t = null;
        if (s != null) {
            t = Config.GSON.fromJson(s, clazz);
        }
        return t;
    }

    public static void set(String key, Object value) {
        saver.set(key, value);
    }

    public static void setAsJson(String key, Object value) {
        String jsonValue = Config.GSON.toJson(value);
        saver.set(key, jsonValue);
    }


}

