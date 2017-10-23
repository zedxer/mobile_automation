package com.example.jawwad.mobiledatabase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

/**
 * Created by Jawwad on 11/23/2016.
 */

public class Batterychargingbroadcast {

    public  int level = CurrentConditionValues.battery;
    public boolean chargingstatus;

    public Batterychargingbroadcast(Context c) {


        c.registerReceiver(this.batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    public int getLevel()
    {
        return this.level;
    }

    public boolean getStatus()
    {
        return this.chargingstatus;
    }


    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int  levelv = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            level = levelv;
            if (status == BatteryManager.BATTERY_STATUS_CHARGING)
            {
                chargingstatus = true;
            }
            else if (status == BatteryManager.BATTERY_STATUS_DISCHARGING)
            {
                chargingstatus = false;
            }


        }
    };


}
