package dmitrykuznetsov.rememberbirthday.common.permissions;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dmitrykuznetsov.rememberbirthday.common.permissions.model.PermissionData;

public class PermissionsStorage {

    public static final int PERMISSION_REQUEST_READ_CONTACTS = 1;
    public static final int PERMISSION_REQUEST_WRITE_STORAGE = 2;
    public static final int PERMISSION_REQUEST_CONTACTS_AND_STORAGE = 3;

    private Context context;
    private Map<String, PermissionData> permissionMap;

    public PermissionsStorage(Context context, Map<String, PermissionData> permissionMap) {
        this.context = context;
        this.permissionMap = permissionMap;
    }

    public PermissionData getPermissionData(String permission) {
        return permissionMap.get(permission);
    }

    public String[] getPermissions() {
        List<String> permissions = new ArrayList<>();
        for (Map.Entry<String, PermissionData> entry: permissionMap.entrySet()) {
            permissions.add(entry.getKey());
        }
        return permissions.toArray(new String[permissions.size()]);
    }

    public boolean hasPermissionReadContacts() {
        return (hasPermission(Manifest.permission.READ_CONTACTS));
    }

    public boolean hasPermissionWriteStorage() {
        return (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    private boolean hasPermission(String permission) {
        return (PackageManager.PERMISSION_GRANTED
                == ContextCompat.checkSelfPermission(context, permission));
    }
}