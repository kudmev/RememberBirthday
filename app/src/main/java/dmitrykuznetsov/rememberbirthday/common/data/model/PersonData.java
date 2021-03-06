package dmitrykuznetsov.rememberbirthday.common.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import dmitrykuznetsov.rememberbirthday.BR;

/**
 * Created by dmitry on 09.05.17.
 */

public class PersonData extends BaseObservable implements Parcelable /*extends RealmObject*/ {

    private int id;
    private String name;
    private String note;
    private String bindPhone;
    private String pathImage;
    private long dateInMillis;

    public PersonData() {
    }

    public PersonData(int id, String name, String note, String bindPhone, String pathImage, long dateInMillis/*, RealmList<ReminderData> reminders*/) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.bindPhone = bindPhone;
        this.pathImage = pathImage;
        this.dateInMillis = dateInMillis;
    }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
        notifyPropertyChanged(BR.note);
    }

    @Bindable
    public String getBindPhone() {
        return bindPhone;
    }

    public void setBindPhone(String bindPhone) {
        this.bindPhone = bindPhone;
        notifyPropertyChanged(BR.bindPhone);
    }

    @Bindable
    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
        notifyPropertyChanged(BR.pathImage);
    }

    @Bindable
    public long getDateInMillis() {
        return dateInMillis;
    }

    public void setDateInMillis(long dateInMillis) {
        this.dateInMillis = dateInMillis;
        notifyPropertyChanged(BR.dateInMillis);
    }

    @Override
    public String toString() {
        return "PersonData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", bindPhone='" + bindPhone + '\'' +
                ", pathImage='" + pathImage + '\'' +
                ", dateInMillis=" + dateInMillis +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.note);
        dest.writeString(this.bindPhone);
        dest.writeString(this.pathImage);
        dest.writeLong(this.dateInMillis);
    }

    protected PersonData(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.note = in.readString();
        this.bindPhone = in.readString();
        this.pathImage = in.readString();
        this.dateInMillis = in.readLong();
    }

    public static final Parcelable.Creator<PersonData> CREATOR = new Parcelable.Creator<PersonData>() {
        @Override
        public PersonData createFromParcel(Parcel source) {
            return new PersonData(source);
        }

        @Override
        public PersonData[] newArray(int size) {
            return new PersonData[size];
        }
    };

    public void setPersonData(PersonData personData) {
        setId(personData.id);
        setName(personData.name);
        setNote(personData.note);
        setBindPhone(personData.bindPhone);
        setPathImage(personData.pathImage);
        setDateInMillis(personData.dateInMillis);
    }
}
