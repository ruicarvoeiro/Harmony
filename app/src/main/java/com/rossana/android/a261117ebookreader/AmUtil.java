package com.rossana.android.a261117ebookreader;

/**
 * Created by some bitch on 26/11/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

public class AmUtil {
    Activity mActivity;

    /////// INICIO PARTE DADA PELO STOR \\\\\\
    public AmUtil(Activity pA, int pAppRequestCode) {
        this.mActivity = pA;
    } //AmUtil

    public AmUtil(Activity pA) {
        this.mActivity = pA;
    }//AmUtil

    public void utilPopularSpinnerComOpcoes(Spinner pSpn, String[] pOpcoes) {
        ArrayAdapter<String> ad = new ArrayAdapter<String>(
                mActivity,
                android.R.layout.simple_spinner_item,
                pOpcoes
        );
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pSpn.setAdapter(ad);
    } //utilPopularSpinnerComOpcoes


    public void utilFeedback(final String msg) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Toast t = Toast.makeText(mActivity, msg, Toast.LENGTH_LONG);
                t.show();
            } //run
        }; //r

        //java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare()
        mActivity.runOnUiThread(r);
    } //utilFeedback

    //--------------------------------------------------------------------------------------------------------------
    public Boolean utilCheckPermission(String pStrPermission) {
        int iCheckPermissionResult = ContextCompat.checkSelfPermission(
                mActivity,
                pStrPermission
                //Manifest.permission.CALL_PHONE
        );

        boolean bHasPermission =
                (iCheckPermissionResult == PackageManager.PERMISSION_GRANTED);

        return bHasPermission;
    } //utilCheckPermission

    //--------------------------------------------------------------------------------------------------------------
    public boolean utilModernRequestPermission(String strPermissionToAsk, int pAppRequestCode, Boolean bShowToast) {
        boolean bHasPermission = utilCheckPermission(strPermissionToAsk);

        if (!bHasPermission) {
            try {
                ActivityCompat.requestPermissions(
                        mActivity, //Activity
                        //new String[]{Manifest.permission.CALL_PHONE}, //array of permissions
                        new String[]{strPermissionToAsk}, //array of permissions
                        //THIS_APP_REQUEST_CODE //request code
                        pAppRequestCode
                );
                if (bShowToast) {
                    utilFeedback(strPermissionToAsk + " GRANTED!");
                }
                return true;
            }//try
            catch (Exception e) {
                if (bShowToast) {
                    utilFeedback(strPermissionToAsk + " NOT GRANTED!");
                }
                return false;
            } //catch
        } //if
        return false;
    } //utilModernRequestPermission

    public HashMap<String, Boolean> utilModernRequestPermissions(String[] straPermissionsToAsk, int pAppRequestCode, Boolean bShowToast) {
        HashMap<String, Boolean> ret = new HashMap<>();

        for (String strPermission : straPermissionsToAsk) {
            Boolean requestResult =
                    utilModernRequestPermission(
                            strPermission,
                            pAppRequestCode,
                            bShowToast
                    );
            ret.put(strPermission, requestResult);
        }
        return ret;
    } //utilModernRequestPermissions

    public Boolean utilCheckSupportedIntent(Intent pIntent) {
        PackageManager pm = mActivity.getPackageManager();
        Boolean bThereIsAtLeastOneAppCapableOfRespondingToTheIntent =
                pIntent.resolveActivity(pm) != null;

        return bThereIsAtLeastOneAppCapableOfRespondingToTheIntent;
    } //utilCheckSupportedIntent

    /////// FIM PARTE DADA PELO STOR \\\\\\
} //AmUtil
