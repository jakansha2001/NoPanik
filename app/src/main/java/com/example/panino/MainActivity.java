package com.example.panino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button add;

    private ImageButton alert;

    public static String lat = "", lon = "";

    FusedLocationProviderClient fusedLocationProviderClient;

    SharedPreferences sharedpreferences;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;

    String mapsurl="https://www.google.com/maps/search/?api=1&query=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.addid);

        alert = findViewById(R.id.alertid);

        sharedpreferences = getSharedPreferences("num", Context.MODE_PRIVATE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        final String a = sharedpreferences.getString("n1", "1");
        final String b = sharedpreferences.getString("n2", "2");
        final String c = sharedpreferences.getString("n3", "3");

        getLocation();

        Log.d("checks", a + b + c);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }


        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }

                if (a.equals("1")) {
                    Toast.makeText(MainActivity.this, "Please save contacts numbers first!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Message sent with current location!!", Toast.LENGTH_SHORT).show();

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(a, null, "There's an Emergency please reach out to me as soon as possible\n" + mapsurl + lat + "," + lon, null, null);

                    SmsManager smsManager1 = SmsManager.getDefault();
                    smsManager1.sendTextMessage(b, null, "There's an Emergency please reach out to me as soon as possible\n" + mapsurl + lat + "," + lon, null, null);

                    SmsManager smsManager2 = SmsManager.getDefault();
                    smsManager2.sendTextMessage(c, null, "There's an Emergency please reach out to me as soon as possible\n" + mapsurl + lat + "," + lon, null, null);
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ContactsPage.class);
                startActivity(intent);
            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {

                    try {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        lat = String.valueOf(addresses.get(0).getLatitude());
                        lon = String.valueOf(addresses.get(0).getLongitude());

                        Log.d("lat11", lat+lon);




                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
}