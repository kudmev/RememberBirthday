package dmitrykuznetsov.rememberbirthday.common.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import dmitrykuznetsov.rememberbirthday.BR;

/**
 * Created by dmitry on 11.03.17.
 */

public class PersonObservable extends BaseObservable {
    public int id;
    public String name;
    public String note;
    public String bindPhone;
    public String pathImage;
    public long dateInMillis;

    public PersonObservable() {
        this.id = 0;
        this.name = "";
        this.note = "";
        this.bindPhone = "";
        this.pathImage = "";
        this.dateInMillis = 0;
    }

    public PersonObservable(PersonData personData) {
        this.id = personData.getId();
        this.name = personData.getName();
        this.note = personData.getNote();
        this.bindPhone = personData.getBindPhone();
        this.pathImage = personData.getPathImage();
        this.dateInMillis = personData.getDateInMillis();
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
}
