package dmitrykuznetsov.rememberbirthday.common.permissions.di;

import android.Manifest;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.permissions.PermissionsStorage;
import dmitrykuznetsov.rememberbirthday.common.permissions.model.PermissionData;

/**
 * Created by dmitry on 1/16/18.
 */

@Module
public class PermissionModule {

    @Provides
    @Singleton
    PermissionsStorage providePermissionsUtil(Context context) {
        Map<String, PermissionData> permissionMap = new HashMap<>();

        String permission = Manifest.permission.READ_CONTACTS;
        int requestId = PermissionsStorage.PERMISSION_REQUEST_READ_CONTACTS;
        int requestRationaleTitleId = R.string.permission_rationale_title_read_contacts;
        int requestRationaleMessageId = R.string.permission_rationale_message_read_contacts;
        PermissionData permissionData = new PermissionData(permission, requestId, requestRationaleTitleId, requestRationaleMessageId);
        permissionMap.put(permission, permissionData);

        permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        requestId = PermissionsStorage.PERMISSION_REQUEST_WRITE_STORAGE;
        requestRationaleTitleId = R.string.permission_rationale_title_write_storage;
        requestRationaleMessageId = R.string.permission_rationale_message_write_storage;
        permissionData = new PermissionData(permission, requestId, requestRationaleTitleId, requestRationaleMessageId);
        permissionMap.put(permission, permissionData);

        return new PermissionsStorage(context, permissionMap);
    }
}
