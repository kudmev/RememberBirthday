package dmitrykuznetsov.rememberbirthday.data;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by dmitry on 09.05.17.
 */

public class PersonData extends RealmObject {

    public static final String DATE = "dateInMillis";
    public static final String ID = "id";

    private int id;
    private String name;
    private String note;
    private String bindPhone;
    private String pathImage;
    private long dateInMillis;
    private RealmList<ReminderData> reminders;

    public PersonData() {
    }

    public PersonData(int id, String name, String note, String bindPhone, String pathImage, long dateInMillis, RealmList<ReminderData> reminders) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.bindPhone = bindPhone;
        this.pathImage = pathImage;
        this.dateInMillis = dateInMillis;
        this.reminders = reminders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getBindPhone() {
        return bindPhone;
    }

    public void setBindPhone(String bindPhone) {
        this.bindPhone = bindPhone;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public long getDateInMillis() {
        return dateInMillis;
    }

    public void setDateInMillis(long dateInMillis) {
        this.dateInMillis = dateInMillis;
    }

    public RealmList<ReminderData> getReminders() {
        return reminders;
    }

    public void setReminders(RealmList<ReminderData> reminders) {
        this.reminders = reminders;
    }
}
