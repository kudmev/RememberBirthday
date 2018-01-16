package dmitrykuznetsov.rememberbirthday.common.permissions.model;

/**
 * Created by dmitry on 1/16/18.
 */

public class PermissionData {

    public final String permission;
    public final int requestCode;
    public final int requestRationaleTitleId;
    public final int requestRationaleMessageId;

    public PermissionData(String permission, int requestCode, int requestRationaleTitleId, int requestRationaleMessageId) {
        this.permission = permission;
        this.requestCode = requestCode;
        this.requestRationaleTitleId = requestRationaleTitleId;
        this.requestRationaleMessageId = requestRationaleMessageId;
    }
}
