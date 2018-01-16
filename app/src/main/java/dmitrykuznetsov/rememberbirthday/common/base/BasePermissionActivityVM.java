package dmitrykuznetsov.rememberbirthday.common.base;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.permissions.PermissionsStorage;
import dmitrykuznetsov.rememberbirthday.common.permissions.model.PermissionData;

/**
 * Created by vernau on 3/15/17.
 */

public abstract class BasePermissionActivityVM<A extends AppCompatActivity> extends BaseActivityVM<A> {

    private PermissionsStorage permissionsStorage;

    public BasePermissionActivityVM(A activity, PermissionsStorage permissionsStorage) {
        super(activity);
        this.permissionsStorage = permissionsStorage;
    }

    protected final boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    protected final void requestPermission(String permission, Runnable runnable) {
            PermissionData permissionData = permissionsStorage.getPermissionData(permission);
            if (!hasPermission(permission)) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissionData.permission)) {
                    DialogInterface.OnClickListener okListener = (dialog, id) -> requestPermission(permissionData.permission, permissionData.requestCode);
                    DialogInterface.OnCancelListener cancelListener = (dialog) -> permissionCancel(permissionData.requestCode);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(permissionData.requestRationaleTitleId)
                            .setMessage(permissionData.requestRationaleMessageId)
                            .setPositiveButton(R.string.ok, okListener)
                            .setOnCancelListener(cancelListener)
                            .show();
                } else {
                    requestPermission(permissionData.permission, permissionData.requestCode);
                }
            } else {
                if (runnable != null) {
                    runnable.run();
                }
            }
    }

    private void requestPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, final String[] permissions,
                                           int[] grantResults) {
        for (int i = 0; i < permissions.length; i ++) {
                if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    permissionOK(requestCode);
                } else {
                    permissionCancel(requestCode);
                }
        }
    }

    protected abstract void permissionCancel(int requestCode);

    protected abstract void permissionOK(int requestCode);


}
