package dmitrykuznetsov.rememberbirthday.common.data.model;

/**
 * Created by dmitry on 1/12/18.
 */

public class SimplePerson {
    public final int id;
    public final long millis;

    public SimplePerson(int id, long millis) {
        this.id = id;
        this.millis = millis;
    }
}
