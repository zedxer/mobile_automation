package com.example.jawwad.mobiledatabase.InterfaceActionCondition.Action;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;

import com.example.jawwad.mobiledatabase.InterfaceActionCondition.Interface.IAction;

import static android.content.Context.LOCATION_SERVICE;


/**
 * Created by Naqi on 12/2/2016.
 */

public class Gpsconcrete implements IAction {

    boolean ison;
    LocationManager locationManager;

    public Gpsconcrete(boolean isonv) {
        this.ison = isonv;
    }

    @Override
    public String getname() {
        return "->  GPS";
    }

    @Override
    public void applyactions(Context c) {

        locationManager = (LocationManager) c.getSystemService(LOCATION_SERVICE);
        boolean enable =  locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(this.ison)
        {


            if (!enable) {
                //  Intent intent=new Intent("android.location.GPS_ENABLED_CHANGE");
                //  intent.putExtra("enabled", true);
                //  c.sendBroadcast(intent);
                //}

                String provider = Settings.Secure.getString(c.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                if (!provider.contains("gps")) //**forcefull solution ***
                { //if gps is enabled
                    final Intent poke = new Intent();
                    poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                    poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                    poke.setData(Uri.parse("3"));
                    c.sendBroadcast(poke);
                }



            }

        }
        else {

            if (enable) {
                //  Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
                //  intent.putExtra("enabled", false);
                //  c.sendBroadcast(intent);

                String provider = Settings.Secure.getString(c.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

                if(provider.contains("gps")){ //if gps is enabled
                    final Intent poke = new Intent();
                    poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                    poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                    poke.setData(Uri.parse("3"));
                    c.sendBroadcast(poke);
                }



            }

        }

    }

    @Override
    public String getactiondetail() {
        String detail= "GPS: ";
        if (ison)
        {
            detail+= "On";

        }
        else
        {
            detail+= "Off";
        }
        return detail;
    }

}

