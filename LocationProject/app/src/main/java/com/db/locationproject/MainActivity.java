package com.db.locationproject;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private TextView textViewUpdate;
    private Button startBTN, stopBTN;

    private static final int CHECK_SETTINGS_CODE = 111;
    private static final int REQUEST_LOCATION_PERMISSION = 222;
    private FusedLocationProviderClient fusedLocationClient; // связь
    private SettingsClient settingsClient; // настройка данных
    private LocationRequest locationRequest; // запрос на местоположение
    private LocationSettingsRequest locationSettingsRequest; // тоже запрос
    private LocationCallback locationCallback; // взаимосявзь
    private Location currentLocation; // местоположение пользователя
    private boolean isLocationUpdatesActive; //
    private String locationUpdateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.locationTextView);
        textViewUpdate = findViewById(R.id.locationUpdateTextView);
        startBTN = findViewById(R.id.startLocationBTN);
        stopBTN = findViewById(R.id.stopLocationBTN);

        settingsClient = LocationServices.getSettingsClient(this);
        buildLocationRequest();
        buildLocationRequest();
        buildLocationCallBack();
        buildLocationSettingsRequest();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // ДЗ

        startBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLocationUpdates();
            }
        });
    }



    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();
    }

    private void buildLocationCallBack() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                currentLocation = locationResult.getLastLocation();
                updateLocationUi();
            }
        };
    }

    private void updateLocationUi() {
        if (currentLocation != null) {
            textView.setText("" + currentLocation.getLatitude() + "/" + currentLocation.getLongitude()
            );
            textViewUpdate.setText(
                    // DateFormat хранит время
                    DateFormat.getTimeInstance().format(new Date())
            );
        }
    }
    private void startLocationUpdates() {
        isLocationUpdatesActive = true;
        startBTN.setEnabled(false);
        stopBTN.setEnabled(true);
        settingsClient.checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(this,
                        new OnSuccessListener<LocationSettingsResponse>() {
                                    @Override
                                    public void onSuccess(
                                            LocationSettingsResponse locationSettingsResponse) {
                                        if
                                        (ActivityCompat.checkSelfPermission(
                                                MainActivity.this,
                                                android.Manifest.permission.ACCESS_FINE_LOCATION)
                                                != PackageManager.PERMISSION_GRANTED
                                                &&
                                                ActivityCompat.checkSelfPermission(MainActivity.this,
                                                        android.Manifest.permission.ACCESS_COARSE_LOCATION)
                                                        != PackageManager.PERMISSION_GRANTED) {
                                            return;
                                        }
                                        fusedLocationClient.requestLocationUpdates(
                                                locationRequest,
                                                locationCallback,
                                                Looper.myLooper()
                                        );
                                        updateLocationUi();
                                    }
                                })
                .addOnFailureListener(this, new
                        OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull
                                                  Exception e) {
                                int statusCode = ((ApiException)
                                        e).getStatusCode();
                                switch (statusCode) {
                                    case LocationSettingsStatusCodes
                                            .RESOLUTION_REQUIRED:
                                        try {
                                            ResolvableApiException resolvableApiException =
                                                    (ResolvableApiException) e;
                                            resolvableApiException.startResolutionForResult(
                                                    MainActivity.this,
                                                    CHECK_SETTINGS_CODE
                                            );
                                        } catch
                                        (IntentSender.SendIntentException sie) {
                                            sie.printStackTrace();
                                        }
                                        break;
                                    case LocationSettingsStatusCodes
                                            .SETTINGS_CHANGE_UNAVAILABLE:
                                        String message =
                                                "Adjust location settings on your device";
                                        Toast.makeText(MainActivity.this, message,
                                                Toast.LENGTH_LONG).show();
                                        isLocationUpdatesActive
                                                = false;
                                        startBTN.setEnabled(true);
                                        stopBTN.setEnabled(false);
                                }
                                updateLocationUi();
                            }
                        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHECK_SETTINGS_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.d("MainActivity", "User has agreed to change location" +
                                "settings");
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.d("MainActivity", "User has not agreed to change location" +
                                "settings");
                        isLocationUpdatesActive = false;
                        startBTN.setEnabled(true);
                        stopBTN.setEnabled(false);
                        updateLocationUi();
                        break;
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isLocationUpdatesActive &&
                checkLocationPermission()) {
            startLocationUpdates();
        } else if (!checkLocationPermission()) {
            requestLocationPermission();
        }
    }

    private boolean checkLocationPermission() {
        int permissionState =
                ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState ==
                PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        boolean shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale
                (this, android.Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRationale) {
            showSnackBar("Location permission is needed for " + "app functionality", "OK",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(
                                    MainActivity.this,
                                    new String[]{
                                            android.Manifest.permission.ACCESS_FINE_LOCATION
                                    },
                                    REQUEST_LOCATION_PERMISSION
                            );
                        }
                    }
            );
        } else {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    REQUEST_LOCATION_PERMISSION
            );
        }
    }

    private void showSnackBar(final String mainText, final String action, View.OnClickListener listener) {
            Snackbar.make(findViewById(android.R.id.content), mainText, Snackbar.LENGTH_INDEFINITE)
                    .setAction(action, listener).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION_PERMISSION)
        {
            if (grantResults.length <= 0) {
                Log.d("onRequestPermissions",
                        "Request was cancelled");
            } else if (grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                if (isLocationUpdatesActive) {
                    startLocationUpdates();
                }
            } else {
                showSnackBar(
                        "Turn on location on settings",
                        "Settings",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new
                                        Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts(
                                        "package",
                                        BuildConfig.APPLICATION_ID, null
                                );
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                );
            }
        }
    }
}