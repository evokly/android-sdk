/**
 *
 * Created by Lukasz Majda on 4.07.2016.
 *
 * Copyright © 2016 Evokly. All rights reserved.
 *
 */
package com.evokly.demoapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.evokly.sdk.*;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_COARSE_LOCATION_PERMISSIONS = 0x21;

    private CoordinatorLayout mMainContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMainContent = (CoordinatorLayout) findViewById(R.id.main_content);

        Button debugButton = (Button) findViewById(R.id.button_debug);
        if (debugButton != null) {
            debugButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Evokly.openDebug(MainActivity.this);
                }
            });
        }

        // check permission
        int permissionCheck;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionCheck = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION);
        } else {
            permissionCheck = PackageManager.PERMISSION_GRANTED;
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            initializeEvokly();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_COARSE_LOCATION_PERMISSIONS);
            }
        }
    }

    @Override
    protected void onDestroy() {
        disposeEvokly();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_COARSE_LOCATION_PERMISSIONS: {
                if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initializeEvokly();
                } else {
                    Snackbar.make(mMainContent, R.string.permission_failure, Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        }
    }

    private void initializeEvokly() {
        ServiceSettings serviceSettings = new ServiceSettings();
        serviceSettings.enableConfigAutoUpdate = false;
        serviceSettings.enableStartOnBoot = false;
        serviceSettings.enableWorkInBackground = false;
        serviceSettings.subdomain = BuildConfig.EVOKLY_SUBDOMAIN;
        serviceSettings.apiKey = BuildConfig.EVOKLY_APIKEY;
        serviceSettings.endpointsUrl = BuildConfig.EVOKLY_ENDPOINTS_URL;
        serviceSettings.notificationIcon = R.drawable.ic_stat_evo_sygnet;

        Evokly.initialize(this, serviceSettings);
    }

    private void disposeEvokly() {
        Evokly.dispose(this);
    }

}
