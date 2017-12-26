package dmitrykuznetsov.rememberbirthday.common.support;

import com.google.gson.Gson;

import javax.inject.Inject;


public class Config {

    private AppSharedPreferences saver;
    private Gson gson;
    private Convert convert;
    private Utils utils;

    @Inject
    public Config(AppSharedPreferences saver, Gson gson, Convert convert, Utils utils) {
        this.saver = saver;
        this.gson = gson;
        this.convert = convert;
        this.utils = utils;
        saver.load();
    }

    public String getAsString(String name) {
        return utils.toStr(saver.get(name));
    }

    public Object get(String name) {
        return saver.get(name);
    }

    public <T> T getAsJson(String name, Class<T> clazz) {
        String s = (String) get(name);
        T t = null;
        if (s != null) {
            t = gson.fromJson(s, clazz);
        }
        return t;
    }

    public void set(String key, Object value) {
        saver.set(key, value);
    }

    public void setAsJson(String key, Object value) {
        String jsonValue = gson.toJson(value);
        saver.set(key, jsonValue);
    }

    public long getAsLong(String key) {
        Object obj = get(key);
        long value = convert.toLong(obj);
        return value;
    }

    public double getAsDouble(String key) {
        Object obj = get(key);
        double value = convert.toDouble(obj);
        return value;
    }

    public int getAsInt(String key) {
        Object obj = get(key);
        int value = convert.toInt(obj);
        return value;
    }

    public boolean getAsBoolean(String key) {
        Object obj = get(key);
        boolean value = convert.toBoolean(obj);
        return value;
    }
}

