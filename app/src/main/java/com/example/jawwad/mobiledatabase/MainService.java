package com.example.jawwad.mobiledatabase;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemClock;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Jawwad on 11/11/2016.
 */

public class MainService extends IntentService {

    static Context cntxt;

    public MainService() {
        super("Sensorservices");
    }


    public static void getCurrentTime() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("dd:MMMM:yyyy HH:mm a");
        final String strDate = simpleDateFormat.format(calendar.getTime());
        String[] s = strDate.split(" ");
        CurrentConditionValues.date = s[0];
        CurrentConditionValues.time = s[1];

        calendar = null;
        simpleDateFormat = null;

    }

    public static void getCurrentBattery(Context c) {

        Batterychargingbroadcast b = new Batterychargingbroadcast(c);
        CurrentConditionValues.battery = b.getLevel();
        c = null;
        b = null;
    }


    public static void getCurrentCharging(Context c) {

        Batterychargingbroadcast bat = new Batterychargingbroadcast(c);
        CurrentConditionValues.charging = bat.getStatus();
        c = null;

    }

    public static void getCurrentLocation(Context c) {
        LatLng newLatlng = new LatLng(0.0, 0.0);
        if(Createnewlocation.myLoc == newLatlng){

        }else

//        CurrentConditionValues.location = new LatLng(MainActivity.gps.getLatitude(),MainActivity.gps.getLongitude());
        CurrentConditionValues.location = Createnewlocation.myLoc;

    }




    public static void Runsensors() {
        getCurrentTime();
        getCurrentBattery(cntxt);
        getCurrentCharging(cntxt);
        getCurrentLocation(cntxt);

    }

    static boolean con = true;
    @Override
    protected void onHandleIntent(Intent intent) {

        synchronized (this) {
            int count = 0;
            while (con) {
                MainService.Runsensors();
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    Messenger messenger = (Messenger) bundle.get("messenger");
                    Message msg = Message.obtain();
                    if (!con) {
                        break;

                    }

                    try {
                        messenger.send(msg);
                    } catch (RemoteException e) {
                    }

                }
                SystemClock.sleep(5000);  // 2 seconds
                count+=10;
            }

        }
    }
    }