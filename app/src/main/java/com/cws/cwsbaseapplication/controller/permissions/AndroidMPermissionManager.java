package com.cws.cwsbaseapplication.controller.permissions;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;

/**
 * Manages permissions for Android Marshmallow and above
 * Created by nsaini on 19/4/16.
 */
public class AndroidMPermissionManager {
    private FragmentActivity context;

    public AndroidMPermissionManager(FragmentActivity context){
        this.context = context;
    }

    public void requestPermissions(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> revokedPermissions = new ArrayList<>();
            int[] grantedPermisions = new int[permissions.length];
            for(int index=0 ; index < permissions.length; index ++){
                String permission = permissions[index];
                // Assume thisActivity is the current activity
                int permissionCheck = ActivityCompat.checkSelfPermission(context, permission);
                // Here, thisActivity is the current activity
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    revokedPermissions.add(permission);
                } else {
                    grantedPermisions[index] = PackageManager.PERMISSION_GRANTED;
                }
            }

            if(revokedPermissions.size() > 0){
                // Should we show an explanation?
                // if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                // } else {
                // No explanation needed, we can request the permission.
                String[] neededPermissions = new String[revokedPermissions.size()];
                neededPermissions = revokedPermissions.toArray(neededPermissions);
                ActivityCompat.requestPermissions(context, neededPermissions, requestCode);
            }else{
                onRequestPermissionsResult(context,requestCode, permissions, new int[]{PackageManager.PERMISSION_GRANTED});
            }

        } else {
            onRequestPermissionsResult(context,requestCode, permissions, new int[]{PackageManager.PERMISSION_GRANTED});
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void onRequestPermissionsResult(FragmentActivity context, int requestCode, String permissions[], int[] grantResults){
        context.onRequestPermissionsResult(requestCode, permissions, new int[]{PackageManager.PERMISSION_GRANTED});
    }
}
