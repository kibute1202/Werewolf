package kh.nobita.hang.Utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import kh.nobita.hang.R;
import kh.nobita.hang.model.ListPlayers;

public class GamePermissions {
    private static final String TAG = GamePermissions.class.getSimpleName();
    public static final int REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int REQUEST_WRITE_EXTERNAL_STORAGE = REQUEST_READ_EXTERNAL_STORAGE + 1;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermissionReadExternalStorage(final Context context) {
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    new MaterialDialog.Builder(context)
                            .title(LocaleHelper.getLangResources(context).getString(R.string.permission_necessary_title))
                            .content(LocaleHelper.getLangResources(context).getString(R.string.permission_necessary_content))
                            .positiveText(LocaleHelper.getLangResources(context).getString(R.string.agree))
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                public void onClick(MaterialDialog dialog, DialogAction which) {
                                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                                }
                            })
                            .show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermissionWriteExternalStorage(final Context context) {
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    new MaterialDialog.Builder(context)
                            .title(LocaleHelper.getLangResources(context).getString(R.string.permission_necessary_title))
                            .content(LocaleHelper.getLangResources(context).getString(R.string.permission_necessary_content))
                            .positiveText(LocaleHelper.getLangResources(context).getString(R.string.agree))
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                public void onClick(MaterialDialog dialog, DialogAction which) {
                                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                                }
                            })
                            .show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
}
