package dmitrykuznetsov.rememberbirthday.data;

import io.realm.RealmObject;

/**
 * Created by dmitry on 09.05.17.
 */

public class ReminderData extends RealmObject {
    private int id;
    private long time;

    public ReminderData() {
    }

    public ReminderData(int id, long time) {
        this.id = id;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
