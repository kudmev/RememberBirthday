package dmitrykuznetsov.rememberbirthday.common.receiver.model;

/**
 * Created by dmitry on 1/14/18.
 */

public class AlarmTime {
    public final boolean isNeedPlusDay;
    public final int hour;
    public  final int minute;

    public AlarmTime(boolean isNeedPlusDay, int hour, int minute) {
        this.isNeedPlusDay = isNeedPlusDay;
        this.hour = hour;
        this.minute = minute;
    }
}
